package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.implementations;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.PayloadStorage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/**
 * an interceptor that filters incoming updates based on the allowed container types
 */
public class ContentTypeFilterInterceptor extends Interceptor {

    private final List<ContentType> allowedContentType;

    /**
     * @param allowedContentType List of content type updates with which will be allowed.
     *                           if the update does not contain any of the specified types, it will be filtered
     */
    public ContentTypeFilterInterceptor(List<ContentType> allowedContentType){
        this.allowedContentType = allowedContentType;
    }

    @Override
    public boolean before(Update update, PayloadStorage storage) {
        for (ContentType type : allowedContentType){
            switch (type){
                case MESSAGE:               if (update.hasMessage())            return process();
                case INLINE_QUERY:          if (update.hasInlineQuery())        return process();
                case CHOOSE_INLINE_QUERY:   if (update.hasChosenInlineQuery())  return process();
                case CALLBACK_QUERY:        if (update.hasCallbackQuery())      return process();
                case EDIT_MESSAGE:          if (update.hasEditedMessage())      return process();
                case EDIT_CHANNEL_POST:     if (update.hasEditedChannelPost())  return process();
                case SHIPPING_QUERY:        if (update.hasShippingQuery())      return process();
                case PRE_CHECKOUT_QUERY:    if (update.hasPreCheckoutQuery())   return process();
                case MY_CHAT_MEMBER:        if (update.hasMyChatMember())       return process();
                case CHAT_MEMBER:           if (update.hasChatMember())         return process();
            }
        }

        return interrupt();
    }

    /**
     * Enumeration of {@link Update} types of content
     */
    public enum ContentType{
        MESSAGE,
        INLINE_QUERY,
        CHOOSE_INLINE_QUERY,
        CALLBACK_QUERY,
        EDIT_MESSAGE,
        EDIT_CHANNEL_POST,
        SHIPPING_QUERY,
        PRE_CHECKOUT_QUERY,
        MY_CHAT_MEMBER,
        CHAT_MEMBER
    }
}
