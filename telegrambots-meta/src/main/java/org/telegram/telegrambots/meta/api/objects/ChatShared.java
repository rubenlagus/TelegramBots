package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object contains information about the chat whose identifier was shared with the bot using a KeyboardButtonRequestChat button.
 * @author Ruben Bermudez
 * @version 6.5
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatShared implements BotApiObject {

    private static final String REQUESTID_FIELD = "request_id";
    private static final String CHATID_FIELD = "chat_id";

    /**
     * Identifier of the request
     */
    @JsonProperty(REQUESTID_FIELD)
    private String requestId;
    /**
     * Identifier of the shared chat.
     * This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
     * @apiNote The bot may not have access to the user and could be unable to use this identifier, unless the user is already known to the bot by some other means.
     */
    @JsonProperty(CHATID_FIELD)
    private Long chatId;
}
