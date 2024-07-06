package org.telegram.telegrambots.meta.api.objects.media.paid;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * The paid media to send is a photo.
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
public class InputPaidMediaVideo extends InputPaidMedia {
    private static final String TYPE = "photo";

    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String SUPPORTS_STREAMING_FIELD = "supports_streaming";
    public static final String THUMBNAIL_FIELD = "thumbnail";

    /**
     * Optional.
     * Video width
     */
    @JsonProperty(WIDTH_FIELD)
    private Integer width;
    /**
     * Optional.
     * Video height
     */
    @JsonProperty(HEIGHT_FIELD)
    private Integer height;
    /**
     * Optional.
     * Video duration
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
    /**
     * Optional.
     * Pass True, if the uploaded video is suitable for streaming
     */
    @JsonProperty(SUPPORTS_STREAMING_FIELD)
    private Boolean supportsStreaming;
    /**
     * Optional.
     * Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side.
     * The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail's width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private InputFile thumbnail;

    public InputPaidMediaVideo(@NonNull String media) {
        super(media);
    }

    public InputPaidMediaVideo(File mediaFile, String fileName) {
        super();
        setMedia(mediaFile, fileName);
    }

    public InputPaidMediaVideo(InputStream mediaStream, String fileName) {
        super();
        setMedia(mediaStream, fileName);
    }

    public InputPaidMediaVideo(@NonNull String media,
                           boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                           Integer width, Integer height, Integer duration, Boolean supportsStreaming,
                           InputFile thumbnail) {
        super(media, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.supportsStreaming = supportsStreaming;
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
}
