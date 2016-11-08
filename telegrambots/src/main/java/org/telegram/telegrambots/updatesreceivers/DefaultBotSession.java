package org.telegram.telegrambots.updatesreceivers;

import com.google.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.telegram.telegrambots.ApiConstants;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotOptions;
import org.telegram.telegrambots.generics.BotSession;
import org.telegram.telegrambots.generics.LongPollingBot;
import org.telegram.telegrambots.generics.UpdatesHandler;
import org.telegram.telegrambots.generics.UpdatesReader;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Thread to request updates with active wait
 * @date 20 of June of 2015
 */
public class DefaultBotSession implements BotSession {
    protected static final String LOGTAG = "BOTSESSION";

    private volatile boolean running = false;

    private final ConcurrentLinkedDeque<Update> receivedUpdates = new ConcurrentLinkedDeque<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReaderThread readerThread;
    private HandlerThread handlerThread;
    private LongPollingBot callback;
    private String token;
    private int lastReceivedUpdate = 0;
    private DefaultBotOptions options;

    @Inject
    public DefaultBotSession() {
    }

    @Override
    public void start() {
        if (running) {
            throw new IllegalStateException("Session already running");
        }

        running = true;
        if (readerThread == null || readerThread.isInterrupted()) {
            readerThread = new ReaderThread();
            readerThread.setName(callback.getBotUsername() + " Telegram Connection");
        }
        readerThread.start();

        if (handlerThread == null || handlerThread.isInterrupted()) {
            handlerThread = new HandlerThread();
            handlerThread.setName(callback.getBotUsername() + " Telegram Executor");
        }
        handlerThread.start();
    }

    @Override
    public void close() {
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
        return running;
    }

    private class ReaderThread extends Thread implements UpdatesReader {
        private CloseableHttpClient httpclient;
        private RequestConfig requestConfig;

        @Override
        public synchronized void start() {
            super.start();

            httpclient = HttpClientBuilder.create()
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                    .setMaxConnTotal(100)
                    .build();

            requestConfig = options.getRequestConfig();
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
                try {
                    GetUpdates request = new GetUpdates();
                    request.setLimit(100);
                    request.setTimeout(ApiConstants.GETUPDATES_TIMEOUT);
                    request.setOffset(lastReceivedUpdate + 1);
                    String url = ApiConstants.BASE_URL + token + "/" + GetUpdates.PATH;
                    //http client
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.addHeader("charset", StandardCharsets.UTF_8.name());
                    httpPost.setConfig(requestConfig);
                    httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(request), ContentType.APPLICATION_JSON));

                    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                        HttpEntity ht = response.getEntity();
                        BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                        String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
                        try {
                            List<Update> updates = request.deserializeResponse(responseContent);

                            if (updates.isEmpty()) {
                                synchronized (this) {
                                    this.wait(500);
                                }
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
                        }catch (JSONException e) {
                            BotLogger.severe(responseContent, LOGTAG, e);
                        }
                    } catch (InvalidObjectException | TelegramApiRequestException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                } catch (InterruptedException e) {
                    if (!running) {
                        receivedUpdates.clear();
                    }
                    BotLogger.debug(LOGTAG, e);
                } catch (Exception global) {
                    BotLogger.severe(LOGTAG, global);
                    try {
                        synchronized (this) {
                            this.wait(500);
                        }
                    } catch (InterruptedException e) {
                        if (!running) {
                            receivedUpdates.clear();
                        }
                        BotLogger.debug(LOGTAG, e);
                    }
                }
            }
            BotLogger.debug(LOGTAG, "Reader thread has being closed");
        }
    }

    private class HandlerThread extends Thread implements UpdatesHandler {
        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while (running) {
                try {
                    Update update = receivedUpdates.pollLast();
                    if (update == null) {
                        synchronized (receivedUpdates) {
                            receivedUpdates.wait();
                            update = receivedUpdates.pollLast();
                            if (update == null) {
                                continue;
                            }
                        }
                    }
                    callback.onUpdateReceived(update);
                } catch (InterruptedException e) {
                    BotLogger.debug(LOGTAG, e);
                } catch (Exception e) {
                    BotLogger.severe(LOGTAG, e);
                }
            }
            BotLogger.debug(LOGTAG, "Handler thread has being closed");
        }
    }
}
