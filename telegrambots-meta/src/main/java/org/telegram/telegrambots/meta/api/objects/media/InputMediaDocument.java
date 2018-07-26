package org.telegram.telegrambots.meta.api.objects.media;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents a general file to be sent.
 */
@SuppressWarnings("unused")
public class InputMediaDocument extends InputMedia<InputMediaDocument> {
    private static final String TYPE = "document";
    public static final String THUMB_FIELD = "thumb";

    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail‘s width and height should not exceed 90.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

    public InputMediaDocument() {
        super();
    }

    public InputMediaDocument(String media, String caption) {
        super(media, caption);
    }

    public InputFile getThumb() {
        return thumb;
    }

    public InputMediaDocument setThumb(InputFile thumb) {
        this.thumb = thumb;
        return this;
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
        return "InputMediaDocument{" +
                "thumb=" + thumb +
                "} " + super.toString();
    }
}
