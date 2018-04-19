package org.telegram.telegrambots.facilities;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.concurrent.TimeUnit;

/**
 * Created by bvn13 on 17.04.2018.
 */
public class TelegramHttpClientBuilder {

    public static CloseableHttpClient build(DefaultBotOptions options) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100);

        if (options.getHttpProxy() != null) {

            httpClientBuilder.setProxy(options.getHttpProxy());

            if (options.getCredentialsProvider() != null) {
                httpClientBuilder
                        .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                        .setDefaultCredentialsProvider(options.getCredentialsProvider());
            }

        }

        return httpClientBuilder.build();
    }

}
