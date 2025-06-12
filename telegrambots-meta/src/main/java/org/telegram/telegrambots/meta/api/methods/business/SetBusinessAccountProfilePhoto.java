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
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 * Changes the profile photo of a managed business account.
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
public class SetBusinessAccountProfilePhoto extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "setBusinessAccountProfilePhoto";

    public static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    public static final String PHOTO_FIELD = "photo";
    public static final String IS_PUBLIC_FIELD = "is_public";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * The new profile photo to set
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private InputProfilePhoto photo;
    /**
     * Optional.
     * Pass True to set the public photo, which will be visible even if the main photo is hidden
     * by the business account's privacy settings.
     *
     * @apiNote An account can have only one public photo.
     */
    @JsonProperty(IS_PUBLIC_FIELD)
    private Boolean isPublic;

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
        photo.validate();
    }
}
