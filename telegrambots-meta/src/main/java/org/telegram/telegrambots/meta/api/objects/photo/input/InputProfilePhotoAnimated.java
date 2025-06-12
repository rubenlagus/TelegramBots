package org.telegram.telegrambots.meta.api.objects.photo.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InputProfilePhotoAnimated extends InputProfilePhoto {
    public static final String TYPE = "animated";
    public static final String ANIMATION_FIELD = "animation";
    public static final String MAIN_FRAME_TIMESTAMP_FIELD = "main_frame_timestamp";
    
    /**
     * The animated profile photo. Profile photos can't be reused and can only be uploaded as a new file, 
     * so you can pass "attach://&lt;file_attach_name&gt;" if the photo was uploaded using multipart/form-data 
     * under &lt;file_attach_name&gt;.
     */
    @JsonProperty(ANIMATION_FIELD)
    @NonNull
    private InputFile animation;
    /**
     * Optional.
     * Timestamp in seconds of the frame that will be used as the static profile photo.
     * Defaults to 0.0.
     */
    @JsonProperty(MAIN_FRAME_TIMESTAMP_FIELD)
    private Float mainFrameTimestamp;

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
        animation.validate();
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
