package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to unban a previously kicked user in a supergroup. The user will not
 * return to the group automatically, but will be able to join via link, etc. The bot must be an
 * administrator in the group for this to work. Returns True on success.
 */
public class UnbanChatMember extends BotApiMethod<Boolean> {
    public static final String PATH = "unbanchatmember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    private Integer userId; ///< Required. Unique identifier of the target user

    public UnbanChatMember() {
        super();
    }

    public UnbanChatMember(String chatId, Integer userId) {
        super();
        this.chatId = checkNotNull(chatId);
        this.userId = checkNotNull(userId);
    }

    public UnbanChatMember(Long chatId, Integer userId) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.userId = checkNotNull(userId);
    }

    public String getChatId() {
        return chatId;
    }

    public UnbanChatMember setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public UnbanChatMember setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public UnbanChatMember setUserId(Integer userId) {
        this.userId = userId;
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
                throw new TelegramApiRequestException("Error unbanning chat member", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (userId == null) {
            throw new TelegramApiValidationException("UserId can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "UnbanChatMember{" +
                "chatId='" + chatId + '\'' +
                ", userId='" + userId +
                '}';
    }
}
