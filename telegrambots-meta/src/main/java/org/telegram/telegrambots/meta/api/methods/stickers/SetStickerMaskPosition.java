package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.stickers.MaskPosition;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to change the mask position of a mask sticker.
 * The sticker must belong to a sticker set that was created by the bot.
 *
 * Returns True on success.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class SetStickerMaskPosition extends BotApiMethodBoolean {
    public static final String PATH = "setStickerMaskPosition";

    public static final String STICKER_FIELD = "sticker";
    public static final String MASK_POSITION_FIELD = "mask_position";

    /**
     * File identifier of the sticker
     */
    @JsonProperty(STICKER_FIELD)
    @NonNull
    private String sticker;

    /**
     * Optional.
     * Position where the mask should be placed on faces. For “mask” stickers only.
     * Omit the parameter to remove the mask position.
     */
    @JsonProperty(MASK_POSITION_FIELD)
    private MaskPosition maskPosition;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
        if (maskPosition != null) {
            maskPosition.validate();
        }
    }
}
