package org.telegram.telegrambots.api.methods;



import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send text messages. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class ForwardMessage extends BotApiMethod<Message> {
    public static final String PATH = "forwardmessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String FROMCHATID_FIELD = "from_chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";


    private String chat_id; ///< Unique identifier for the chat to send the message to (or username for channels)

    private String from_chat_id; ///< Unique identifier for the chat where the original message was sent — User or GroupChat id

    private Integer message_id; ///< Unique message identifier
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */

    private Boolean disable_notification;

    public ForwardMessage() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public ForwardMessage setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public ForwardMessage setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public String getFromChatId() {
        return from_chat_id;
    }

    public ForwardMessage setFromChatId(String fromChatId) {
        this.from_chat_id = fromChatId;
        return this;
    }

    public Integer getMessageId() {
        return message_id;
    }

    public ForwardMessage setMessageId(Integer messageId) {
        this.message_id = messageId;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public ForwardMessage enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public ForwardMessage disableNotification() {
        this.disable_notification = true;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chat_id == null) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (from_chat_id == null) {
            throw new TelegramApiValidationException("FromChatId can't be empty", this);
        }
        if (message_id == null) {
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
                "chatId='" + chat_id + '\'' +
                ", fromChatId=" + from_chat_id +
                ", messageId=" + message_id +
                '}';
    }
}
