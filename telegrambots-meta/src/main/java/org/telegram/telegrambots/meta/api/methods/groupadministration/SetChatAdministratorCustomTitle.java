package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.5
 * Use this method to set a custom title for an administrator in a supergroup promoted by the bot.
 * Returns True on success.
 */
public class SetChatAdministratorCustomTitle extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatAdministratorCustomTitle";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USERID_FIELD = "user_id";
    private static final String CUSTOMTITLE_FIELD = "custom_title";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(USERID_FIELD)
    private Integer userId; ///< Unique identifier of the target user
    @JsonProperty(CUSTOMTITLE_FIELD)
    private String customTitle; ///< New custom title for the administrator; 0-16 characters, emoji are not allowed

    public SetChatAdministratorCustomTitle() {
        super();
    }

    public SetChatAdministratorCustomTitle(String chatId, Integer userId, String customTitle) {
        super();
        this.chatId = checkNotNull(chatId);
        this.userId = checkNotNull(userId);
        this.customTitle = checkNotNull(customTitle);
    }

    public SetChatAdministratorCustomTitle(Long chatId, Integer userId, String customTitle) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.userId = checkNotNull(userId);
        this.customTitle = checkNotNull(customTitle);
    }

    public String getChatId() {
        return chatId;
    }

    public SetChatAdministratorCustomTitle setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetChatAdministratorCustomTitle setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public SetChatAdministratorCustomTitle setUserId(Integer userId) {
        checkNotNull(userId);
        this.userId = userId;
        return this;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public SetChatAdministratorCustomTitle setCustomTitle(String customTitle) {
        checkNotNull(customTitle);
        this.customTitle = customTitle;
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
                throw new TelegramApiRequestException("Error setting chat description", result);
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
        if (userId == null || userId == 0) {
            throw new TelegramApiValidationException("UserId can't be empty", this);
        }
        if (customTitle == null) {
            throw new TelegramApiValidationException("CustomTitle can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "SetChatDescription{" +
                "chatId='" + chatId + '\'' +
                "userId='" + userId + '\'' +
                ", customTitle='" + customTitle + '\'' +
                '}';
    }
}
