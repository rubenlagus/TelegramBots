package org.telegram.telegrambots.facilities;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.facilities.proxysocketfactorys.HttpConnectionSocketFactory;
import org.telegram.telegrambots.facilities.proxysocketfactorys.HttpSSLConnectionSocketFactory;
import org.telegram.telegrambots.facilities.proxysocketfactorys.SocksConnectionSocketFactory;
import org.telegram.telegrambots.facilities.proxysocketfactorys.SocksSSLConnectionSocketFactory;

import static org.telegram.telegrambots.Constants.SOCKET_TIMEOUT;

/**
 * Created by bvn13 on 17.04.2018.
 */
public class TelegramHttpClientBuilder {

    public static CloseableHttpClient build(DefaultBotOptions options) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(createConnectionManager(options));
        return httpClientBuilder.build();
    }

    private static HttpClientConnectionManager createConnectionManager(DefaultBotOptions options) {
        Registry<ConnectionSocketFactory> registry;
        switch (options.getProxyType()) {
            case HTTP:
                registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", new HttpConnectionSocketFactory())
                        .register("https", new HttpSSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
                break;
            case SOCKS4:
            case SOCKS5:
                registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", new SocksConnectionSocketFactory())
                        .register("https", new SocksSSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                        .build();
                break;
            default:
                return null;
        }

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setTimeToLive(TimeValue.ofSeconds(70))
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(SOCKET_TIMEOUT)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setMaxTotal(100);
        return connectionManager;
    }

}
