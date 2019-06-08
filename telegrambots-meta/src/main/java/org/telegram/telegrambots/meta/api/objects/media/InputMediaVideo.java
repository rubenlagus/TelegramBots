package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Represents a video.
 */
@SuppressWarnings({"unused"})
@JsonDeserialize
public class InputMediaVideo extends InputMedia<InputMediaVideo> {
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
        super();
    }

    public InputMediaVideo(String media, String caption) {
        super(media, caption);
    }

    public Integer getWidth() {
        return width;
    }

    public InputMediaVideo setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public InputMediaVideo setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public InputMediaVideo setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Boolean getSupportsStreaming() {
        return supportsStreaming;
    }

    public InputMediaVideo setSupportsStreaming(Boolean supportsStreaming) {
        this.supportsStreaming = supportsStreaming;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public InputMediaVideo setThumb(InputFile thumb) {
        this.thumb = thumb;
        return this;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
    }

    @Override
    public String toString() {
        return "InputMediaVideo{" +
                "width=" + width +
                ", height=" + height +
                ", duration=" + duration +
                ", supportsStreaming=" + supportsStreaming +
                "} " + super.toString();
    }
}
