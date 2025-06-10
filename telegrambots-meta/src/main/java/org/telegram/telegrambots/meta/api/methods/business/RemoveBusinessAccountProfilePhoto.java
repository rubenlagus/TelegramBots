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
 * Removes the current profile photo of a managed business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_edit_profile_photo business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveBusinessAccountProfilePhoto extends BotApiMethodBoolean {
    public static final String PATH = "removeBusinessAccountProfilePhoto";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String IS_PUBLIC_FIELD = "is_public";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * Optional.
     * Pass True to remove the public photo, which is visible even if the main photo is hidden
     * by the business account's privacy settings.
     *
     * @apiNote After the main photo is removed, the previous profile photo (if present) becomes the main photo.
     */
    @JsonProperty(IS_PUBLIC_FIELD)
    private Boolean isPublic;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
    }
}
