package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.3
 *
 * This object represents a parameter of the inline keyboard button used to automatically authorize a user.
 * Serves as a great replacement for the Telegram Login Widget when the user is coming from Telegram.
 * All the user needs to do is tap/click a button and confirm that they want to log in.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUrl implements Validable, BotApiObject {
    private static final String URL_FIELD = "url";
    private static final String FORWARD_TEXT_FIELD = "forward_text";
    private static final String BOT_USERNAME_FIELD = "bot_username";
    private static final String REQUEST_WRITE_ACCESS_FIELD = "request_write_access";

    /**
     * An HTTP URL to be opened with user authorization data added to the query string when the button is pressed.
     * If the user refuses to provide authorization data, the original URL without information about the user will be opened.
     * The data added is the same as described in Receiving authorization data.
     *
     * @implNote You must always check the hash of the received data to verify the authentication and the integrity
     * of the data as described in Checking authorization.
     */
    @JsonProperty(URL_FIELD)
    @NonNull
    private String url;
    @JsonProperty(FORWARD_TEXT_FIELD)
    private String forwardText; ///< Optional. New text of the button in forwarded messages.
    /**
     * Optional. Username of a bot, which will be used for user authorization. See Setting up a bot for more details.
     * If not specified, the current bot's username will be assumed.
     * The url's domain must be the same as the domain linked with the bot.
     */
    @JsonProperty(BOT_USERNAME_FIELD)
    private String botUsername; ///< Optional. Animation duration
    @JsonProperty(REQUEST_WRITE_ACCESS_FIELD)
    private Boolean requestWriteAccess; ///< Optional. Pass True to request the permission for your bot to send messages to the user.

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url == null || url.isEmpty()) {
            throw new TelegramApiValidationException("Url parameter can't be empty", this);
        }
    }
}
