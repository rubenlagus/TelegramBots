package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to move a sticker in a set created by the bot to a specific position.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetStickerPositionInSet extends BotApiMethodBoolean {
    private static final String PATH = "setStickerPositionInSet";

    private static final String STICKER_FIELD = "sticker";
    private static final String POSITION_FIELD = "position";

    @JsonProperty(STICKER_FIELD)
    @NonNull
    private String sticker; ///< File identifier of the sticker
    @JsonProperty(POSITION_FIELD)
    @NonNull
    private Integer position; ///< New sticker position in the set, zero-based

    @Override
    public String getMethod() {
        return PATH;
    }


    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
        if (position < 0) {
            throw new TelegramApiValidationException("position can't be null", this);
        }
    }
}
