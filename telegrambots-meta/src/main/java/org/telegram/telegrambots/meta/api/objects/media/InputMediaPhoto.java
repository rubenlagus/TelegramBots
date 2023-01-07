package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Represents a photo.
 */
@SuppressWarnings("unused")
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaPhoto extends InputMedia {
    private static final String TYPE = "photo";

    public static final String HASSPOILER_FIELD = "has_spoiler";

    /**
     * Optional.
     * Pass True if the photo must be covered with a spoiler animation
     */
    @JsonProperty(HASSPOILER_FIELD)
    private Boolean hasSpoiler;

    public InputMediaPhoto() {
        super();
    }

    public InputMediaPhoto(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaPhoto(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities,
                           boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream,
                           Boolean hasSpoiler) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.hasSpoiler = hasSpoiler;
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
