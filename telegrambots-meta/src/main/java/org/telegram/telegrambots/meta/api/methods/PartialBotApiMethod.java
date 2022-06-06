package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
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

    /**
     * Deserialize a json answer to the response type to a method
     * @param answer Json answer received
     * @param exceptionMessage message to exception if result false
     * @return Answer for the method
     */
    public T deserializeResponseDefault(String answer, String exceptionMessage) throws TelegramApiRequestException{

        try {
            ApiResponse<T> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<T>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException(exceptionMessage, result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }

    }
}
