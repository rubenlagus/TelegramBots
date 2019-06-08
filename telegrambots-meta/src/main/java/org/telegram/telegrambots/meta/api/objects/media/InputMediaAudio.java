package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an audio file to be treated as music to be sent.
 */
@SuppressWarnings({"unused"})
@JsonDeserialize
public class InputMediaAudio extends InputMedia<InputMediaAudio> {
    private static final String TYPE = "audio";

    public static final String DURATION_FIELD = "duration";
    public static final String PERFORMER_FIELD = "performer";
    public static final String TITLE_FIELD = "title";
    public static final String THUMB_FIELD = "thumb";

    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Optional. Duration of the audio in seconds
    @JsonProperty(PERFORMER_FIELD)
    private String performer; ///< Optional. Performer of the audio
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title of the audio
    /**
     * Optional. Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;


    public InputMediaAudio() {
        super();
    }

    public InputMediaAudio(String media, String caption) {
        super(media, caption);
    }

    public Integer getDuration() {
        return duration;
    }

    public InputMediaAudio setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getPerformer() {
        return performer;
    }

    public InputMediaAudio setPerformer(String performer) {
        this.performer = performer;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InputMediaAudio setTitle(String title) {
        this.title = title;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public InputMediaAudio setThumb(InputFile thumb) {
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
        return "InputMediaAudio{" +
                "duration=" + duration +
                ", performer=" + performer +
                ", title=" + title +
                ", thumb=" + thumb +
                "} " + super.toString();
    }
}
