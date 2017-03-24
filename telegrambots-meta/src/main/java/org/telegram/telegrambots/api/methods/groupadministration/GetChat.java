package org.telegram.telegrambots.api.methods.groupadministration;




import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to get information about the chat. Returns Chat object on success.
 * @date 20 of May of 2016
 */
public class GetChat extends BotApiMethod<Chat> {
    public static final String PATH = "getChat";

    private static final String CHATID_FIELD = "chat_id";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    public GetChat() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public GetChat setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public GetChat setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
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
        if (chat_id == null) {
            throw new TelegramApiValidationException("ChatId can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "GetChat{" +
                "chatId='" + chat_id + '\'' +
                '}';
    }
}
