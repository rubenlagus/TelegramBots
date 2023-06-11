package org.telegram.telegrambots.meta.api.methods.name;

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
 * @version 6.7
 * Use this method to change the bot's name. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMyName extends BotApiMethodBoolean {
    public static final String PATH = "setMyName";

    private static final String NAME_FIELD = "name";
    private static final String LANGUAGE_CODE_FIELD = "language_code";

    /**
     * Optional
     * New bot name; 0-64 characters.
     * Pass an empty string to remove the dedicated name for the given language.
     */
    @JsonProperty(NAME_FIELD)
    private String name;

    /**
     * Optional
     * A two-letter ISO 639-1 language code.
     * If empty, the name will be shown to all users for whose language there is no dedicated name.
     */
    @JsonProperty(LANGUAGE_CODE_FIELD)
    private String languageCode;

    @Override
    public String getMethod() {
        return PATH;
    }
}
