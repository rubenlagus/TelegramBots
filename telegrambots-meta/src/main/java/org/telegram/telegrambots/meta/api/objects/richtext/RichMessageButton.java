package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.CopyTextButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.DisabledButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.SwitchInlineQueryChosenChat;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * This object represents a button in a RichMessage.
 *
 * @apiNote Exactly one of the fields other than text and style must be used to specify
 * the type of the button.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RichMessageButton implements Validable, BotApiObject {
    private static final String TEXT_FIELD = "text";
    private static final String STYLE_FIELD = "style";
    private static final String URL_FIELD = "url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";
    private static final String WEB_APP_FIELD = "web_app";
    private static final String LOGIN_URL_FIELD = "login_url";
    private static final String SWITCH_INLINE_QUERY_FIELD = "switch_inline_query";
    private static final String SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD = "switch_inline_query_current_chat";
    private static final String SWITCH_INLINE_QUERY_CHOSEN_CHAT_FIELD = "switch_inline_query_chosen_chat";
    private static final String COPY_TEXT_FIELD = "copy_text";
    private static final String DISABLED_FIELD = "disabled";

    /**
     * Text of the button.
     *
     * @apiNote May contain only plain text, RichTextCustomEmoji and RichTextDateTime entities.
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;
    /**
     * Optional.
     * Style of the button. Must be one of "danger" (red), "success" (green), "primary" (blue)
     * or "link" (the button is shown as a regular link without borders).
     * If omitted, then an app-specific style is used.
     *
     * @apiNote The style "link" is allowed only for callback buttons.
     */
    @JsonProperty(STYLE_FIELD)
    private String style;
    /**
     * Optional.
     * HTTP or tg:// URL to be opened when the button is pressed.
     * Links tg://user?id=&lt;user_id&gt; can be used to mention a user by their identifier without
     * using a username, if this is allowed by their privacy settings.
     */
    @JsonProperty(URL_FIELD)
    private String url;
    /**
     * Optional.
     * Data to be sent in a callback query to the bot when the button is pressed, 1-64 bytes
     */
    @JsonProperty(CALLBACK_DATA_FIELD)
    private String callbackData;
    /**
     * Optional.
     * Description of the Web App that will be launched when the user presses the button.
     *
     * @apiNote Available only in private chats between a user and the bot.
     * @apiNote Not supported for messages sent on behalf of a business account.
     */
    @JsonProperty(WEB_APP_FIELD)
    private WebAppInfo webApp;
    /**
     * Optional.
     * An HTTPS URL used to automatically authorize the user.
     *
     * @apiNote Not supported for ephemeral messages.
     * @apiNote LoginUrl.botUsername is not supported here.
     */
    @JsonProperty(LOGIN_URL_FIELD)
    private LoginUrl loginUrl;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats, open that chat
     * and insert the bot's username and the specified inline query in the input field.
     * May be empty, in which case just the bot's username will be inserted.
     *
     * @apiNote Not supported for messages sent in channel direct messages chats and on behalf of a business account.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_FIELD)
    private String switchInlineQuery;
    /**
     * Optional.
     * If set, pressing the button will insert the bot's username and the specified inline query
     * in the current chat's input field. May be empty, in which case only the bot's username will be inserted.
     *
     * @apiNote Not supported in channels and for messages sent in channel direct messages chats
     * and on behalf of a business account.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_CURRENT_CHAT_FIELD)
    private String switchInlineQueryCurrentChat;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats of the specified
     * type, open that chat and insert the bot's username and the specified inline query in the input field.
     *
     * @apiNote Not supported for messages sent in channel direct messages chats and on behalf of a business account.
     */
    @JsonProperty(SWITCH_INLINE_QUERY_CHOSEN_CHAT_FIELD)
    private SwitchInlineQueryChosenChat switchInlineQueryChosenChat;
    /**
     * Optional.
     * A button that copies the specified text to the clipboard
     */
    @JsonProperty(COPY_TEXT_FIELD)
    private CopyTextButton copyText;
    /**
     * Optional.
     * If set, then the button is disabled and does nothing
     */
    @JsonProperty(DISABLED_FIELD)
    private DisabledButton disabled;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (loginUrl != null) {
            loginUrl.validate();
        }
        if (webApp != null) {
            webApp.validate();
        }
        if (switchInlineQueryChosenChat != null) {
            switchInlineQueryChosenChat.validate();
        }
        if (copyText != null) {
            copyText.validate();
        }
    }
}
