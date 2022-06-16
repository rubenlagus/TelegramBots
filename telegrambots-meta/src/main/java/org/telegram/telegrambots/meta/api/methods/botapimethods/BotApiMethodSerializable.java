package org.telegram.telegrambots.meta.api.methods.botapimethods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
}
