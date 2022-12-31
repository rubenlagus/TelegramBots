package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

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

    public T deserializeResponse(String answer, Class<T> returnClass) throws TelegramApiRequestException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return deserializeResponseInternal(answer, type);
    }

    public <K extends Serializable> T deserializeResponseArray(String answer, Class<K> returnClass) throws TelegramApiRequestException {
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, returnClass);
        return deserializeResponseInternal(answer, collectionType);
    }

    protected <K extends Serializable> T deserializeResponseSerializable(String answer, Class<K> returnClass) throws TelegramApiRequestException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return deserializeResponseInternal(answer, type);
    }

    private T deserializeResponseInternal(String answer, JavaType type) throws TelegramApiRequestException {
        try {
            JavaType responseType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ApiResponse.class, type);
            ApiResponse<T> result = OBJECT_MAPPER.readValue(answer, responseType);
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException(String.format("Error executing %s query", this.getClass().getName()), result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    /**
     * Getter for method path (that is the same as method name)
     * @return Method path
     */
    @JsonProperty(BotApiMethod.METHOD_FIELD)
    public abstract String getMethod();
}
