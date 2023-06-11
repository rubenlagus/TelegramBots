package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

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
import org.telegram.telegrambots.meta.api.objects.adminrights.ChatAdministratorRights;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.5
 *
 * This object defines the criteria used to request a suitable chat.
 * The identifier of the selected chat will be shared with the bot when the corresponding button is pressed.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class KeyboardButtonRequestChat implements Validable, BotApiObject {

    private static final String REQUESTID_FIELD = "request_id";
    private static final String CHATISCHANNEL_FIELD = "chat_is_channel";
    private static final String CHATISFORUM_FIELD = "chat_is_forum";
    private static final String CHATHASUSERNAME_FIELD = "chat_has_username";
    private static final String CHATISCREATED_FIELD = "chat_is_created";
    private static final String USERADMINISTRATORRIGHTS_FIELD = "user_administrator_rights";
    private static final String BOTADMINISTRATORRIGHTS_FIELD = "bot_administrator_rights";
    private static final String BOTISMEMBER_FIELD = "bot_is_member";
    private static final String SWITCH_INLINE_QUERY_CHOSEN_CHAT_FIELD = "switch_inline_query_chosen_chat";

    /**
     * Signed 32-bit identifier of the request
     */
    @JsonProperty(REQUESTID_FIELD)
    @NonNull
    private String requestId;
    /**
     * Pass True to request a channel chat, pass False to request a group or a supergroup chat.
     */
    @JsonProperty(CHATISCHANNEL_FIELD)
    @NonNull
    private Boolean chatIsChannel;
    /**
     * Optional.
     * Pass True to request a forum supergroup, pass False to request a non-forum chat.
     * If not specified, no additional restrictions are applied.
     */
    @JsonProperty(CHATISFORUM_FIELD)
    private Boolean chatIsForum;
    /**
     * Optional.
     * Pass True to request a supergroup or a channel with a username, pass False to request a chat without a username.
     * If not specified, no additional restrictions are applied.
     */
    @JsonProperty(CHATHASUSERNAME_FIELD)
    private Boolean chatHasUsername;
    /**
     * Optional.
     * Pass True to request a chat owned by the user.
     * Otherwise, no additional restrictions are applied.
     */
    @JsonProperty(CHATISCREATED_FIELD)
    private Boolean chatIsCreated;
    /**
     * Optional.
     * A JSON-serialized object listing the required administrator rights of the user in the chat.
     * If not specified, no additional restrictions are applied.
     */
    @JsonProperty(USERADMINISTRATORRIGHTS_FIELD)
    private ChatAdministratorRights userAdministratorRights;
    /**
     * Optional.
     * A JSON-serialized object listing the required administrator rights of the bot in the chat.
     * The rights must be a subset of user_administrator_rights.
     * If not specified, no additional restrictions are applied.
     */
    @JsonProperty(BOTADMINISTRATORRIGHTS_FIELD)
    private ChatAdministratorRights botAdministratorRights;
    /**
     * Optional.
     * Pass True to request a chat with the bot as a member.
     * Otherwise, no additional restrictions are applied.
     */
    @JsonProperty(BOTISMEMBER_FIELD)
    private Boolean botIsMember;
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats of the specified type,
     * open that chat and insert the bot's username and the specified inline query in the input field
     */
    @JsonProperty(SWITCH_INLINE_QUERY_CHOSEN_CHAT_FIELD)
    private SwitchInlineQueryChosenChat switchInlineQueryChosenChat;


    @Override
    public void validate() throws TelegramApiValidationException {
        if (requestId.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
        if (userAdministratorRights != null) {
            userAdministratorRights.validate();
        }
        if (botAdministratorRights != null) {
            botAdministratorRights.validate();
        }
        if (switchInlineQueryChosenChat != null) {
            switchInlineQueryChosenChat.validate();
        }
    }
}
