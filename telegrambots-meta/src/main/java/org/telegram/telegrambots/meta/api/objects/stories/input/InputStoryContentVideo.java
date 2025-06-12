package org.telegram.telegrambots.meta.api.objects.stories.input;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Describes a video to post as a story.
 * The video must be of the size 720x1280, streamable, encoded with H.265 codec,
 * with key frames added each second in the MPEG4 format, and must not exceed 30 MB.
 * The video can't be reused and can only be uploaded as a new file.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InputStoryContentVideo extends InputStoryContent {
    private static final String TYPE = "video";
    private static final String VIDEO_FIELD = "video";
    private static final String DURATION_FIELD = "duration";
    private static final String COVER_FRAME_TIMESTAMP_FIELD = "cover_frame_timestamp";
    private static final String IS_ANIMATION_FIELD = "is_animation";

    /**
     * The video to post as a story. The video must be of the size 720x1280, streamable, encoded with H.265 codec,
     * with key frames added each second in the MPEG4 format, and must not exceed 30 MB.
     * The video can't be reused and can only be uploaded as a new file, so you can pass "attach://&lt;file_attach_name&gt;"
     * if the video was uploaded using multipart/form-data under &lt;file_attach_name&gt;.
     */
    @JsonProperty(VIDEO_FIELD)
    @NonNull
    private String video;

    /**
     * Optional. Precise duration of the video in seconds; 0-60
     */
    @JsonProperty(DURATION_FIELD)
    private Float duration;

    /**
     * Optional. Timestamp in seconds of the frame that will be used as the static cover for the story.
     * Defaults to 0.0.
     */
    @JsonProperty(COVER_FRAME_TIMESTAMP_FIELD)
    private Float coverFrameTimestamp;

    /**
     * Optional. Pass True if the video has no sound
     */
    @JsonProperty(IS_ANIMATION_FIELD)
    private Boolean isAnimation;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (video.isEmpty()) {
            throw new TelegramApiValidationException("Video can't be empty", this);
        }
        
        if (duration != null && (duration < 0 || duration > 60)) {
            throw new TelegramApiValidationException("Duration must be between 0 and 60 seconds", this);
        }
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
