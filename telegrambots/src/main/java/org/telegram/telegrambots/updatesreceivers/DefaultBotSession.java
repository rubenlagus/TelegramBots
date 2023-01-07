package org.telegram.telegrambots.updatesreceivers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.facilities.TelegramHttpClientBuilder;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.*;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.telegram.telegrambots.Constants.SOCKET_TIMEOUT;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Thread to request updates with active wait
 */
public class DefaultBotSession implements BotSession {
    private static final Logger log = LoggerFactory.getLogger(DefaultBotSession.class);

    private AtomicBoolean running = new AtomicBoolean(false);

    private final ConcurrentLinkedDeque<Update> receivedUpdates = new ConcurrentLinkedDeque<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReaderThread readerThread;
    private HandlerThread handlerThread;
    private LongPollingBot callback;
    private String token;
    private int lastReceivedUpdate = 0;
    private DefaultBotOptions options;
    private UpdatesSupplier updatesSupplier;

    public DefaultBotSession() {
    }

    @Override
    public synchronized void start() {
        if (running.get()) {
            throw new IllegalStateException("Session already running");
        }

        running.set(true);

        lastReceivedUpdate = 0;

        readerThread = new ReaderThread(updatesSupplier, this);
        readerThread.setName(callback.getBotUsername() + " Telegram Connection");
        readerThread.start();

        handlerThread = new HandlerThread();
        handlerThread.setName(callback.getBotUsername() + " Telegram Executor");
        handlerThread.start();
    }

    @Override
    public synchronized void stop() {
        if (!running.get()) {
            throw new IllegalStateException("Session already stopped");
        }

        running.set(false);

        if (readerThread != null) {
            readerThread.interrupt();
        }

        if (handlerThread != null) {
            handlerThread.interrupt();
        }

        if (callback != null) {
            callback.onClosing();
        }
    }

    public void setUpdatesSupplier(UpdatesSupplier updatesSupplier) {
        this.updatesSupplier = updatesSupplier;
    }

    @Override
    public void setOptions(BotOptions options) {
        if (this.options != null) {
            throw new InvalidParameterException("BotOptions has already been set");
        }
        this.options = (DefaultBotOptions) options;
    }

    @Override
    public void setToken(String token) {
        if (this.token != null) {
            throw new InvalidParameterException("Token has already been set");
        }
        this.token = token;
    }

