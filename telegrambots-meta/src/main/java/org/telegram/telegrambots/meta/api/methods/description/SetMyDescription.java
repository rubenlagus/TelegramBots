package org.telegram.telegrambots.meta.api.methods.description;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * Use this method to change the bot's description, which is shown in the chat with the bot if the chat is empty.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMyDescription extends BotApiMethodBoolean {
    public static final String PATH = "setMyDescription";

    private static final String DESCRIPTION_FIELD = "description";
    private static final String LANGUAGE_CODE_FIELD = "language_code";

    /**
     * Optional
     * New bot description; 0-512 characters.
     * Pass an empty string to remove the dedicated description for the given language.
     */
    @JsonProperty(DESCRIPTION_FIELD)
    private String description;
    /**
     * Optional
     * A two-letter ISO 639-1 language code.
     * If empty, the description will be applied to all users for whose language there is no dedicated description.
     */
    @JsonProperty(LANGUAGE_CODE_FIELD)
    private String languageCode;

    @Override
    public String getMethod() {
        return PATH;
    }
}
