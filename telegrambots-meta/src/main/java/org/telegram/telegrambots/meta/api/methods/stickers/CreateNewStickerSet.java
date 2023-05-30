package org.telegram.telegrambots.meta.api.methods.stickers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;
import org.telegram.telegrambots.meta.api.objects.stickers.MaskPosition;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Arrays;
import java.util.List;

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
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CreateNewStickerSet extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "createNewStickerSet";

    public static final String USERID_FIELD = "user_id";
    public static final String STICKERS_FIELD = "stickers";
    public static final String NAME_FIELD = "name";
    public static final String TITLE_FIELD = "title";
    public static final String STICKER_FORMAT_FIELD = "sticker_format";
    public static final String STICKERTYPE_FIELD = "sticker_type";
    public static final String NEEDS_REPAINTING_FIELD = "needs_repainting";

    @Deprecated
    public static final String PNGSTICKER_FIELD = "png_sticker";
    @Deprecated
    public static final String TGSSTICKER_FIELD = "tgs_sticker";
    @Deprecated
    public static final String WEBMSTICKER_FIELD = "webm_sticker";
    @Deprecated
    public static final String EMOJIS_FIELD = "emojis";
    @Deprecated
    public static final String CONTAINSMASKS_FIELD = "contains_masks";
    @Deprecated
    public static final String MASKPOSITION_FIELD = "mask_position";


    /**
     * User identifier of created sticker set owner
     */
    @NonNull
    private Long userId;
    /**
     * Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals).
     * Can contain only English letters, digits and underscores.
     * Must begin with a letter, can't contain consecutive underscores and must end in
     * "_by_<bot_username>". <bot_username> is case-insensitive. 1-64 characters.
     */
    @NonNull
    private String name;
    /**
     * Sticker set title, 1-64 characters
     */
    @NonNull
    private String title;
    /**
     * A JSON-serialized list of 1-50 initial stickers to be added to the sticker set
     */
    @NonNull
    @Singular
    private List<InputSticker> stickers;
    /**
     * Format of stickers in the set, must be one of “static”, “animated”, “video”
     */
    @NonNull
    private String stickerFormat;
    /**
     * Optional
     * True if stickers in the sticker set must be repainted to the color of text when used in messages,
     * the accent color if used as emoji status, white on chat photos, or another appropriate color based on context;
     * for custom emoji sticker sets only
     */
    private Boolean needsRepainting;
    /**
     * Optional
     * Type of stickers in the set, pass “regular”, “mask”, or “custom_emoji”.
     * By default, a regular sticker set is created.
     */
    @Builder.Default
    private String stickerType = "regular";


    /**
     * Name of sticker set, to be used in t.me/addstickers/<name> URLs.
     * Can contain only english letters, digits and underscores.
     * Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”.
     * <bot_username> is case insensitive. 7-64 characters.
     */
    @NonNull
    @Deprecated
    private String emojis; ///< One or more emoji corresponding to the sticker
    @Deprecated
    private MaskPosition maskPosition; ///< Optional. Position where the mask should be placed on faces
    /**
     * Optional.
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers,
     * pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one
     * using multipart/form-data. More info on Sending Files »
     */
    @Deprecated
    private InputFile pngSticker;

    /**
     * Optional
     * TGS animation with the sticker, uploaded using multipart/form-data.
     * See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
     */
    @Deprecated
    private InputFile tgsSticker;

    /**
     * Optional
     * WEBM video with the sticker, uploaded using multipart/form-data.
     * See https://core.telegram.org/stickers#video-stickers for technical requirements
     */
    @Deprecated
    private InputFile webmSticker;

    /**
     * @deprecated Use {@link #setStickerType(String)}
     */
    @Deprecated
    public void setContainsMasks(boolean containsMasks) {
        if (containsMasks) {
            this.stickerType = "mask";
        } else {
            this.stickerType = "regular";
        }
    }

    /**
     * @deprecated Use {@link #getStickerType()} or {@link #isMask()}
     */
    @Deprecated
    public Boolean getContainsMasks() {
        return isMask();
    }

    @Deprecated
    public boolean isRegularSticker() {
        return "regular".equals(stickerType);
    }
    @Deprecated
    public boolean isMask() {
        return "mask".equals(stickerType);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }
        if (name.isEmpty() || name.length() > 64) {
            throw new TelegramApiValidationException("name must be between 1 and 64 characters", this);
        }
        if (title.isEmpty() || title.length() > 64) {
            throw new TelegramApiValidationException("title must be between 1 and 64 characters", this);
        }
        if (!Arrays.asList("regular", "mask", "custom_emoji").contains(stickerType)) {
            throw new TelegramApiValidationException("Stickertype must be 'regular', 'mask' or 'custom_emoji'", this);
        }
        if (!Arrays.asList("static", "animated", "video").contains(stickerFormat)) {
            throw new TelegramApiValidationException("StickerFormat must be 'static', 'animated', 'video'", this);
        }

        if (needsRepainting != null && !"custom_emoji".equals(stickerType)) {
            throw new TelegramApiValidationException("needsRepainting is only allowed with custom emojis", this);
        }
        if (pngSticker == null && tgsSticker == null && webmSticker == null) {
            if (stickers.isEmpty()) {
                throw new TelegramApiValidationException("sticker can't be empty", this);
            } else {
                for (InputSticker sticker : stickers) {
                    sticker.validate();
                }
            }
        } else {
            // Support deprecated option
            if (emojis.isEmpty()) {
                throw new TelegramApiValidationException("emojis can't be empty", this);
            }
            if ((pngSticker != null && tgsSticker != null) || (pngSticker != null && webmSticker != null)
                        || (tgsSticker != null && webmSticker != null)) {
                throw new TelegramApiValidationException("One of pngSticker, tgsSticker or webmSticker is needed", this);
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

    public static class CreateNewStickerSetBuilder {
        /**
         * @deprecated Use {@link #stickerType(String)} or {@link #setStickerType(String)}
         */
        @Tolerate
        @Deprecated
        public CreateNewStickerSet.CreateNewStickerSetBuilder containsMasks(@NonNull Boolean containsMasks) {
            if (containsMasks) {
                this.stickerType("mask");
            } else {
                this.stickerType("regular");
            }
            return this;
        }
    }
}
