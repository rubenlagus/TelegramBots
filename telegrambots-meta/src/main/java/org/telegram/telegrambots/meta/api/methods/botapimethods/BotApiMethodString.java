package org.telegram.telegrambots.meta.api.methods.botapimethods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 9.6
 *
 * A method of Telegram Bots Api that returns a String result
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BotApiMethodString extends BotApiMethod<String> {
    @Override
    public String deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, String.class);
    }

    public static abstract class BotApiMethodStringBuilder<C extends BotApiMethodString, B extends BotApiMethodStringBuilder<C, B>> extends BotApiMethodBuilder<String, C, B> {

    }
}
