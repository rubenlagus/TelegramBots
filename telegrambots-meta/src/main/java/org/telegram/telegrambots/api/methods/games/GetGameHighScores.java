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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

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

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Optional	Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId; ///< Optional	Required if inline_message_id is not specified. Unique identifier of the sent message
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId; ///< Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
    @JsonProperty(USER_ID_FIELD)
    private Integer userId; ///<Target user id

    public GetGameHighScores() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public GetGameHighScores setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public GetGameHighScores setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public GetGameHighScores setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public GetGameHighScores setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
        return this;
    }

    public GetGameHighScores setUserId(Integer userId) {
        this.userId = userId;
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
        if (userId == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
        if (inlineMessageId == null) {
            if (chatId == null) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chatId != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (messageId != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
    }

    @Override
    public String toString() {
        return "GetGameHighScores{" +
                "chatId='" + chatId + '\'' +
                ", messageId=" + messageId +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
