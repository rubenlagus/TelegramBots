package org.telegram.telegrambots.bots;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.BackOff;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Configurations for the Bot
 * @date 21 of July of 2016
 */
public class DefaultBotOptions implements BotOptions {
    /**
     * Max number of threads used for async methods executions (default 1)
     */
    private int maxThreads;
    private RequestConfig requestConfig;
    private volatile HttpContext httpContext;
    private BackOff backOff;
    private Integer maxWebhookConnections;
    private String baseUrl;
    private List<String> allowedUpdates;
    private ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
    private int getUpdatesTimeout;
    private int getUpdatesLimit;

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
        getUpdatesTimeout = ApiConstants.GETUPDATES_TIMEOUT;
        getUpdatesLimit = 100;
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
     * @param requestConfig Request config to be used in all Http requests
     * @implSpec Default implementation assumes no proxy is needed and sets a 75secs timoute
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    public BackOff getBackOff() {
        return backOff;
    }

    /**
     * @param BackOff backOff to be used when long polling fails
     * @implSpec Default implementation assumes starting at 500ms and max time of 60 minutes
     */
    public void setBackOff(BackOff BackOff) {
        this.backOff = BackOff;
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

    public int getGetUpdatesTimeout() {
        return getUpdatesTimeout;
    }

    public void setGetUpdatesTimeout(int getUpdatesTimeout) {
        this.getUpdatesTimeout = getUpdatesTimeout;
    }

    public int getGetUpdatesLimit() {
        return getUpdatesLimit;
    }

    public void setGetUpdatesLimit(int getUpdatesLimit) {
        this.getUpdatesLimit = getUpdatesLimit;
    }
}
