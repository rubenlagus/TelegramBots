package org.telegram.telegrambots.meta.api.methods.pinnedmessages;

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
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to remove a message from the list of pinned messages in a chat.
 * In private chats and channel direct messages chats, all messages can be unpinned.
 * Conversely, the bot must be an administrator with the 'can_pin_messages' right or the 'can_edit_messages' right
 * to unpin messages in groups and channels respectively.
 * Returns True on success.
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
public class UnpinChatMessage extends BotApiMethodBoolean {
    public static final String PATH = "unpinChatMessage";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    /**
     * Required.
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername).
     * In private chats and channel direct messages chats, all messages can be unpinned.
     * In groups and channels, the bot must be an administrator with appropriate rights.
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Optional
     * Identifier of the message to unpin.
     *
     * @apiNote Required if business_connection_id is specified.
     * @apiNote If not specified, the most recent pinned message (by sending date) will be unpinned.
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message will be unpinned
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
        Validations.requiredChatId(chatId, this);
    }

    public static abstract class UnpinChatMessageBuilder<C extends UnpinChatMessage, B extends UnpinChatMessageBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public UnpinChatMessageBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
