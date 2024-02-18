package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to delete a sticker set that was created by the bot.
 *
 * Returns True on success.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Builder
public class DeleteStickerSet extends BotApiMethodBoolean {
    public static final String PATH = "deleteStickerSet";

    public static final String NAME_FIELD = "name";

    /**
     * Sticker set name
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name.isEmpty() || name.length() > 64) {
            throw new TelegramApiValidationException("name must be between 1 and 64 characters", this);
        }
    }
}
