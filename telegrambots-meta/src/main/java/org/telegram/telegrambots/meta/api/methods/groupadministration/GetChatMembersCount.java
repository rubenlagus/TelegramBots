package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get the number of members in a chat. Returns Int on success.
 */
public class GetChatMembersCount extends BotApiMethod<Integer> {
    public static final String PATH = "getChatMembersCount";

    private static final String CHATID_FIELD = "chat_id";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)

    public GetChatMembersCount() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public GetChatMembersCount setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public GetChatMembersCount setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Integer deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Integer> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Integer>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting chat members count", result);
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
    }

    @Override
    public String toString() {
        return "GetChatMembersCount{" +
                "chatId='" + chatId + '\'' +
                '}';
    }
}
