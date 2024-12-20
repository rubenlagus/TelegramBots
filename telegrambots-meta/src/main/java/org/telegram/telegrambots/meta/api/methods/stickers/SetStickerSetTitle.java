package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to set the title of a created sticker set.
 *
 * Returns True on success.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetStickerSetTitle extends BotApiMethodBoolean {
    public static final String PATH = "setStickerSetTitle";

    public static final String NAME_FIELD = "name";
    public static final String TITLE_FIELD = "title";

    /**
     * Sticker set name
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;

    /**
     * Sticker set title, 1-64 characters
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name.isEmpty() || name.length() > 64) {
            throw new TelegramApiValidationException("name must be between 1 and 64 characters", this);
        }
        if (title.isEmpty() || title.length() > 64) {
            throw new TelegramApiValidationException("title must be between 1 and 64 characters", this);
        }
    }
}
