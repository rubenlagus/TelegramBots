package org.telegram.telegrambots.api.methods.groupadministration;

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
 * Use this method to change the title of a chat. Titles can't be changed for private chats.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * @apiNote In regular groups (non-supergroups), this method will only work if the ‘All Members Are Admins’ setting is off in the target group.
 */
public class SetChatTitle extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatTitle";

    private static final String CHATID_FIELD = "chat_id";
    private static final String TITLE_FIELD = "title";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Required. New chat title, 1-255 characters

    public SetChatTitle() {
        super();
    }

    public SetChatTitle(String chatId, String title) {
        super();
        this.chatId = checkNotNull(chatId);
        this.title = checkNotNull(title);
    }

    public SetChatTitle(Long chatId, String title) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.title = checkNotNull(title);
    }

    public String getChatId() {
        return chatId;
    }

    public SetChatTitle setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetChatTitle setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SetChatTitle setTitle(String title) {
        Objects.requireNonNull(title);
        this.title = title;
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
                throw new TelegramApiRequestException("Error setting chat title", result);
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
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "SetChatTitle{" +
                "chatId='" + chatId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
