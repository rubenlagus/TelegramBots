package org.telegram.telegrambots.meta.api.methods.botapimethods;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BotApiMethodMessage extends BotApiMethod<Message> {
    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Message.class);
    }

    public static abstract class BotApiMethodMessageBuilder<C extends BotApiMethodMessage, B extends BotApiMethodMessage.BotApiMethodMessageBuilder<C, B>> extends BotApiMethodBuilder<Message, C, B> {

    }
}
