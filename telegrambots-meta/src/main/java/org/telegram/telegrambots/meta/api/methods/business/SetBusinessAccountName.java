package org.telegram.telegrambots.meta.api.methods.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 * Changes the first and last name of a managed business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_change_name business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SetBusinessAccountName extends BotApiMethodBoolean {
    public static final String PATH = "setBusinessAccountName";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * The new value of the first name for the business account; 1-64 characters
     */
    @JsonProperty(FIRST_NAME_FIELD)
    @NonNull
    private String firstName;
    /**
     * The new value of the last name for the business account; 0-64 characters
     */
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
        if (firstName.isEmpty()) {
            throw new TelegramApiValidationException("FirstName parameter can't be empty string", this);
        }
        if (firstName.length() > 64) {
            throw new TelegramApiValidationException("FirstName parameter can't exceed 64 characters", this);
        }
        if (lastName != null && lastName.length() > 64) {
            throw new TelegramApiValidationException("LastName parameter can't exceed 64 characters", this);
        }
    }
}
