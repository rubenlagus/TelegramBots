package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to change search keywords assigned to a regular or custom emoji sticker.
 * The sticker must belong to a sticker set created by the bot.
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetStickerKeywords extends BotApiMethodBoolean {
    public static final String PATH = "setStickerKeywords";

    public static final String STICKER_FIELD = "sticker";
    public static final String KEYWORDS_FIELD = "keywords";

    /**
     * File identifier of the sticker
     */
    @JsonProperty(STICKER_FIELD)
    @NonNull
    private String sticker;

    /**
     * Optional.
     * List of 0-20 search keywords for the sticker with total length of up to 64 characters.
     * For “regular” and “custom_emoji” stickers only.
     */
    @JsonProperty(KEYWORDS_FIELD)
    @Singular
    private List<String> keywords;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
        if (keywords != null && keywords.size() > 20) {
            throw new TelegramApiValidationException("Keywords list must have between 0 and 20 items", this);
        }
    }
}
