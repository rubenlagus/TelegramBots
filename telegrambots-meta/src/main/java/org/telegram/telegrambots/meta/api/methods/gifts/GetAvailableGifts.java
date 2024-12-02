package org.telegram.telegrambots.meta.api.methods.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.gifts.Gifts;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 8.0
 * Returns the list of gifts that can be sent by the bot to users.
 * Requires no parameters.
 *
 * Returns a Gifts object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAvailableGifts extends BotApiMethod<Gifts> {
    public static final String PATH = "getAvailableGifts";

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Gifts deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Gifts.class);
    }
}
