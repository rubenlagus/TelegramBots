package org.telegram.telegrambots.meta.api.methods.botapimethods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.CollectionType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Api method that can't be use completely as Json
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class PartialBotApiMethod<T extends Serializable> implements Validable {
    @JsonIgnore
    protected static final JsonMapper JSON_MAPPER = new JsonMapper();

    /**
     * Deserialize a json answer to the response type to a method
     * @param answer Json answer received
     * @return Answer for the method
     */
    public abstract T deserializeResponse(String answer) throws TelegramApiRequestException;

    public T deserializeResponse(String answer, Class<T> returnClass) throws TelegramApiRequestException {
        JavaType type = JSON_MAPPER.getTypeFactory().constructType(returnClass);
        return deserializeResponseInternal(answer, type);
    }

    public <K extends Serializable> T deserializeResponseArray(String answer, Class<K> returnClass) throws TelegramApiRequestException {
        CollectionType collectionType = JSON_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, returnClass);
        return deserializeResponseInternal(answer, collectionType);
    }

    protected <K extends Serializable> T deserializeResponseSerializable(String answer, Class<K> returnClass) throws TelegramApiRequestException {
        JavaType type = JSON_MAPPER.getTypeFactory().constructType(returnClass);
        return deserializeResponseInternal(answer, type);
    }

    private T deserializeResponseInternal(String answer, JavaType type) throws TelegramApiRequestException {
        try {
            JavaType responseType = JSON_MAPPER.getTypeFactory().constructParametricType(ApiResponse.class, type);
            ApiResponse<T> result = JSON_MAPPER.readValue(answer, responseType);
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException(String.format("Error executing %s query", this.getClass().getName()), result);
            }
        } catch (JacksonException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    /**
     * Getter for method path (that is the same as method name)
     * @return Method path
     */
    @JsonProperty(BotApiMethod.METHOD_FIELD)
    public abstract String getMethod();

    @JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
    public static abstract class PartialBotApiMethodBuilder<T extends Serializable, C extends PartialBotApiMethod<T>, B extends PartialBotApiMethodBuilder<T, C, B>> {

    }
}
