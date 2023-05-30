package org.telegram.telegrambots.meta.api.methods.description;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.description.BotShortDescription;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 6.6
 *
 * Use this method to get the current bot short description for the given user language.
 *
 * Returns BotShortDescription on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyShortDescription extends BotApiMethod<BotShortDescription> {
    public static final String PATH = "getMyShortDescription";

    private static final String LANGUAGE_CODE_FIELD = "language_code";

    /**
     * Optional
     * A two-letter ISO 639-1 language code.
     * If empty, the description will be applied to all users for whose language there is no dedicated description.
     */
    @JsonProperty(LANGUAGE_CODE_FIELD)
    private String languageCode;

    @Override
    public BotShortDescription deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, BotShortDescription.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
