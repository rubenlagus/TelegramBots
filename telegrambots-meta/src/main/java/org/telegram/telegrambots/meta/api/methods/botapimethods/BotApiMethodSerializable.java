package org.telegram.telegrambots.meta.api.methods.botapimethods;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BotApiMethodSerializable extends BotApiMethod<Serializable> {
    public Serializable deserializeResponseMessageOrBoolean(String answer) throws TelegramApiRequestException {
        return deserializeResponseFromPossibilities(answer, Arrays.asList(Message.class, Boolean.class));
    }

    public Serializable deserializeResponseFromPossibilities(String answer, List<Class<? extends Serializable>> possibleValues) throws TelegramApiRequestException {
        Throwable lastException = null;
        for (Class<? extends Serializable> possibleValue : possibleValues) {
            try {
                return deserializeResponseSerializable(answer, possibleValue);
            } catch (TelegramApiRequestException e) {
                if (e.getCause() instanceof IOException) {
                    lastException = e.getCause();
                } else {
                    throw e;
                }
            }
        }

        throw new TelegramApiRequestException("Unable to deserialize response", lastException);
    }

    public static abstract class BotApiMethodSerializableBuilder<C extends BotApiMethodSerializable, B extends BotApiMethodSerializable.BotApiMethodSerializableBuilder<C, B>> extends BotApiMethodBuilder<Serializable, C, B> {

    }
}
