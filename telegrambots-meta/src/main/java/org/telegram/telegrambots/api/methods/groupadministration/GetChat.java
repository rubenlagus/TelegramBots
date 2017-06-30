package org.telegram.telegrambots.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get information about the chat. Returns Chat object on success.
 */
public class GetChat extends BotApiMethod<Chat> {
    public static final String PATH = "getChat";

    private static final String CHATID_FIELD = "chat_id";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)

    public GetChat() {
        super();
    }

    public GetChat(String chatId) {
        super();
        this.chatId = checkNotNull(chatId);
    }

    public GetChat(Long chatId) {
        super();
        this.chatId = checkNotNull(chatId).toString();
    }

    public String getChatId() {
        return chatId;
    }

    public GetChat setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public GetChat setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Chat deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Chat> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Chat>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting chat", result);
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
        return "GetChat{" +
                "chatId='" + chatId + '\'' +
                '}';
    }
}
