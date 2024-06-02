package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Arrays;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to set the thumbnail of a regular or mask sticker set.
 * The format of the thumbnail file must match the format of the stickers in the set.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetStickerSetThumbnail extends BotApiMethodBoolean {
    public static final String PATH = "setStickerSetThumbnail";

    public static final String NAME_FIELD = "name";
    public static final String USER_ID_FIELD = "user_id";
    public static final String THUMBNAIL_FIELD = "thumbnail";
    public static final String FORMAT_FIELD = "format";

    /**
     * Sticker set name
     */
    @NonNull
    private String name;
    /**
     * User identifier of the sticker set owner
     */
    @NonNull
    private Long userId;
    /**
     * Format of the added sticker,
     * must be one of “static” for a .WEBP or .PNG image, “animated” for a .TGS animation, “video” for a WEBM video
     */
    @NonNull
    private String format;
    /**
     * Optional
     * A .WEBP or .PNG image with the thumbnail, must be up to 128 kilobytes in size and have a width and height of
     * exactly 100px, or a .TGS animation with a thumbnail up to 32 kilobytes in size
     * (see <a href="https://core.telegram.org/stickers#animated-sticker-requirements">https://core.telegram.org/stickers#animated-sticker-requirements</a>
     * for animated sticker technical requirements),
     * or a WEBM video with the thumbnail up to 32 kilobytes in size;
     * see <a href="https://core.telegram.org/stickers#video-sticker-requirements">https://core.telegram.org/stickers#video-sticker-requirements</a>
     * for video sticker technical requirements.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers,
     * pass an HTTP URL as a String for Telegram to get a file from the Internet,
     * or upload a new one using multipart/form-data. More information on Sending Files ».
     * Animated and video sticker set thumbnails can't be uploaded via HTTP URL.
     *
     * If omitted, then the thumbnail is dropped and the first sticker is used as the thumbnail.
     */
    private InputFile thumbnail;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name.isEmpty()) {
            throw new TelegramApiValidationException("name can't be null", this);
        }
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be null", this);
        }
        if (!Arrays.asList("static", "animated", "video").contains(format)) {
            throw new TelegramApiValidationException("Format must be 'static', 'animated', 'video'", this);
        }
        if (thumbnail != null) {
            thumbnail.validate();
        }
    }
}
