package org.telegram.telegrambots.updatesreceivers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.api.Constants;
import org.telegram.telegrambots.api.TelegramApiConfiguration;
import org.telegram.telegrambots.api.methods.GetUpdates;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.ITelegramLongPollingBot;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Thread to request updates with active wait
 * @date 20 of June of 2015
 */
public class UpdatesThread {
    private final ITelegramLongPollingBot callback;
    private final ReaderThread readerThread;
    private final HandlerThread handlerThread;
    private int lastReceivedUpdate = 0;
    private String token;
    private final ConcurrentLinkedDeque<Update> receivedUpdates = new ConcurrentLinkedDeque<>();

    public UpdatesThread(String token, ITelegramLongPollingBot callback) {
        this.token = token;
        this.callback = callback;
        this.readerThread = new ReaderThread();
        this.readerThread.start();
        this.handlerThread = new HandlerThread();
        this.handlerThread.start();
    }

    private class ReaderThread extends Thread {
        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while(true) {
                GetUpdates request = new GetUpdates();
                request.setLimit(100);
                request.setTimeout(20);
                request.setOffset(lastReceivedUpdate + 1);
                CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).setConnectionTimeToLive(20, TimeUnit.SECONDS).build();
                String url = Constants.BASEURL + token + "/" + GetUpdates.PATH;
                HttpPost httpPost = new HttpPost(url);
                if (TelegramApiConfiguration.getInstance().getProxy() != null) {
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setProxy(TelegramApiConfiguration.getInstance().getProxy())
                            .build();
                    httpPost.setConfig(requestConfig);
                }
                try {
                    httpPost.addHeader("charset", "UTF-8");
                    httpPost.setEntity(new StringEntity(request.toJson().toString(), ContentType.APPLICATION_JSON));
                    HttpResponse response;
                    response = httpclient.execute(httpPost);
                    HttpEntity ht = response.getEntity();

                    BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                    String responseContent = EntityUtils.toString(buf, "UTF-8");

                    try {
                        JSONObject jsonObject = new JSONObject(responseContent);
                        if (!jsonObject.getBoolean("ok")) {
                            throw new InvalidObjectException(jsonObject.toString());
                        }
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        if (jsonArray.length() != 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Update update = new Update(jsonArray.getJSONObject(i));
                                if (update.getUpdateId() > lastReceivedUpdate) {
                                    lastReceivedUpdate = update.getUpdateId();
                                    receivedUpdates.addFirst(update);
                                }
                            }
                            synchronized (receivedUpdates) {
                                receivedUpdates.notifyAll();
                            }
                        } else {
                            try {
                                synchronized (this) {
                                    this.wait(500);
                                }
                            } catch (InterruptedException ignored) {
                            }
                        }
                    } catch (JSONException ignored) {
                    }
                } catch (IOException ignored) {
                }
            }
        }
    }

    private class HandlerThread extends Thread {
        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while(true) {
                try {
                    Update update = receivedUpdates.pollLast();
                    if (update == null) {
                        synchronized (receivedUpdates) {
                            try {
                                receivedUpdates.wait();
                            } catch (InterruptedException e) {
                                continue;
                            }
                            update = receivedUpdates.pollLast();
                            if (update == null) {
                                continue;
                            }
                        }
                    }
                    callback.onUpdateReceived(update);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
