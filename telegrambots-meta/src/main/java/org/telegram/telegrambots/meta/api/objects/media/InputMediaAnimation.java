package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
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
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class InputMediaAnimation extends InputMedia {
    private static final String TYPE = "animation";

    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    public static final String THUMBNAIL_FIELD = "thumbnail";
    private static final String HAS_SPOILER_FIELD = "has_spoiler";
    private static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";

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
    @JsonProperty(HAS_SPOILER_FIELD)
    private Boolean hasSpoiler;
    /**
     * Optional.
     * Pass True, if the caption must be shown above the message media
     */
    @JsonProperty(SHOW_CAPTION_ABOVE_MEDIA_FIELD)
    private Boolean showCaptionAboveMedia;

    public InputMediaAnimation(@NonNull String media) {
        super(media);
    }

    public InputMediaAnimation(File mediaFile, String fileName) {
        super();
        setMedia(mediaFile, fileName);
    }

    public InputMediaAnimation(InputStream mediaStream, String fileName) {
        super();
        setMedia(mediaStream, fileName);
    }

    public InputMediaAnimation(@NonNull String media, String caption, String parseMode, List<MessageEntity> captionEntities,
                               boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                               Integer width, Integer height, Integer duration, InputFile thumbnail, Boolean hasSpoiler) {
        super(media, caption, parseMode, captionEntities, isNewMedia, mediaName, newMediaFile, newMediaStream);
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
}
