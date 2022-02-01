package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.stickers.MaskPosition;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

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
public class CreateNewStickerSet extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "createNewStickerSet";

    public static final String USERID_FIELD = "user_id";
    public static final String NAME_FIELD = "name";
    public static final String TITLE_FIELD = "title";
    public static final String PNGSTICKER_FIELD = "png_sticker";
    public static final String TGSSTICKER_FIELD = "tgs_sticker";
    public static final String WEBMSTICKER_FIELD = "webm_sticker";
    public static final String EMOJIS_FIELD = "emojis";
    public static final String CONTAINSMASKS_FIELD = "contains_masks";
    public static final String MASKPOSITION_FIELD = "mask_position";

    @NonNull
    private Long userId; ///< User identifier of created sticker set owner
    /**
     * Name of sticker set, to be used in t.me/addstickers/<name> URLs.
     * Can contain only english letters, digits and underscores.
     * Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”.
     * <bot_username> is case insensitive. 7-64 characters.
     */
    @NonNull
    private String name; ///< Sticker set title, 1-64 characters
    @NonNull
    private String title; ///< User identifier of created sticker set owner
    @NonNull
    private String emojis; ///< One or more emoji corresponding to the sticker
    private Boolean containsMasks; ///< Optional. Pass True, if a set of mask stickers should be created
    private MaskPosition maskPosition; ///< Optional. Position where the mask should be placed on faces
    /**
     * Optional.
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers,
     * pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one
     * using multipart/form-data. More info on Sending Files »
     */
    private InputFile pngSticker;

    /**
     * Optional
     * TGS animation with the sticker, uploaded using multipart/form-data.
     * See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
     */
    private InputFile tgsSticker;

    /**
     * Optional
     * WEBM video with the sticker, uploaded using multipart/form-data.
     * See https://core.telegram.org/stickers#video-stickers for technical requirements
     */
    private InputFile webmSticker;

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error creating new sticker set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }
        if (name.isEmpty()) {
            throw new TelegramApiValidationException("name can't be empty", this);
        }
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("title can't be empty", this);
        }
        if (emojis.isEmpty()) {
            throw new TelegramApiValidationException("emojis can't be empty", this);
        }

        if (pngSticker == null && tgsSticker == null && webmSticker == null) {
            throw new TelegramApiValidationException("One of pngSticker, tgsSticker or webmSticker is needed", this);
        }

        if ((pngSticker != null && tgsSticker != null) || (pngSticker != null && webmSticker != null)
                || (tgsSticker != null && webmSticker != null)) {
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
