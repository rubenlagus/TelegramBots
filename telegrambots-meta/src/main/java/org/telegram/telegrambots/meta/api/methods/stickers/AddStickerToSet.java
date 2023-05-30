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
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;
import org.telegram.telegrambots.meta.api.objects.stickers.MaskPosition;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to add a new sticker to a set created by the bot.
 * The format of the added sticker must match the format of the other stickers in the set. Emoji sticker sets can have up to 200 stickers.
 * Animated and video sticker sets can have up to 50 stickers. Static sticker sets can have up to 120 stickers.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class AddStickerToSet extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "addStickerToSet";

    public static final String USERID_FIELD = "user_id";
    public static final String NAME_FIELD = "name";
    public static final String STICKER_FIELD = "sticker";

    @Deprecated
    public static final String PNGSTICKER_FIELD = "png_sticker";
    @Deprecated
    public static final String TGSSTICKER_FIELD = "tgs_sticker";
    @Deprecated
    public static final String WEBMSTICKER_FIELD = "webm_sticker";
    @Deprecated
    public static final String EMOJIS_FIELD = "emojis";
    @Deprecated
    public static final String MASKPOSITION_FIELD = "mask_position";

    @NonNull
    private Long userId; ///< User identifier of sticker set owner
    @NonNull
    private String name; ///< Sticker set name
    /**
     * A JSON-serialized object with information about the added sticker.
     * If exactly the same sticker had already been added to the set, then the set isn't changed.
     *
     * @apiNote This field will become NonNull in next major release as per Telegram API definition.
     */
    // @NonNull
    private InputSticker sticker;


    @NonNull
    @Deprecated
    private String emojis; ///< One or more emoji corresponding to the sticker

    @Deprecated
    private MaskPosition maskPosition; ///< Optional. Position where the mask should be placed on faces
    /**
     * Optional.
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px. Pass a file_id as a String to send a file
     * that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram
     * to get a file from the Internet, or upload a new one using multipart/form-data.
     */
    @Deprecated
    private InputFile pngSticker;
    /**
     * Optional.
     * TGS animation with the sticker, uploaded using multipart/form-data.
     * See <a href="https://core.telegram.org/animated_stickers#technical-requirements"/a> for technical requirements
     */
    @Deprecated
    private InputFile tgsSticker;

    /**
     * Optional.
     * WEBM video with the sticker, uploaded using multipart/form-data.
     * See <a href="https://core.telegram.org/stickers#video-stickers"/a> for technical requirements
     */
    @Deprecated
    private InputFile webmSticker;

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }
        if (name.isEmpty()) {
            throw new TelegramApiValidationException("name can't be empty", this);
        }

        if (pngSticker == null && tgsSticker == null && webmSticker == null) {
            if (sticker == null) {
                throw new TelegramApiValidationException("Sticker can't be empty", this);
            } else {
                sticker.validate();
            }
        } else {
            // Support deprecated mode
            if (emojis.isEmpty()) {
                throw new TelegramApiValidationException("emojis can't be empty", this);
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
}
