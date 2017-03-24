/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.api.methods.games;




import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief Use this method to get data for high score tables.
 * Will return the score of the specified user and several of his neighbors in a game.
 * On success, returns an Array of GameHighScore objects.
 *
 * @note This method will currently return scores for the target user,
 * plus two of his closest neighbors on each side. Will also return the top three users
 * if the user and his neighbors are not among them.
 * Please note that this behavior is subject to change.
 *
 * @date 16 of September of 2016
 */
public class GetGameHighScores extends BotApiMethod<ArrayList<GameHighScore>> {
    public static final String PATH = "getGameHighScores";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String USER_ID_FIELD = "user_id";


    private String chat_id; ///< Optional	Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)

    private Integer message_id; ///< Optional	Required if inline_message_id is not specified. Unique identifier of the sent message

    private String inline_message_id; ///< Optional	Required if chat_id and message_id are not specified. Identifier of the inline message

    private Integer user_id; ///<Target user id

    public GetGameHighScores() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public Integer getMessageId() {
        return message_id;
    }

    public String getInlineMessageId() {
        return inline_message_id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public GetGameHighScores setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public GetGameHighScores setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public GetGameHighScores setMessageId(Integer messageId) {
        this.message_id = messageId;
        return this;
    }

    public GetGameHighScores setInlineMessageId(String inlineMessageId) {
        this.inline_message_id = inlineMessageId;
        return this;
    }

    public GetGameHighScores setUserId(Integer user_id) {
        this.user_id = user_id;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<GameHighScore> deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<ArrayList<GameHighScore>> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ArrayList<GameHighScore>>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting game high scores", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (user_id == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
        if (inline_message_id == null) {
            if (chat_id == null) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (message_id == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chat_id != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (message_id != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
    }

    @Override
    public String toString() {
        return "GetGameHighScores{" +
                "chatId='" + chat_id + '\'' +
                ", messageId=" + message_id +
                ", inlineMessageId='" + inline_message_id + '\'' +
                ", userId=" + user_id +
                '}';
    }
}
