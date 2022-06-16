package org.telegram.telegrambots.meta.api.methods.stickers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.stickers.MaskPosition;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to create a new sticker set owned by a user.
 * The bot will be able to edit the sticker set thus created.
 * You must use exactly one of the fields png_sticker, tgs_sticker, or webm_sticker.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddStickerToSet extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "addStickerToSet";

    public static final String USERID_FIELD = "user_id";
    public static final String NAME_FIELD = "name";
    public static final String PNGSTICKER_FIELD = "png_sticker";
    public static final String TGSSTICKER_FIELD = "tgs_sticker";
    public static final String WEBMSTICKER_FIELD = "webm_sticker";
    public static final String EMOJIS_FIELD = "emojis";
    public static final String MASKPOSITION_FIELD = "mask_position";

    @NonNull
    private Long userId; ///< User identifier of sticker set owner
    @NonNull
    private String name; ///< Sticker set name
    @NonNull
    private String emojis; ///< One or more emoji corresponding to the sticker
    private MaskPosition maskPosition; ///< Optional. Position where the mask should be placed on faces
    /**
     * Optional.
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px. Pass a file_id as a String to send a file
     * that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram
     * to get a file from the Internet, or upload a new one using multipart/form-data.
     */
    private InputFile pngSticker;
    /**
     * Optional.
     * TGS animation with the sticker, uploaded using multipart/form-data.
     * See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
     */
    private InputFile tgsSticker;

    /**
     * Optional.
     * WEBM video with the sticker, uploaded using multipart/form-data.
     * See https://core.telegram.org/stickers#video-stickers for technical requirements
     */
    private InputFile webmSticker;

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }
        if (name.isEmpty()) {
            throw new TelegramApiValidationException("name can't be empty", this);
        }
        if (emojis.isEmpty()) {
            throw new TelegramApiValidationException("emojis can't be empty", this);
        }

        if (pngSticker == null && tgsSticker == null && webmSticker == null) {
            throw new TelegramApiValidationException("One of pngSticker, tgsSticker or webmSticker is needed", this);
        }

        if ((pngSticker != null && tgsSticker != null) || (pngSticker != null && webmSticker != null) ||
                (tgsSticker != null && webmSticker != null)) {
            throw new TelegramApiValidationException("Only one of pngSticker, tgsSticker or webmSticker are allowed", this);
        }

        if (pngSticker != null) {
            pngSticker.validate();
        }

        if (tgsSticker != null) {
            tgsSticker.validate();
        }

        if (webmSticker != null) {
            webmSticker.validate();
        }

        if (maskPosition != null) {
            maskPosition.validate();
        }
    }
}
