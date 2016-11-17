package org.telegram.telegrambots.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.ChatMember;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to get information about a member of a chat.
 * Returns a ChatMember object on success.
 * @date 20 of May of 2016
 */
public class GetChatMember extends BotApiMethod<ChatMember> {
    public static final String PATH = "getChatMember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USERID_FIELD = "user_id";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USERID_FIELD)
    private Integer userId; ///< Unique identifier of the target user

    public GetChatMember() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public GetChatMember setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public GetChatMember setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public GetChatMember setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ChatMember deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<ChatMember> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ChatMember>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting chat member", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId can't be null", this);
        }
        if (userId == null) {
            throw new TelegramApiValidationException("UserId can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "GetChatMember{" +
                "chatId='" + chatId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
