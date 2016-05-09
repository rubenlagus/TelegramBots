package org.telegram.telegrambots.updatesreceivers;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.BotLogger;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.ITelegramLongPollingBot;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import static org.telegram.telegrambots.Constants.ERRORCODEFIELD;
import static org.telegram.telegrambots.Constants.ERRORDESCRIPTIONFIELD;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Thread to request updates with active wait
 * @date 20 of June of 2015
 */
public class BotSession {
    private static final String LOGTAG = "BOTSESSION";
    private static final int SOCKET_TIMEOUT = 75 * 1000;

    private final ITelegramLongPollingBot callback;
    private final ReaderThread readerThread;
    private final HandlerThread handlerThread;
    private final ConcurrentLinkedDeque<Update> receivedUpdates = new ConcurrentLinkedDeque<>();
    private final String token;
    private int lastReceivedUpdate = 0;
    private volatile boolean running = true;
    private volatile CloseableHttpClient httpclient;
    private volatile RequestConfig requestConfig;


    public BotSession(String token, ITelegramLongPollingBot callback) {
        this.token = token;
        this.callback = callback;
        httpclient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100)
                .build();
        requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
        this.readerThread = new ReaderThread();
        readerThread.setName(callback.getBotUsername() + " Telegram Connection");
        this.readerThread.start();
        this.handlerThread = new HandlerThread();
        handlerThread.setName(callback.getBotUsername() + " Executor");
        this.handlerThread.start();
    }
    
    public void close()
    {
    	running = false;
    	if(httpclient != null)
    	{
    		try
			{
				httpclient.close();
				httpclient = null;
            } catch (IOException e) {
                BotLogger.severe(LOGTAG, e);
            }
    	}
    	
    }

    private class ReaderThread extends Thread {

		@Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while(running) {
                try {
                    GetUpdates request = new GetUpdates();
                    request.setLimit(100);
                    request.setTimeout(50);
                    request.setOffset(lastReceivedUpdate + 1);
                    String url = Constants.BASEURL + token + "/" + GetUpdates.PATH;
                    //http client
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.addHeader("charset", StandardCharsets.UTF_8.name());
                    httpPost.setConfig(requestConfig);
                    httpPost.setEntity(new StringEntity(request.toJson().toString(), ContentType.APPLICATION_JSON));

                    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                        HttpEntity ht = response.getEntity();
                        BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                        String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(responseContent);
                        if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                            throw new TelegramApiException("Error getting updates", jsonObject.getString(ERRORDESCRIPTIONFIELD), jsonObject.getInt(ERRORCODEFIELD));
                        }
                        JSONArray jsonArray = jsonObject.getJSONArray(Constants.RESPONSEFIELDRESULT);
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
                            } catch (InterruptedException e) {
                                BotLogger.severe(LOGTAG, e);
                            }
                        }
                    } catch (InvalidObjectException | JSONException | TelegramApiException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                } catch (Exception global) {
                    BotLogger.severe(LOGTAG, global);
                    try {
                        synchronized (this) {
                            this.wait(500);
                        }
                    } catch (InterruptedException e) {
                        BotLogger.severe(LOGTAG, e);
                    }
                }
            }
        }
    }

    private class HandlerThread extends Thread {
        @Override
        public void run() {
            setPriority(Thread.MIN_PRIORITY);
            while(running) {
                try {
                    Update update = receivedUpdates.pollLast();
                    if (update == null) {
                        synchronized (receivedUpdates) {
                            try {
                                receivedUpdates.wait();
                            } catch (InterruptedException e) {
                                BotLogger.severe(LOGTAG, e);
                                continue;
                            }
                            update = receivedUpdates.pollLast();
                            if (update == null) {
                                continue;
                            }
                        }
                    }
                    callback.onUpdateReceived(update);
                } catch (Exception e) {
                    BotLogger.severe(LOGTAG, e);
                }
            }
        }
    }
}
