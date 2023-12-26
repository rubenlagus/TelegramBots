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
 * Represents a general file to be sent.
 */
@SuppressWarnings("unused")
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaDocument extends InputMedia {
    private static final String TYPE = "document";

    public static final String THUMBNAIL_FIELD = "thumbnail";
    public static final String DISABLECONTENTTYPEDETECTION_FIELD = "disable_content_type_detection";

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
     * Disables automatic server-side content type detection for files uploaded using multipart/form-data.
     * Always true, if the document is sent as part of an album.
     */
    @JsonProperty(DISABLECONTENTTYPEDETECTION_FIELD)
    private Boolean disableContentTypeDetection;

    public InputMediaDocument() {
        super();
    }

    public InputMediaDocument(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaDocument(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities,
                              boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream, InputFile thumbnail,
                              Boolean disableContentTypeDetection) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.thumbnail = thumbnail;
        this.disableContentTypeDetection = disableContentTypeDetection;
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

    public static class InputMediaDocumentBuilder {

        @Tolerate
        @Deprecated
        public InputMediaDocument.InputMediaDocumentBuilder thumb(InputFile thumb) {
            this.thumbnail = thumb;
            return this;
        }
    }
}
