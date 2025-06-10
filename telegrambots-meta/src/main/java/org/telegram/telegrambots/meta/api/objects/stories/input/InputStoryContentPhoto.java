package org.telegram.telegrambots.meta.api.objects.stories.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Describes a photo to post as a story.
 * The photo must be of the size 1080x1920 and must not exceed 10 MB.
 * The photo can't be reused and can only be uploaded as a new file.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InputStoryContentPhoto extends InputStoryContent {
    private static final String TYPE = "photo";
    private static final String PHOTO_FIELD = "photo";

    /**
     * The photo to post as a story. The photo must be of the size 1080x1920 and must not exceed 10 MB.
     * The photo can't be reused and can only be uploaded as a new file, so you can pass "attach://&lt;file_attach_name&gt;"
     * if the photo was uploaded using multipart/form-data under &lt;file_attach_name&gt;.
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private String photo;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (photo.isEmpty()) {
            throw new TelegramApiValidationException("Photo can't be empty", this);
        }
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
