package org.telegram.telegrambots.meta.api.methods.updates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * Use this method to get current webhook status.
 * Requires no parameters.
 * On success, returns a WebhookInfo object.
 * Will throw an error, if the bot is using getUpdates.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GetWebhookInfo extends BotApiMethod<WebhookInfo> {
    public static final String PATH = "getwebhookinfo";

    @Override
    public String getMethod() {
        return PATH;
    }


    @Override
    public WebhookInfo deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, WebhookInfo.class);
    }
}
