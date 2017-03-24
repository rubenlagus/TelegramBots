package org.telegram.telegrambots.api.methods.groupadministration;




import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
 * @date 20 of May of 2016
 */
public class LeaveChat extends BotApiMethod<Boolean> {
    public static final String PATH = "leaveChat";

    private static final String CHATID_FIELD = "chat_id";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    public LeaveChat() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public LeaveChat setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public LeaveChat setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
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
                throw new TelegramApiRequestException("Error leaving chat", result);
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
        return "LeaveChat{" +
                "chatId='" + chat_id + '\'' +
                '}';
    }
}
