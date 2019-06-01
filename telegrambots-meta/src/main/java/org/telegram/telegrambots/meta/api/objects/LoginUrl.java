package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.3
 *
 * This object represents a parameter of the inline keyboard button used to automatically authorize a user.
 * Serves as a great replacement for the Telegram Login Widget when the user is coming from Telegram.
 * All the user needs to do is tap/click a button and confirm that they want to log in.
 */
@SuppressWarnings("unused")
public class LoginUrl implements InputBotApiObject, Validable {
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

    public LoginUrl() {
        super();
    }

    public LoginUrl(String url) {
        this.url = checkNotNull(url);
    }

    public String getUrl() {
        return url;
    }

    public LoginUrl setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getForwardText() {
        return forwardText;
    }

    public LoginUrl setForwardText(String forwardText) {
        this.forwardText = forwardText;
        return this;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public LoginUrl setBotUsername(String botUsername) {
        this.botUsername = botUsername;
        return this;

    }

    public Boolean getRequestWriteAccess() {
        return requestWriteAccess;
    }

    public LoginUrl setRequestWriteAccess(Boolean requestWriteAccess) {
        this.requestWriteAccess = requestWriteAccess;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url == null || url.isEmpty()) {
            throw new TelegramApiValidationException("Url parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "LoginUrl{" +
                "url='" + url + '\'' +
                ", forwardText='" + forwardText + '\'' +
                ", botUsername='" + botUsername + '\'' +
                ", requestWriteAccess=" + requestWriteAccess +
                '}';
    }
}
