package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to change the list of emoji assigned to a regular or custom emoji sticker.
 * The sticker must belong to a sticker set created by the bot.
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
public class SetStickerEmojiList extends BotApiMethodBoolean {
    public static final String PATH = "setStickerEmojiList";

    public static final String STICKER_FIELD = "sticker";
    public static final String EMOJI_LIST_FIELD = "emoji_list";

    /**
     * File identifier of the sticker
     */
    @JsonProperty(STICKER_FIELD)
    @NonNull
    private String sticker;

    /**
     * List of 1-20 emoji associated with the sticker
     */
    @JsonProperty(EMOJI_LIST_FIELD)
    @NonNull
    @Singular("emoji")
    private List<String> emojiList;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
        if (emojiList.isEmpty() || emojiList.size() > 20) {
            throw new TelegramApiValidationException("Emoji list must have between 1 and 20 items", this);
        }
    }
}
