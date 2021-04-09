package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * an interceptor that filters text updates based on regular regex, if regex don't match text update interrupt
 */
public class RegexFilterInterceptor extends Interceptor {

    private final String regex;

    /**
     * @param regex regular expression
     */
    public RegexFilterInterceptor(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean before(Update update, PayloadStorage storage) {
        if (!(update.hasMessage() && update.getMessage().hasText())) return interrupt();

        if (!update.getMessage().getText().matches(regex)){
            return interrupt();
        }

        return process();
    }
}
