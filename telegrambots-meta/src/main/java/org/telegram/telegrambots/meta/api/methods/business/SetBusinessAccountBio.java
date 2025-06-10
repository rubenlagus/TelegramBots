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
 * Changes the bio of a managed business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_change_bio business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SetBusinessAccountBio extends BotApiMethodBoolean {
    public static final String PATH = "setBusinessAccountBio";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String BIO_FIELD = "bio";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * The new value of the bio for the business account; 0-140 characters
     */
    @JsonProperty(BIO_FIELD)
    private String bio;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
        if (bio != null && bio.length() > 140) {
            throw new TelegramApiValidationException("Bio parameter can't exceed 140 characters", this);
        }
    }
}
