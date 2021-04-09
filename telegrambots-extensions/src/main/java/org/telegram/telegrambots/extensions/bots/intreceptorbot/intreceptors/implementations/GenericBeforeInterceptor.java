package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.Callable;

/**
 * an interceptor that execute callable before processing update
 */
public class GenericBeforeInterceptor extends Interceptor {
    private static final Logger log = LoggerFactory.getLogger(GenericBeforeInterceptor.class);

    private final Callable callable;

    public GenericBeforeInterceptor(Callable callable) {
        this.callable = callable;
    }

    /**
     * @return {@link Interceptor#INTERRUPT} if {@link Callable#call()} throw some exception
     */
    @Override
    public boolean before(Update update, PayloadStorage storage) {
        try {
            callable.call();
        } catch (Exception e) {
            log.warn("", e);
            return interrupt();
        }

        return process();
    }
}
