package org.telegram.telegrambots.meta.api.methods.pinnedmessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

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
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class PinChatMessage extends BotApiMethodBoolean {
    public static final String PATH = "pinChatMessage";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    /**
     * Required.
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Required.
     * Identifier of a message to pin
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    @NonNull
    private Integer messageId;
    /**
     * Pass True, if it is not necessary to send a notification to all chat members about the new pinned message.
     * Notifications are always disabled in channels.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message will be pinned
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
    }

    public static abstract class PinChatMessageBuilder<C extends PinChatMessage, B extends PinChatMessageBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public PinChatMessageBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
