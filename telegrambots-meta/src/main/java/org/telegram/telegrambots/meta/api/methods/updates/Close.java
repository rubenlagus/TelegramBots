package org.telegram.telegrambots.meta.api.methods.updates;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * Use this method to close the bot instance before moving it from one local server to another.
 * You need to delete the webhook before calling this method to ensure that the bot isn't launched again after server restart.
 * The method will return error 429 in the first 10 minutes after the bot is launched.
 * Returns True on success.
 * Requires no parameters.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class Close extends BotApiMethod<WebhookInfo> {
    public static final String PATH = "close";

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public WebhookInfo deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<WebhookInfo> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<WebhookInfo>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error logging out info", result);
            }
        } catch (IOException e2) {
            throw new TelegramApiRequestException("Unable to deserialize response", e2);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
    }
}
