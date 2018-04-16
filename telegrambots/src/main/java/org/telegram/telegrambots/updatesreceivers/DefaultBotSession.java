package org.telegram.telegrambots.updatesreceivers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.telegram.telegrambots.ApiConstants;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.*;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import static org.telegram.telegrambots.Constants.SOCKET_TIMEOUT;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Thread to request updates with active wait
 */
public class DefaultBotSession implements BotSession {
    private static final String LOGTAG = "BOTSESSION";

    private volatile boolean running = false;

    private final ConcurrentLinkedDeque<Update> receivedUpdates = new ConcurrentLinkedDeque<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReaderThread readerThread;
    private HandlerThread handlerThread;
    private LongPollingBot callback;
    private String token;
    private int lastReceivedUpdate = 0;
    private DefaultBotOptions options;
    private UpdatesSupplier updatesSupplier;

    @Inject
    public DefaultBotSession() {
    }

    @Override
    public synchronized void start() {
        if (running) {
            throw new IllegalStateException("Session already running");
        }

        running = true;

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
        if (!running) {
            throw new IllegalStateException("Session already stopped");
        }

        running = false;

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
    public synchronized boolean isRunning() {
        return running;
    }

    private class ReaderThread extends Thread implements UpdatesReader {

        private final UpdatesSupplier updatesSupplier;
        private final Object lock;
        private CloseableHttpClient httpclient;
        private ExponentialBackOff exponentialBackOff;
        private RequestConfig requestConfig;

        public ReaderThread(UpdatesSupplier updatesSupplier, Object lock) {
            this.updatesSupplier = Optional.ofNullable(updatesSupplier).orElse(this::getUpdatesFromServer);
            this.lock = lock;
        }

        protected CloseableHttpClient createHttpClient() {
            CloseableHttpClient localClient = null;

            if (options.getCredentialsProvider() != null) {
                localClient = HttpClientBuilder.create()
                        .setProxy(options.getHttpProxy())
                        .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                        .setDefaultCredentialsProvider(options.getCredentialsProvider())
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                        .setMaxConnTotal(100)
                        .build();
            } else {
                localClient = HttpClientBuilder.create()
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                        .setMaxConnTotal(100)
                        .build();
            }

            return localClient;
        }

        @Override
        public synchronized void start() {
            httpclient = createHttpClient();
            requestConfig = options.getRequestConfig();
            exponentialBackOff = options.getExponentialBackOff();

            if (exponentialBackOff == null) {
                exponentialBackOff = new ExponentialBackOff();
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
                    BotLogger.warn(LOGTAG, e);
                }
            }
            super.interrupt();
        }

        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while (running) {
                synchronized (lock) {
                    if (running) {
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
                            if (!running) {
                                receivedUpdates.clear();
                            }
                            BotLogger.debug(LOGTAG, e);
                            interrupt();
                        } catch (Exception global) {
                            BotLogger.severe(LOGTAG, global);
                            try {
                                synchronized (lock) {
                                    lock.wait(exponentialBackOff.nextBackOffMillis());
                                }
                            } catch (InterruptedException e) {
                                if (!running) {
                                    receivedUpdates.clear();
                                }
                                BotLogger.debug(LOGTAG, e);
                                interrupt();
                            }
                        }
                    }
                }
            }
            BotLogger.debug(LOGTAG, "Reader thread has being closed");
        }

        private List<Update> getUpdatesFromServer() throws IOException {
            GetUpdates request = new GetUpdates()
                    .setLimit(100)
                    .setTimeout(ApiConstants.GETUPDATES_TIMEOUT)
                    .setOffset(lastReceivedUpdate + 1);

            if (options.getAllowedUpdates() != null) {
                request.setAllowedUpdates(options.getAllowedUpdates());
            }

            String url = options.getBaseUrl() + token + "/" + GetUpdates.PATH;
            //http client
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("charset", StandardCharsets.UTF_8.name());
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);

                if (response.getStatusLine().getStatusCode() >= 500) {
                    BotLogger.warn(LOGTAG, responseContent);
                    synchronized (lock) {
                        lock.wait(500);
                    }
                } else {
                    try {
                        List<Update> updates = request.deserializeResponse(responseContent);
                        exponentialBackOff.reset();
                        return updates;
                    } catch (JSONException e) {
                        BotLogger.severe(responseContent, LOGTAG, e);
                    }
                }
            } catch (SocketException | InvalidObjectException | TelegramApiRequestException e) {
                BotLogger.severe(LOGTAG, e);
            } catch (SocketTimeoutException e) {
                BotLogger.fine(LOGTAG, e);
            } catch (InterruptedException e) {
                BotLogger.fine(LOGTAG, e);
                interrupt();
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
            while (running) {
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
                    BotLogger.debug(LOGTAG, e);
                    interrupt();
                } catch (Exception e) {
                    BotLogger.severe(LOGTAG, e);
                }
            }
            BotLogger.debug(LOGTAG, "Handler thread has being closed");
        }
    }
}
