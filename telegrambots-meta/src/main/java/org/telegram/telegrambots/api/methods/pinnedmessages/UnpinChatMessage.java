package org.telegram.telegrambots.api.methods.pinnedmessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to unpin a message in a supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 */
public class UnpinChatMessage extends BotApiMethod<Boolean> {
    public static final String PATH = "unpinChatMessage";

    private static final String CHATID_FIELD = "chat_id";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)

    public UnpinChatMessage() {
        super();
    }

    public UnpinChatMessage(String chatId) {
        super();
        this.chatId = checkNotNull(chatId);
    }

    public UnpinChatMessage(Long chatId) {
        super();
        this.chatId = checkNotNull(chatId).toString();
    }

    public String getChatId() {
        return chatId;
    }

    public UnpinChatMessage setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public UnpinChatMessage setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
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
                throw new TelegramApiRequestException("Error unpinning chat message", result);
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
    }

    @Override
    public String toString() {
        return "UnpinChatMessage{" +
                "chatId='" + chatId + '\'' +
                '}';
    }
}
