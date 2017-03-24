package org.telegram.telegrambots.api.methods.groupadministration;




import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.ChatMember;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to get a list of administrators in a chat.
 * An Array of ChatMember objects is returned on success,
 * containing information about all chat administrators except other bots.
 * If the chat is a group or a supergroup and no administrators were appointed,
 * only the creator will be returned.
 * @date 20 of May of 2016
 */
public class GetChatAdministrators extends BotApiMethod<ArrayList<ChatMember>> {
    public static final String PATH = "getChatAdministrators";

    private static final String CHATID_FIELD = "chat_id";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    public GetChatAdministrators() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public GetChatAdministrators setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public GetChatAdministrators setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<ChatMember> deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<ArrayList<ChatMember>> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ArrayList<ChatMember>>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting chat administrators", result);
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
        return "GetChatAdministrators{" +
                "chatId='" + chat_id + '\'' +
                '}';
    }
}
