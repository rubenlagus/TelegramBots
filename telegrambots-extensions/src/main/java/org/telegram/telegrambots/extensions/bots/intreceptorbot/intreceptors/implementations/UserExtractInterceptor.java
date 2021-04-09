package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Payload;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Stage;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.First;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * an interceptor that extracts the user object from the update and stores it in PayloadStorage
 */
@First(stage = Stage.BEFORE)
public class UserExtractInterceptor extends Interceptor {

    @Override
    public boolean before(Update update, PayloadStorage storage) {
        User user = null;
        if (update.hasMessage()){
            user = update.getMessage().getFrom();
        } else if (update.hasInlineQuery()){
            user = update.getInlineQuery().getFrom();
        } else if (update.hasChosenInlineQuery()){
            user = update.getChosenInlineQuery().getFrom();
        } else if (update.hasCallbackQuery()){
            user = update.getCallbackQuery().getFrom();
        } else if (update.hasEditedMessage()){
            user = update.getEditedMessage().getFrom();
        } else if (update.hasEditedChannelPost()){
            user = update.getEditedChannelPost().getFrom();
        } else if (update.hasShippingQuery()){
            user = update.getShippingQuery().getFrom();
        } else if (update.hasPreCheckoutQuery()){
            user = update.getPreCheckoutQuery().getFrom();
        } else if (update.hasMyChatMember()){
            user = update.getMyChatMember().getFrom();
        } else if (update.hasChatMember()){
            user = update.getChatMember().getFrom();
        }
        storage.addPayload(User.class, Payload.of(user));

        return process();
    }
}
