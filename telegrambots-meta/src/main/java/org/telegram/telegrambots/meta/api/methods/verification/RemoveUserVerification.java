package org.telegram.telegrambots.meta.api.methods.verification;

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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 8.2
 *
 * Removes verification from a user who is currently verified on behalf of the organization represented by the bot.
 * Returns True on success.
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
public class RemoveUserVerification extends BotApiMethodBoolean {
    public static final String PATH = "removeUserVerification";

    private static final String USER_ID_FIELD = "user_id";

    /**
     * Unique identifier of the target user
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
