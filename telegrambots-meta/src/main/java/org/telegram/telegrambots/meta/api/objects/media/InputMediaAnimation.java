package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
 */
@SuppressWarnings("unused")
public class InputMediaAnimation extends InputMedia<InputMediaAnimation> {
    private static final String TYPE = "animation";

    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String DURATION_FIELD = "duration";
    public static final String THUMB_FIELD = "thumb";

    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Optional. Animation width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Optional. Animation height
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Optional. Animation duration
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail‘s width and height should not exceed 90.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

    public InputMediaAnimation() {
        super();
    }

    public InputMediaAnimation(String media, String caption) {
        super(media, caption);
    }

    public Integer getWidth() {
        return width;
    }

    public InputMediaAnimation setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public InputMediaAnimation setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public InputMediaAnimation setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public InputMediaAnimation setThumb(InputFile thumb) {
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
        return "InputMediaAnimation{" +
                "width=" + width +
                ", height=" + height +
                ", duration=" + duration +
                ", thumb=" + thumb +
                "} " + super.toString();
    }
}
