package org.telegram.telegrambots.meta.api.methods;

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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to forward messages of any kind.
 *
 * Service messages and messages with protected content can't be forwarded.
 *
 * On success, the Message sent is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class ForwardMessage extends BotApiMethodMessage {
    public static final String PATH = "forwardmessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String FROMCHATID_FIELD = "from_chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String PROTECTCONTENT_FIELD = "protect_content";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (or username for channels)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(FROMCHATID_FIELD)
    @NonNull
    private String fromChatId; ///< Unique identifier for the chat where the original message was sent — User or GroupChat id
    @JsonProperty(MESSAGEID_FIELD)
    @NonNull
    private Integer messageId; ///< Unique message identifier
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification;
    @JsonProperty(PROTECTCONTENT_FIELD)
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Tolerate
    public void setFromChatId(@NonNull Long fromChatId) {
        this.fromChatId = fromChatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (fromChatId.isEmpty()) {
            throw new TelegramApiValidationException("FromChatId can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static abstract class ForwardMessageBuilder<C extends ForwardMessage, B extends ForwardMessageBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public ForwardMessageBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public ForwardMessageBuilder<C, B> fromChatId(@NonNull Long fromChatId) {
            this.fromChatId = fromChatId.toString();
            return this;
        }
    }
}
