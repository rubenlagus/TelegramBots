package org.telegram.telegrambots.meta.api.objects.photo.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * An animated profile photo in the MPEG4 format.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InputProfilePhotoStatic extends InputProfilePhoto {
    public static final String TYPE = "static";
    public static final String PHOTO_FIELD = "photo";

    /**
     * The static profile photo.
     * Profile photos can't be reused and can only be uploaded as a new file,
     * so you can pass “attach://<file_attach_name>”
     * if the photo was uploaded using multipart/form-data under <file_attach_name>.
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private InputFile photo;

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();

        photo.validate();
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
