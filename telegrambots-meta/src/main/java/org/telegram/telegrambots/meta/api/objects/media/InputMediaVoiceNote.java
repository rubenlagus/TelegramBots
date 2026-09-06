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
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.2
 *
 * Represents a voice message file to be sent.
 */
@SuppressWarnings({"unused"})
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputMediaVoiceNote extends InputMedia {
    private static final String TYPE = "voice_note";

    public static final String DURATION_FIELD = "duration";

    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Optional. Duration of the voice message in seconds

    public InputMediaVoiceNote(@NonNull String media) {
        super(media);
    }

    public InputMediaVoiceNote(File mediaFile, String fileName) {
        super();
        setMedia(mediaFile, fileName);
    }

    public InputMediaVoiceNote(InputStream mediaStream, String fileName) {
        super();
        setMedia(mediaStream, fileName);
    }

    public InputMediaVoiceNote(@NonNull String media, String caption, String parseMode, List<MessageEntity> captionEntities,
                               boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                               Integer duration) {
        super(media, caption, parseMode, captionEntities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.duration = duration;
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
