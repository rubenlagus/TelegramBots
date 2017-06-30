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
 * Use this method to change the description of a supergroup or channels.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 */
public class SetChatDescription extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatDescription";

    private static final String CHATID_FIELD = "chat_id";
    private static final String DESCRIPTION_FIELD = "description";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< New chat description, 0-255 characters

    public SetChatDescription() {
        super();
    }

    public SetChatDescription(String chatId, String description) {
        super();
        this.chatId = checkNotNull(chatId);
        this.description = checkNotNull(description);
    }

    public SetChatDescription(Long chatId, String description) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.description = checkNotNull(description);
    }

    public String getChatId() {
        return chatId;
    }

    public SetChatDescription setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetChatDescription setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SetChatDescription setDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
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
        if (description == null) {
            throw new TelegramApiValidationException("Description can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "SetChatDescription{" +
                "chatId='" + chatId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
