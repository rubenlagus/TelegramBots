package org.telegram.telegrambots.api;

import org.apache.http.HttpHost;

/**
 * Created by Sergey Skoptsov (flicus@gmail.com) on 11.03.2016.
 */

public class TelegramApiConfiguration {

    private HttpHost proxy = null;

    public static TelegramApiConfiguration getInstance() {
        return Singleton.instance;
    }

    public HttpHost getProxy() {
        return proxy;
    }

    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    public void setProxy(String host, int port, String scheme) {
        this.proxy = new HttpHost(host, port, scheme);
    }

    private static class Singleton {
        public static TelegramApiConfiguration instance = new TelegramApiConfiguration();
    }
}
