package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
 */
@SuppressWarnings("unused")
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaAnimation extends InputMedia {
    private static final String TYPE = "animation";

    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String DURATION_FIELD = "duration";
    public static final String THUMBNAIL_FIELD = "thumbnail";
    public static final String HASSPOILER_FIELD = "has_spoiler";

    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Optional. Animation width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Optional. Animation height
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Optional. Animation duration
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumbnail;
    /**
     * Optional.
     * Pass True if the animation must be covered with a spoiler animation
     */
    @JsonProperty(HASSPOILER_FIELD)
    private Boolean hasSpoiler;

    public InputMediaAnimation() {
        super();
    }

    public InputMediaAnimation(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaAnimation(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities,
                               boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                               Integer width, Integer height, Integer duration, InputFile thumbnail, Boolean hasSpoiler) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.hasSpoiler = hasSpoiler;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
    }

    /**
     * @deprecated Use {{@link #getThumbnail()}}
     */
    @JsonIgnore
    @Deprecated
    public InputFile getThumb() {
        return thumbnail;
    }

    /**
     * @deprecated Use {{@link #setThumbnail(InputFile)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumb(InputFile thumb) {
        this.thumbnail = thumb;
    }

    public static class InputMediaAnimationBuilder {

        @Tolerate
        @Deprecated
        public InputMediaAnimation.InputMediaAnimationBuilder thumb(InputFile thumb) {
            this.thumbnail = thumb;
            return this;
        }
    }
}
