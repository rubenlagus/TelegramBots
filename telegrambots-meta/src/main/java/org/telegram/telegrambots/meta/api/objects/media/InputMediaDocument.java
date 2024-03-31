package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Represents a general file to be sent.
 */
@SuppressWarnings("unused")

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@SuperBuilder
@Jacksonized
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

    public InputMediaDocument(@NonNull String media) {
        super(media);
    }

    public InputMediaDocument(File mediaFile, String fileName) {
        super();
        setMedia(mediaFile, fileName);
    }

    public InputMediaDocument(InputStream mediaStream, String fileName) {
        super();
        setMedia(mediaStream, fileName);
    }

    public InputMediaDocument(@NonNull String media, String caption, String parseMode, List<MessageEntity> captionEntities,
                              boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                              InputFile thumbnail, Boolean disableContentTypeDetection) {
        super(media, caption, parseMode, captionEntities, isNewMedia, mediaName, newMediaFile, newMediaStream);
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
}
