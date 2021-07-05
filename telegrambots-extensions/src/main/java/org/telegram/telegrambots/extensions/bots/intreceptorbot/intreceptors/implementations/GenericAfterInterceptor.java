package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.Callable;

/**
 * an interceptor that execute callable after processing update
 */
public class GenericAfterInterceptor extends Interceptor {
    private static final Logger log = LoggerFactory.getLogger(GenericAfterInterceptor.class);

    private final Callable callable;

    public GenericAfterInterceptor(Callable callable) {
        this.callable = callable;
    }

    @Override
    public void after(Update update, PayloadStorage storage) {
        try {
            callable.call();
        } catch (Exception e) {
            log.warn("", e);
        }
    }
}
