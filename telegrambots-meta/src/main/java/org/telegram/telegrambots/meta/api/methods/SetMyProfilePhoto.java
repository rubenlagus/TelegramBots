package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.photo.input.InputProfilePhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.4
 * Changes the profile photo of the bot. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMyProfilePhoto extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "setMyProfilePhoto";

    public static final String PHOTO_FIELD = "photo";

    /**
     * The new profile photo to set
     */
    @JsonProperty(PHOTO_FIELD)
    private InputProfilePhoto photo;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (photo == null) {
            throw new TelegramApiValidationException("Photo parameter can't be null", this);
        }
        photo.validate();
    }
}
