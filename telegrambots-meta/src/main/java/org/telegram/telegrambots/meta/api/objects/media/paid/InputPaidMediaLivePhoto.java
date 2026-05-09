package org.telegram.telegrambots.meta.api.objects.media.paid;

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
 * The paid media to send is a live photo.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputPaidMediaLivePhoto extends InputPaidMedia {
    private static final String TYPE = "live_photo";

    private static final String PHOTO_FIELD = "photo";

    /**
     * The static photo to send.
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private String photo;

    public InputPaidMediaLivePhoto(@NonNull String media, @NonNull String photo) {
        super(media);
        this.photo = photo;
    }

    public InputPaidMediaLivePhoto(File mediaFile, String fileName, String photo) {
        super();
        setMedia(mediaFile, fileName);
        this.photo = photo;
    }

    public InputPaidMediaLivePhoto(InputStream mediaStream, String fileName, String photo) {
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
