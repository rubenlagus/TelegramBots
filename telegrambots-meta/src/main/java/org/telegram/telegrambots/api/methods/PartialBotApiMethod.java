package org.telegram.telegrambots.api.methods;


import org.telegram.telegrambots.api.interfaces.Validable;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.myclasses.ObjectMapper;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Api method that can't be use completely as Json
 */
public abstract class PartialBotApiMethod<T extends Serializable> implements Validable {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Deserialize a json answer to the response type to a method
     * @param answer Json answer received
     * @return Answer for the method
     */
    public abstract T deserializeResponse(String answer) throws TelegramApiRequestException;
}
