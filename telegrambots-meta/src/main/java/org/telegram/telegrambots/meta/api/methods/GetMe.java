package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * A simple method for testing your bot's auth token. Requires no parameters.
 * Returns basic information about the bot in form of a User object
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GetMe extends BotApiMethod<User> {
    public static final String PATH = "getme";

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public User deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseDefault(answer, "Error getting me");
    }

    @Override
    public void validate() throws TelegramApiValidationException {
    }
}
