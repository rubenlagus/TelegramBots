/*
 * This file is part of TelegramBots.
 *
 * Foobar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.api.methods.games;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief Use this method to get game high score with a score of the specified user and some
 * of its neighbours in the game.
 * On success, Array of GameHighScore is returned.
 * @date 16 of September of 2016
 */
public class GetGameHighScores extends BotApiMethod<ArrayList<GameHighScore>> {
    public static final String PATH = "setGameScore";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String GAME_ID_FIELD = "game_id";

    private String chatId; ///< Optional	Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)
    private Integer messageId; ///< Optional	Required if inline_message_id is not specified. Unique identifier of the sent message
    private String inlineMessageId; ///< Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
    private Integer userId; ///< User identifier
    private Integer gameId; ///< Game identifier

    public GetGameHighScores() {
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

    public Integer getGameId() {
        return gameId;
    }

    public GetGameHighScores setChatId(String chatId) {
        this.chatId = chatId;
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

    public GetGameHighScores setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public ArrayList<GameHighScore> deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            JSONArray highScores = answer.getJSONArray(Constants.RESPONSEFIELDRESULT);
            ArrayList<GameHighScore> scores = new ArrayList<>();
            for (int i = 0; i < highScores.length(); i++) {
                scores.add(new GameHighScore(highScores.getJSONObject(i)));
            }
            return scores;
        }
        return null;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
        if (gameId == null) {
            throw new TelegramApiValidationException("GameId parameter can't be empty", this);
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
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        if (chatId != null) {
            gen.writeStringField(CHATID_FIELD, chatId);
            gen.writeNumberField(MESSAGEID_FIELD, messageId);
        }
        if (inlineMessageId != null) {
            gen.writeStringField(INLINE_MESSAGE_ID_FIELD, inlineMessageId);
        }
        gen.writeNumberField(USER_ID_FIELD, userId);
        gen.writeNumberField(GAME_ID_FIELD, gameId);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        if (chatId != null) {
            jsonObject.put(CHATID_FIELD, chatId);
            jsonObject.put(MESSAGEID_FIELD, messageId);
        }
        if (inlineMessageId != null) {
            jsonObject.put(INLINE_MESSAGE_ID_FIELD, inlineMessageId);
        }
        jsonObject.put(USER_ID_FIELD, userId);
        jsonObject.put(GAME_ID_FIELD, gameId);

        return jsonObject;
    }

    @Override
    public String toString() {
        return "GetGameHighScores{" +
                "chatId='" + chatId + '\'' +
                ", messageId=" + messageId +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", userId=" + userId +
                ", gameId=" + gameId +
                '}';
    }
}
