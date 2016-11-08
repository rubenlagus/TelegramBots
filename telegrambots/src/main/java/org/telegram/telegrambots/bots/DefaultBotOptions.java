package org.telegram.telegrambots.bots;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.generics.BotOptions;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Configurations for the Bot
 * @date 21 of July of 2016
 */
public class DefaultBotOptions implements BotOptions {
    private String proxyHost;
    private int proxyPort;
    private int maxThreads; ///< Max number of threads used for async methods executions (default 1)
    private RequestConfig requestConfig;

    public DefaultBotOptions() {
        maxThreads = 1;
    }

    /**
     * @deprecated Use {@link #setRequestConfig(RequestConfig)} instead to configure custom request config
     * @param proxyHost Host for the proxy
     *
     * @apiNote This method will be removed in the future
     */
    @Deprecated
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * @deprecated Use {@link #setRequestConfig(RequestConfig)} instead to configure custom request config
     * @param proxyPort Port for the proxy
     *
     * @apiNote This method will be removed in the future
     */
    @Deprecated
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public boolean hasProxy() {
        return proxyHost != null && !proxyHost.isEmpty() && proxyPort > 0;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public RequestConfig getRequestConfig() {
        if (requestConfig == null) {
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
        return requestConfig;
    }

    /**
     * @implSpec Default implementation assumes no proxy is needed and sets a 75secs timoute
     * @param requestConfig Request config to be used in all Http requests
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}
