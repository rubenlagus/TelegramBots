package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public  class BotApiMethod<T extends Serializable> extends PartialBotApiMethod<T> {

    /*
    * Refactored code to remove unnecessary abstraction by moving METHOD_FIELD to PartialBotApiMethod class.
     */
    @Override
    public T deserializeResponse(String answer) throws TelegramApiRequestException {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }
}
