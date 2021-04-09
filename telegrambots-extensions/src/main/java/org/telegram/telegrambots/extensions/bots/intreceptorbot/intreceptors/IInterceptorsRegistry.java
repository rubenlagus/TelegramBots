package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.List;

/**
 * registry for manage interceptor chain
 */
public interface IInterceptorsRegistry {

    /**
     * add interceptor for chain
     * @param iInterceptor interceptor for register
     */
    void registerInterceptor(IInterceptor iInterceptor);

    /**
     * @param update update redirected from {@link LongPollingBot#onUpdateReceived(Update)} params
     * @param storage payloads storage for current bot
     * @return true if chain must be continue
     */
    boolean callBeforeInterceptor(Update update, PayloadStorage storage);

    /**
     * @param update update redirected from {@link LongPollingBot#onUpdateReceived(Update)} params
     * @param storage payloads storage for current bot
     * @param sentMessages list of saving outgoing messages
     */
    void callAfterInterceptor(Update update, PayloadStorage storage, List<Object> sentMessages);

    /**
     * @return instance of {@link PayloadStorage} for current bot
     */
    PayloadStorage getPayloadStorage();

    /**
     * delete all payloads
     */
    void clearStorage();

}
