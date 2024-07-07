package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.stickers.InputSticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to add a new sticker to a set created by the bot.
 * Emoji sticker sets can have up to 200 stickers.
 * Other sticker sets can have up to 120 stickers.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddStickerToSet extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "addStickerToSet";

    public static final String USERID_FIELD = "user_id";
    public static final String NAME_FIELD = "name";
    public static final String STICKER_FIELD = "sticker";

    /**
     * User identifier of sticker set owner
     */
    @NonNull
    private Long userId;
    /**
     * Sticker set name
     */
    @NonNull
    private String name;
    /**
     * A JSON-serialized object with information about the added sticker.
     * If exactly the same sticker had already been added to the set, then the set isn't changed.
     */
    @NonNull
    private InputSticker sticker;

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
        sticker.validate();
    }
}
