package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.games.GameHighScore;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 6.2
 * Use this method to get information about emoji stickers by their identifiers.
 * Returns an Array of Sticker on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCustomEmojiStickers extends BotApiMethod<ArrayList<Sticker>> {
    private static final String PATH = "getCustomEmojiStickers";

    private static final String CUSTOMEMOJIID_FIELD = "custom_emoji_ids";

    /**
     * List of custom emoji identifiers.
     * At most 200 custom emoji identifiers can be specified.
     */
    @Singular
    @JsonProperty(CUSTOMEMOJIID_FIELD)
    private List<String> customEmojiIds;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (customEmojiIds == null || customEmojiIds.isEmpty() || customEmojiIds.size() > 200) {
            throw new TelegramApiValidationException("CustomEmojiIds must be between 1 and 200", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<Sticker> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, Sticker.class);
    }
}
