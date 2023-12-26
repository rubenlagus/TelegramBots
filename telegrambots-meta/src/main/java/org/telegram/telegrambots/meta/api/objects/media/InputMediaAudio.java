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
 * Represents an audio file to be treated as music to be sent.
 */
@SuppressWarnings({"unused"})
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaAudio extends InputMedia {
    private static final String TYPE = "audio";

    public static final String DURATION_FIELD = "duration";
    public static final String PERFORMER_FIELD = "performer";
    public static final String TITLE_FIELD = "title";
    public static final String THUMBNAIL_FIELD = "thumbnail";

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
    private InputFile thumbnail;

    public InputMediaAudio() {
        super();
    }

    public InputMediaAudio(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaAudio(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities,
                           boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                           Integer duration, String performer, String title, InputFile thumbnail) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.duration = duration;
        this.performer = performer;
        this.title = title;
        this.thumbnail = thumbnail;
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

    public static class InputMediaAudioBuilder {

        @Tolerate
        @Deprecated
        public InputMediaAudio.InputMediaAudioBuilder thumb(InputFile thumb) {
            this.thumbnail = thumb;
            return this;
        }
    }
}
