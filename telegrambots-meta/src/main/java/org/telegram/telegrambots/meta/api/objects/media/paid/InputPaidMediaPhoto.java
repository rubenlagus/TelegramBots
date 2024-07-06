package org.telegram.telegrambots.meta.api.objects.media.paid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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
public class InputPaidMediaPhoto extends InputPaidMedia {
    private static final String TYPE = "photo";

    public InputPaidMediaPhoto(@NonNull String media) {
        super(media);
    }

    public InputPaidMediaPhoto(File mediaFile, String fileName) {
        super();
        setMedia(mediaFile, fileName);
    }

    public InputPaidMediaPhoto(InputStream mediaStream, String fileName) {
        super();
        setMedia(mediaStream, fileName);
    }

    public InputPaidMediaPhoto(@NonNull String media, boolean isNewMedia, String mediaName,
                               File newMediaFile, InputStream newMediaStream) {
        super(media, isNewMedia, mediaName, newMediaFile, newMediaStream);
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
