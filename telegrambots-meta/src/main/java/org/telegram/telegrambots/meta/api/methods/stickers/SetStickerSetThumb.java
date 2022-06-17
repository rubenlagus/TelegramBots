package org.telegram.telegrambots.meta.api.methods.stickers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to set the thumbnail of a sticker set.
 * Animated thumbnails can be set for animated sticker sets only. Video thumbnails can be set only for video sticker sets only.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetStickerSetThumb extends BotApiMethodBoolean {
    public static final String PATH = "setStickerSetThumb";

    public static final String NAME_FIELD = "name";
    public static final String USERID_FIELD = "user_id";
    public static final String THUMB_FIELD = "thumb";

    @NonNull
    private String name; ///< Sticker set name
    @NonNull
    private Long userId; ///< User identifier of the sticker set owner
    /**
     * Optional.
     * A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px,
     * or a TGS animation with the thumbnail up to 32 kilobytes in size;
     * see https://core.telegram.org/stickers#animated-sticker-requirements for animated sticker technical requirements,
     * or a WEBM video with the thumbnail up to 32 kilobytes in size;
     * see https://core.telegram.org/stickers#video-sticker-requirements for video sticker technical requirements.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers,
     * pass an HTTP URL as a String for Telegram to get a file from the Internet,
     * or upload a new one using multipart/form-data. More info on Sending Files ».
     * Animated sticker set thumbnail can't be uploaded via HTTP URL.
     */
    private InputFile thumb;

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
        if (thumb != null) {
            thumb.validate();
        }
    }
}
