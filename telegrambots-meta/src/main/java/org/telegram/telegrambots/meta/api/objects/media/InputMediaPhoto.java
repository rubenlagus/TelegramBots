package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.InputStream;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Represents a photo.
 */
@SuppressWarnings("unused")
@JsonDeserialize
public class InputMediaPhoto extends InputMedia<InputMediaPhoto> {
    private static final String TYPE = "photo";

    public InputMediaPhoto() {
        super();
    }

    public InputMediaPhoto(InputStream media, String fileName, String caption) {
        super(media, fileName, caption);
    }

    public InputMediaPhoto(String media, String caption) {
        super(media, caption);
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
        return "InputMediaPhoto{} " + super.toString();
    }
}
