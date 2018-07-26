package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Represents a video.
 */
@SuppressWarnings("unused")
public class InputMediaVideo extends InputMedia<InputMediaVideo> {
    private static final String TYPE = "video";

    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String SUPPORTSSTREAMING_FIELD = "supports_streaming";

    @JsonProperty(WIDTH_FIELD)
    private int width; ///< Optional. Video width
    @JsonProperty(HEIGHT_FIELD)
    private int height; ///< Optional. Video height
    @JsonProperty(DURATION_FIELD)
    private int duration; ///< Optional. Video duration
    @JsonProperty(SUPPORTSSTREAMING_FIELD)
    private Boolean supportsStreaming; ///< Optional. Pass True, if the uploaded video is suitable for streaming

    public InputMediaVideo() {
        super();
    }

    public InputMediaVideo(String media, String caption) {
        super(media, caption);
    }

    public int getWidth() {
        return width;
    }

    public InputMediaVideo setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public InputMediaVideo setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public InputMediaVideo setDuration(int duration) {
        this.duration = duration;
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
}
