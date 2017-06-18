package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send text messages. On success, the sent Message is returned.
 */
public class ForwardMessage extends BotApiMethod<Message> {
    public static final String PATH = "forwardmessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String FROMCHATID_FIELD = "from_chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (or username for channels)
    @JsonProperty(FROMCHATID_FIELD)
    private String fromChatId; ///< Unique identifier for the chat where the original message was sent — User or GroupChat id
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId; ///< Unique message identifier
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification;

    public ForwardMessage() {
        super();
    }

    public ForwardMessage(String chatId, String fromChatId, Integer messageId) {
        this();
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(fromChatId);
        this.chatId = chatId;
        this.fromChatId = fromChatId;
        this.messageId = messageId;
    }

    public ForwardMessage(String chatId, Long fromChatId, Integer messageId) {
        this();
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(fromChatId);
        this.chatId = chatId;
        this.fromChatId = fromChatId.toString();
        this.messageId = messageId;
    }

    public ForwardMessage(Long chatId, String fromChatId, Integer messageId) {
        this();
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(fromChatId);
        this.chatId = chatId.toString();
        this.fromChatId = fromChatId;
        this.messageId = messageId;
    }

    public ForwardMessage(Long chatId, Long fromChatId, Integer messageId) {
        this();
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(fromChatId);
        this.chatId = chatId.toString();
        this.fromChatId = fromChatId.toString();
        this.messageId = messageId;
    }

    public String getChatId() {
        return chatId;
    }

    public ForwardMessage setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public ForwardMessage setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getFromChatId() {
        return fromChatId;
    }

    public ForwardMessage setFromChatId(String fromChatId) {
        this.fromChatId = fromChatId;
        return this;
    }

    public ForwardMessage setFromChatId(Long fromChatId) {
        Objects.requireNonNull(fromChatId);
        this.fromChatId = fromChatId.toString();
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public ForwardMessage setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public ForwardMessage enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public ForwardMessage disableNotification() {
        this.disableNotification = true;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (fromChatId == null || fromChatId.isEmpty()) {
            throw new TelegramApiValidationException("FromChatId can't be empty", this);
        }
        if (messageId == null) {
            throw new TelegramApiValidationException("MessageId can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error forwarding message", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public String toString() {
        return "ForwardMessage{" +
                "chatId='" + chatId + '\'' +
                ", fromChatId='" + fromChatId + '\'' +
                ", messageId=" + messageId +
                ", disableNotification=" + disableNotification +
                '}';
    }
}
