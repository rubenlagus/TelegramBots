package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BooleanBotApiMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    protected static final String METHOD_FIELD = "method";

    /**
     * Getter for method path (that is the same as method name)
     * @return Method path
     */
    @JsonProperty(METHOD_FIELD)
    public abstract String getMethod();

    @Override
    public T deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<T> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<T>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending commands", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }
}
