package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Ruben Bermudez
 * @version 10.0
 *
 * Represents a live photo to be sent.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputMediaLivePhoto extends InputMedia {
    private static final String TYPE = "live_photo";

    private static final String PHOTO_FIELD = "photo";
    private static final String HAS_SPOILER_FIELD = "has_spoiler";
    private static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";

    /**
     * The static photo to send.
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private String photo;
    /**
     * Optional.
     * Pass True if the live photo needs to be covered with a spoiler animation
     */
    @JsonProperty(HAS_SPOILER_FIELD)
    private Boolean hasSpoiler;
    /**
     * Optional.
     * Pass True, if the caption must be shown above the message media
     */
    @JsonProperty(SHOW_CAPTION_ABOVE_MEDIA_FIELD)
    private Boolean showCaptionAboveMedia;

    public InputMediaLivePhoto(@NonNull String media, @NonNull String photo) {
        super(media);
        this.photo = photo;
    }

    public InputMediaLivePhoto(File mediaFile, String fileName, String photo) {
        super();
        setMedia(mediaFile, fileName);
        this.photo = photo;
    }

    public InputMediaLivePhoto(InputStream mediaStream, String fileName, String photo) {
        super();
        setMedia(mediaStream, fileName);
        this.photo = photo;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
        if (photo == null || photo.isEmpty()) {
            throw new TelegramApiValidationException("Photo can't be empty", this);
        }
    }
}
