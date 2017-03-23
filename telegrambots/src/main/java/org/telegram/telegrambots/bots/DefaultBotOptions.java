package org.telegram.telegrambots.bots;

import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.generics.BotOptions;
import org.telegram.telegrambots.updatesreceivers.ExponentialBackOff;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Configurations for the Bot
 * @date 21 of July of 2016
 */
public class DefaultBotOptions implements BotOptions {
    private int maxThreads = 1; ///< Max number of threads used for async methods executions (default 1)
    /**
     * Custom executorService factory
     */
    private Supplier<ExecutorService> executorServiceSupplier = () -> Executors.newFixedThreadPool(maxThreads);
    private RequestConfig requestConfig;
    private ExponentialBackOff exponentialBackOff;
    private Integer maxWebhookConnections;
    private List<String> allowedUpdates;

    public DefaultBotOptions() {}

    public void setMaxThreads(int maxThreads) {
        boolean inRange = maxThreads > 0 && maxThreads <= Runtime.getRuntime().availableProcessors();
        checkArgument(inRange, "Given arg %d exceeds range of (0, availablProcessors]", maxThreads);
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

    public Supplier<ExecutorService> getExecutorServiceSupplier() {
        return executorServiceSupplier;
    }

    public void setExecutorServiceSupplier(Supplier<ExecutorService> executorServiceSupplier) {
        checkNotNull(executorServiceSupplier);
        this.executorServiceSupplier = executorServiceSupplier;
    }
}
