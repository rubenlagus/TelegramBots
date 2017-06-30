package org.telegram.telegrambots.api.methods.pinnedmessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to pin a message in a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 */
public class PinChatMessage extends BotApiMethod<Boolean> {
    public static final String PATH = "pinChatMessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId; ///< Required. Identifier of a message to pin
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Pass true, if it is not necessary to send a notification to all group members about the new pinned message

    public PinChatMessage() {
        super();
    }

    public PinChatMessage(String chatId, Integer messageId) {
        super();
        this.chatId = checkNotNull(chatId);
        this.messageId = checkNotNull(messageId);
    }

    public PinChatMessage(Long chatId, Integer messageId) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.messageId = checkNotNull(messageId);
    }

    public String getChatId() {
        return chatId;
    }

    public PinChatMessage setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public PinChatMessage setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public PinChatMessage setMessageId(Integer messageId) {
        Objects.requireNonNull(messageId);
        this.messageId = messageId;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public PinChatMessage setDisableNotification(Boolean disableNotification) {
        this.disableNotification = disableNotification;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error pinning chat message", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
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

    @Override
    public String toString() {
        return "PinChatMessage{" +
                "chatId='" + chatId + '\'' +
                ", messageId=" + messageId +
                ", disableNotification=" + disableNotification +
                '}';
    }
}
