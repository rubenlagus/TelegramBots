package org.telegram.telegrambots.meta.api.methods.pinnedmessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to add a message to the list of pinned messages in a chat.
 * Returns True on success.
 *
 * @apiNote If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must
 * have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PinChatMessage extends BotApiMethod<Boolean> {
    public static final String PATH = "pinChatMessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(MESSAGEID_FIELD)
    @NonNull
    private Integer messageId; ///< Required. Identifier of a message to pin
    /**
     * Pass True, if it is not necessary to send a notification to all chat members about the new pinned message.
     * Notifications are always disabled in channels.
     */
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseDefault(answer, "Error pinning chat message");
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (messageId == null) {
            throw new TelegramApiValidationException("MessageId parameter can't be null", this);
        }
    }
}
