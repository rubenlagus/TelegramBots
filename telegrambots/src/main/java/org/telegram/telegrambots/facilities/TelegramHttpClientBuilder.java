package org.telegram.telegrambots.facilities;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import javax.net.ssl.SSLContext;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by bvn13 on 17.04.2018.
 */
public class TelegramHttpClientBuilder {

    public static CloseableHttpClient build(DefaultBotOptions options) {
        HttpClientConnectionManager httpClientConnectionManager = null;

        // to use a SOCKS5 proxy, we need to override connection socket factories used by Apache HttpClient by default
        HttpHost proxyHost = options.getHttpProxy();
        if (proxyHost != null && Objects.equals(proxyHost.getSchemeName(), "socks5")) {
            try {
                // remove proxy information from RequestConfig
                RequestConfig requestConfig = options.getRequestConfig();
                if (requestConfig != null) {
                    // read proxy credentials from RequestConfig and provide a new Authenticator
                    Credentials credentials = Optional.ofNullable(options.getCredentialsProvider())
                            .map(credentialsProvider -> credentialsProvider.getCredentials(new AuthScope(proxyHost)))
                            .orElse(null);
                    if (requestConfig.isAuthenticationEnabled() && credentials != null) {
                        Authenticator.setDefault(new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(
                                        credentials.getUserPrincipal().getName(),
                                        credentials.getPassword().toCharArray()
                                );
                            }
                        });
                    }
                    // make a copy of current request config setting proxy to null
                    requestConfig = RequestConfig.copy(requestConfig)
                            .setProxy(null)
                            .build();
                    options.setRequestConfig(requestConfig);
                }
                // provide new connection manager with proxy-enabled connection socket factories
                Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyHost.getHostName(), proxyHost.getPort()));
                httpClientConnectionManager = new PoolingHttpClientConnectionManager(
                        RegistryBuilder.<ConnectionSocketFactory>create()
                                .register("http", new ProxyPlainConnectionSocketFactory(proxy))
                                .register("https", new ProxySSLConnectionSocketFactory(SSLContext.getDefault(), proxy))
                                .build(),
                        null,
                        null,
                        null,
                        -1,
                        TimeUnit.MILLISECONDS
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to register a SOCKS5 proxy", e);
            }
        }


        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(httpClientConnectionManager)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100);

        if (options.getHttpProxy() != null && !Objects.equals(options.getHttpProxy().getSchemeName(), "socks5")) {

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
