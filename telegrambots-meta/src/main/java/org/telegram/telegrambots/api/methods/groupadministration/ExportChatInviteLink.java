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
 * Use this method to export an invite link to a supergroup or a channel. The bot must be an administrator in the
 * chat for this to work and must have the appropriate admin rights. Returns exported invite link as String on success.
 */
public class ExportChatInviteLink extends BotApiMethod<String> {
    public static final String PATH = "exportChatInviteLink";

    private static final String CHATID_FIELD = "chat_id";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)

    public ExportChatInviteLink() {
        super();
    }

    public ExportChatInviteLink(String chatId) {
        super();
        this.chatId = checkNotNull(chatId);
    }

    public ExportChatInviteLink(Long chatId) {
        super();
        this.chatId = checkNotNull(chatId).toString();
    }

    public String getChatId() {
        return chatId;
    }

    public ExportChatInviteLink setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public ExportChatInviteLink setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public String deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<String> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<String>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error exporting invite link", result);
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
        return "ExportChatInviteLink{" +
                "chatId='" + chatId + '\'' +
                '}';
    }
}
