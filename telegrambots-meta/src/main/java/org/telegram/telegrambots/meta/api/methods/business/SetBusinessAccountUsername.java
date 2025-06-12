package org.telegram.telegrambots.meta.api.methods.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 * Changes the username of a managed business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_change_username business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetBusinessAccountUsername extends BotApiMethodBoolean {
    public static final String PATH = "setBusinessAccountUsername";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String USERNAME_FIELD = "username";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * The new value of the username for the business account; 0-32 characters
     */
    @JsonProperty(USERNAME_FIELD)
    @NonNull
    private String username;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
        if (!username.isEmpty() && username.length() > 32) {
            throw new TelegramApiValidationException("Username parameter can't exceed 32 characters", this);
        }
    }
}
