package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Api method that can't be use completely as Json
 */
public abstract class PartialBotApiMethod<T extends Serializable> implements Validable {
    @JsonIgnore
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Deserialize a json answer to the response type to a method
     * @param answer Json answer received
     * @return Answer for the method
     */
    public abstract T deserializeResponse(String answer) throws TelegramApiRequestException;
}
