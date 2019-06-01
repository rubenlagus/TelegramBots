package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.fields;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Objects;

/**
 * @author Robin Liu
 * @version 4.3
 * This object represents a parameter of the inline keyboard button used to automatically authorize a user.
 */
public class LoginUrl implements InputBotApiObject, Validable {

    private static final String URL_FIELD = "url";
    private static final String FORWARD_TEXT_FIELD = "forward_text";
    private static final String BOT_USERNAME_FIELD = "bot_username";
    private static final String REQUEST_WRITE_ACCESS_FIELD = "request_write_access";

    @JsonProperty(URL_FIELD)
    private String url; ///< An HTTP URL to be opened with user authorization data added to the query string when the button is pressed.
    @JsonProperty(FORWARD_TEXT_FIELD)
    private String forwardText; ///< Optional. New text of the button in forwarded messages.
    @JsonProperty(BOT_USERNAME_FIELD)
    private String botUsername; ///< Username of a bot, which will be used for user authorization.
    @JsonProperty(REQUEST_WRITE_ACCESS_FIELD)
    private Boolean requestWriteAccess; ///< Pass True to request the permission for your bot to send messages to the user.

    public LoginUrl() {
    }

    public LoginUrl(String url) {
        this.url = url;
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
    public String toString() {
        return "LoginUrl{" +
                "url='" + url + '\'' +
                ", forwardText='" + forwardText + '\'' +
                ", botUsername='" + botUsername + '\'' +
                ", requestWriteAccess=" + requestWriteAccess +
                '}';
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url == null || url.isEmpty()) {
            throw new TelegramApiValidationException("URL parameter can't be empty", this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUrl loginUrl = (LoginUrl) o;
        return Objects.equals(url, loginUrl.url) &&
                Objects.equals(forwardText, loginUrl.forwardText) &&
                Objects.equals(botUsername, loginUrl.botUsername) &&
                Objects.equals(requestWriteAccess, loginUrl.requestWriteAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, forwardText, botUsername, requestWriteAccess);
    }
}
