package org.telegram.telegrambots.meta.api.methods.stickers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;
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
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class CreateNewStickerSet extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "createNewStickerSet";

    public static final String USER_ID_FIELD = "user_id";
    public static final String STICKERS_FIELD = "stickers";
    public static final String NAME_FIELD = "name";
    public static final String TITLE_FIELD = "title";
    public static final String STICKER_TYPE_FIELD = "sticker_type";
    public static final String NEEDS_REPAINTING_FIELD = "needs_repainting";

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
        if (needsRepainting != null && !"custom_emoji".equals(stickerType)) {
            throw new TelegramApiValidationException("needsRepainting is only allowed with custom emojis", this);
        }
        if (stickers.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be empty", this);
        } else {
            for (InputSticker sticker : stickers) {
                sticker.validate();
            }
        }
    }
}
