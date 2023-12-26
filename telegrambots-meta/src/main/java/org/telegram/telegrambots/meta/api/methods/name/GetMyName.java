package org.telegram.telegrambots.meta.api.methods.name;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.name.BotName;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 6.7
 * Use this method to get the current bot name for the given user language.
 *
 * Returns BotName on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyName extends BotApiMethod<BotName> {
    public static final String PATH = "getMyDescription";

    private static final String LANGUAGE_CODE_FIELD = "language_code";

    /**
     * Optional
     * A two-letter ISO 639-1 language code or an empty string
     */
    @JsonProperty(LANGUAGE_CODE_FIELD)
    private String languageCode;

    @Override
    public BotName deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, BotName.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
