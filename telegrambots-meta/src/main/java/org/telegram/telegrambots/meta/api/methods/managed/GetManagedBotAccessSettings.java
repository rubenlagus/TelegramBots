package org.telegram.telegrambots.meta.api.methods.managed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.BotAccessSettings;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Use this method to get the access settings of a managed bot.
 * Returns a BotAccessSettings object on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetManagedBotAccessSettings extends BotApiMethod<BotAccessSettings> {
    public static final String PATH = "getManagedBotAccessSettings";

    private static final String USER_ID_FIELD = "user_id";

    /**
     * User identifier of the managed bot whose access settings will be returned
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null || userId == 0) {
            throw new TelegramApiValidationException("UserId can't be null or 0", this);
        }
    }

    @Override
    public BotAccessSettings deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, BotAccessSettings.class);
    }
}
