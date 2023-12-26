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
 *
 * Use this method to change the bot's short description, which is shown on the bot's
 * profile page and is sent together with the link when users share the bot.
 *
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMyShortDescription extends BotApiMethodBoolean {
    public static final String PATH = "setMyShortDescription";

    private static final String SHORT_DESCRIPTION_FIELD = "short_description";
    private static final String LANGUAGE_CODE_FIELD = "language_code";

    /**
     * Optional
     * New short description for the bot; 0-120 characters.
     * Pass an empty string to remove the dedicated short description for the given language.
     */
    @JsonProperty(SHORT_DESCRIPTION_FIELD)
    private String shortDescription;
    /**
     * Optional
     * A two-letter ISO 639-1 language code.
     * If empty, the short description will be applied to all users for whose language there is no dedicated short description.
     */
    @JsonProperty(LANGUAGE_CODE_FIELD)
    private String languageCode;

    @Override
    public String getMethod() {
        return PATH;
    }
}
