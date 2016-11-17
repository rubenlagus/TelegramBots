package org.telegram.telegrambots.api.methods.updates;

import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.WebhookInfo;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief Use this method to get current webhook status.
 * Requires no parameters.
 * On success, returns a WebhookInfo object.
 * Will throw an error, if the bot is using getUpdates.
 *
 * @date 12 of August of 2016
 */
public class GetWebhookInfo extends BotApiMethod<WebhookInfo> {
    public static final String PATH = "getwebhookinfo";

    public GetWebhookInfo() {
        super();
    }

    @Override
    public String toString() {
        return "GetWebhookInfo{}";
    }

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
                throw new TelegramApiRequestException("Error getting webhook info", result);
            }
        } catch (IOException e2) {
            throw new TelegramApiRequestException("Unable to deserialize response", e2);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
    }
}
