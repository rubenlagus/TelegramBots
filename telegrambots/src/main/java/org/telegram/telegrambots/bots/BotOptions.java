package org.telegram.telegrambots.bots;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.Constants;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @deprecated Use {@link DefaultBotOptions} instead
 */
@Deprecated
public class BotOptions extends DefaultBotOptions {
    private String proxyHost;
    private int proxyPort;

    public boolean hasProxy() {
        return proxyHost != null && !proxyHost.isEmpty() && proxyPort > 0;
    }

    /**
     * @deprecated Use {@link #setRequestConfig(RequestConfig)} instead to configure custom request config
     * @param proxyHost Host for the proxy
     *
     * @apiNote This method will be removed in the future
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * @deprecated Use {@link #setRequestConfig(RequestConfig)} instead to configure custom request config
     * @param proxyPort Port for the proxy
     *
     * @apiNote This method will be removed in the future
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    @Override
    public RequestConfig getRequestConfig() {
        if (super.getRequestConfig() == null) {
            if (hasProxy()) { // For backward compatibility
                return RequestConfig.copy(RequestConfig.custom().build())
                        .setProxy(new HttpHost(proxyHost, proxyPort))
                        .setSocketTimeout(Constants.SOCKET_TIMEOUT)
                        .setConnectTimeout(Constants.SOCKET_TIMEOUT)
                        .setConnectionRequestTimeout(Constants.SOCKET_TIMEOUT)
                        .build();
            }
            return RequestConfig.copy(RequestConfig.custom().build())
                    .setSocketTimeout(Constants.SOCKET_TIMEOUT)
                    .setConnectTimeout(Constants.SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(Constants.SOCKET_TIMEOUT)
                    .build();
        }

        return super.getRequestConfig();
    }
}
