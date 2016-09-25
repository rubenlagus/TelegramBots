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

import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief Use this method to set game score of the specified user in the game.
 * On success, if edited message is sent by the bot, the edited Message is returned,
 * otherwise True is returned.
 * If score is not greater than current users score in the chat,
 * an error with description “BOT_SCORE_NOT_MODIFIED” will be returned.
 * @date 16 of September of 2016
 */
public class SetGameScore extends BotApiMethod<Serializable> {
    public static final String PATH = "setGameScore";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String EDIT_MESSAGE_FIELD = "edit_message";
    private static final String USER_ID_FIELD = "user_id";
    private static final String GAME_ID_FIELD = "game_id";
    private static final String SCORE_FIELD = "score";

    private String chatId; ///< Optional	Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)
    private Integer messageId; ///< Optional	Required if inline_message_id is not specified. Unique identifier of the sent message
    private String inlineMessageId; ///< Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
    private Boolean editMessage; ///< Optional	Pass True, if the message should be edited to include the current scoreboard
    private Integer userId; ///< User identifier
    private Integer gameId; ///< Game identifier
    private Integer score; ///< New score, must be positive

    public SetGameScore() {
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

    public Boolean getEditMessage() {
        return editMessage;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Integer getScore() {
        return score;
    }

    public SetGameScore setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetGameScore setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public SetGameScore setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
        return this;
    }

    public SetGameScore setEditMessage(Boolean editMessage) {
        this.editMessage = editMessage;
        return this;
    }

    public SetGameScore setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public SetGameScore setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }

    public SetGameScore setScore(Integer score) {
        this.score = score;
        return this;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Serializable deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            Object result = answer.get(Constants.RESPONSEFIELDRESULT);
            if (result instanceof Boolean) {
                return (Boolean) result;
            }
            if (result instanceof JSONObject) {
                return new Message((JSONObject) result);
            }
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
        if (score == null) {
            throw new TelegramApiValidationException("Score parameter can't be empty", this);
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
        if (editMessage != null) {
            gen.writeBooleanField(EDIT_MESSAGE_FIELD, editMessage);
        }
        gen.writeNumberField(USER_ID_FIELD, userId);
        gen.writeNumberField(GAME_ID_FIELD, gameId);
        gen.writeNumberField(SCORE_FIELD, score);
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
        if (editMessage != null) {
            jsonObject.put(EDIT_MESSAGE_FIELD, editMessage);
        }
        jsonObject.put(USER_ID_FIELD, userId);
        jsonObject.put(GAME_ID_FIELD, gameId);
        jsonObject.put(SCORE_FIELD, score);

        return jsonObject;
    }

    @Override
    public String toString() {
        return "SetGameScore{" +
                "chatId='" + chatId + '\'' +
                ", messageId=" + messageId +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", editMessage=" + editMessage +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", score=" + score +
                '}';
    }
}
