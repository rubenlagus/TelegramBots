package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Represents a video.
 */
@SuppressWarnings({"unused"})
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaVideo extends InputMedia {
    private static final String TYPE = "video";

    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String DURATION_FIELD = "duration";
    public static final String SUPPORTSSTREAMING_FIELD = "supports_streaming";
    public static final String THUMB_FIELD = "thumb";

    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Optional. Video width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Optional. Video height
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Optional. Video duration
    @JsonProperty(SUPPORTSSTREAMING_FIELD)
    private Boolean supportsStreaming; ///< Optional. Pass True, if the uploaded video is suitable for streaming
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    @JsonProperty(THUMB_FIELD)
    private InputFile thumb;

    public InputMediaVideo() {
    }

    public InputMediaVideo(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaVideo(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities, boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream, Integer width, Integer height, Integer duration, Boolean supportsStreaming, InputFile thumb) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.supportsStreaming = supportsStreaming;
        this.thumb = thumb;
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
