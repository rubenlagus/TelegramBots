package org.telegram.telegrambots.bots;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.updatesreceivers.ExponentialBackOff;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Configurations for the Bot
 * @date 21 of July of 2016
 */
public class DefaultBotOptions implements BotOptions {
    private int maxThreads; ///< Max number of threads used for async methods executions (default 1)
    private RequestConfig requestConfig;
    private volatile HttpContext httpContext;
    private ExponentialBackOff exponentialBackOff;
    private Integer maxWebhookConnections;
    private String baseUrl;
    private List<String> allowedUpdates;
    private ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;

    public enum ProxyType {
        NO_PROXY,
        HTTP,
        SOCKS4,
        SOCKS5
    }

    public DefaultBotOptions() {
        maxThreads = 1;
        baseUrl = ApiConstants.BASE_URL;
        httpContext = HttpClientContext.create();
        proxyType = ProxyType.NO_PROXY;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public Integer getMaxWebhookConnections() {
        return maxWebhookConnections;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }

    public void setMaxWebhookConnections(Integer maxWebhookConnections) {
        this.maxWebhookConnections = maxWebhookConnections;
    }

    public List<String> getAllowedUpdates() {
        return allowedUpdates;
    }

    public void setAllowedUpdates(List<String> allowedUpdates) {
        this.allowedUpdates = allowedUpdates;
    }

    /**
     * @implSpec Default implementation assumes no proxy is needed and sets a 75secs timoute
     * @param requestConfig Request config to be used in all Http requests
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public ExponentialBackOff getExponentialBackOff() {
        return exponentialBackOff;
    }

    /**
     * @implSpec Default implementation assumes starting at 500ms and max time of 60 minutes
     * @param exponentialBackOff ExponentialBackOff to be used when long polling fails
     */
    public void setExponentialBackOff(ExponentialBackOff exponentialBackOff) {
        this.exponentialBackOff = exponentialBackOff;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
}