    @Override
    public void setCallback(LongPollingBot callback) {
        if (this.callback != null) {
            throw new InvalidParameterException("Callback has already been set");
        }
        this.callback = callback;
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    @SuppressWarnings("WeakerAccess")
    private class ReaderThread extends Thread implements UpdatesReader {

        private final UpdatesSupplier updatesSupplier;
        private final Object lock;
        private CloseableHttpClient httpclient;
        private BackOff backOff;
        private RequestConfig requestConfig;

        public ReaderThread(UpdatesSupplier updatesSupplier, Object lock) {
            this.updatesSupplier = Optional.ofNullable(updatesSupplier).orElse(this::getUpdatesFromServer);
            this.lock = lock;
        }

        @Override
        public synchronized void start() {
            httpclient = TelegramHttpClientBuilder.build(options);
            requestConfig = options.getRequestConfig();
            backOff = options.getBackOff();

            // fall back to default exponential backoff strategy if no backoff specified
            if (backOff == null) {
                backOff = new ExponentialBackOff();
            }

            if (requestConfig == null) {
                requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                        .setSocketTimeout(SOCKET_TIMEOUT)
                        .setConnectTimeout(SOCKET_TIMEOUT)
                        .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
            }

            super.start();
        }

        @Override
        public void interrupt() {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    log.warn(e.getLocalizedMessage(), e);
                }
            }
            super.interrupt();
        }

        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while (running.get()) {
                synchronized (lock) {
                    if (running.get()) {
                        try {
                            List<Update> updates = updatesSupplier.getUpdates();
                            if (updates.isEmpty()) {
                                lock.wait(500);
                            } else {
                                updates.removeIf(x -> x.getUpdateId() < lastReceivedUpdate);
                                lastReceivedUpdate = updates.parallelStream()
                                        .map(
                                                Update::getUpdateId)
                                        .max(Integer::compareTo)
                                        .orElse(0);
                                receivedUpdates.addAll(updates);

                                synchronized (receivedUpdates) {
                                    receivedUpdates.notifyAll();
                                }
                            }
                        } catch (InterruptedException e) {
                            if (!running.get()) {
                                receivedUpdates.clear();
                            }
                            log.debug(e.getLocalizedMessage(), e);
                            interrupt();
                        } catch (Exception global) {
                            log.error(global.getLocalizedMessage(), global);
                            try {
                                synchronized (lock) {
                                    lock.wait(backOff.nextBackOffMillis());
                                }
                            } catch (InterruptedException e) {
                                if (!running.get()) {
                                    receivedUpdates.clear();
                                }
                                log.debug(e.getLocalizedMessage(), e);
                                interrupt();
                            }
                        }
                    }
                }
            }
            log.debug("Reader thread has being closed");
        }

        private List<Update> getUpdatesFromServer() throws IOException {
            GetUpdates request = GetUpdates
                    .builder()
                    .limit(options.getGetUpdatesLimit())
                    .timeout(options.getGetUpdatesTimeout())
                    .offset(lastReceivedUpdate + 1)
                    .build();

            if (options.getAllowedUpdates() != null) {
                request.setAllowedUpdates(options.getAllowedUpdates());
            }

            String url = options.getBaseUrl() + token + "/" + GetUpdates.PATH;
            //http client
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("charset", StandardCharsets.UTF_8.name());
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = httpclient.execute(httpPost, options.getHttpContext())) {
                String responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

                if (response.getStatusLine().getStatusCode() >= 500) {
                    log.warn(responseContent);
                    synchronized (lock) {
                        lock.wait(500);
                    }
                } else {
                    List<Update> updates = request.deserializeResponse(responseContent);
                    backOff.reset();
                    return updates;
                }
            } catch (SocketException | InvalidObjectException | TelegramApiRequestException e) {
                log.error(e.getLocalizedMessage(), e);
            } catch (SocketTimeoutException e) {
                log.info(e.getLocalizedMessage(), e);
            } catch (InterruptedException e) {
                log.info(e.getLocalizedMessage(), e);
                interrupt();
            } catch (InternalError e) {
                // handle InternalError to workaround OpenJDK bug (resolved since 13.0)
                // https://bugs.openjdk.java.net/browse/JDK-8173620
                if (e.getCause() instanceof InvocationTargetException) {
                    Throwable cause = e.getCause().getCause();
                    log.error(cause.getLocalizedMessage(), cause);
                } else throw e;
            }

            return Collections.emptyList();
        }
    }

    public interface UpdatesSupplier {

        List<Update> getUpdates() throws Exception;
    }

    private List<Update> getUpdateList() {
        List<Update> updates = new ArrayList<>();
        for (Iterator<Update> it = receivedUpdates.iterator(); it.hasNext();) {
            updates.add(it.next());
            it.remove();
        }
        return updates;
    }

    private class HandlerThread extends Thread implements UpdatesHandler {
        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while (running.get()) {
                try {
                    List<Update> updates = getUpdateList();
                    if (updates.isEmpty()) {
                        synchronized (receivedUpdates) {
                            receivedUpdates.wait();
                            updates = getUpdateList();
                            if (updates.isEmpty()) {
                                continue;
                            }
                        }
                    }
                    callback.onUpdatesReceived(updates);
                } catch (InterruptedException e) {
                    log.debug(e.getLocalizedMessage(), e);
                    interrupt();
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage(), e);
                }
            }
            log.debug("Handler thread has being closed");
        }
    }
}
