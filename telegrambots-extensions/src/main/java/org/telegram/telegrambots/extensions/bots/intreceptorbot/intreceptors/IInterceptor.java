package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.List;

/**
 *
 */
public interface IInterceptor {

    /**
     * indicates that interceptor chain must be continue
     */
    boolean INTERRUPT   = true;
    /**
     * indicates that interceptor chain must be interrupt
     */
    boolean PROCESS     = false;

    /**
     * method that will be call before after {@link LongPollingBot#onUpdateReceived(Update)}
     * @param update  update redirected from {@link LongPollingBot#onUpdateReceived(Update)} params
     * @param storage payloads storage for current bot
     */
    boolean before(Update update, PayloadStorage storage);

    /**
     * method that will be call after {@link LongPollingBot#onUpdateReceived(Update)}
     * @param update update redirected from {@link LongPollingBot#onUpdateReceived(Update)} params
     * @param storage payloads storage for current bot
     */
    void after(Update update, PayloadStorage storage);

    /**
     * set current sent objects
     * @param messages null if feature not enabled
     */
    void setSentMessages(List<Object> messages);

}
