package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to delete a sticker from a set created by the bot. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteStickerFromSet extends BotApiMethod<Boolean> {
    private static final String PATH = "deleteStickerFromSet";

    private static final String STICKER_FIELD = "sticker";

    @JsonProperty(STICKER_FIELD)
    @NonNull
    private String sticker; ///< File identifier of the sticker

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseDefault(answer, "Error deleting sticker from set");
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker == null || sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
    }
}
