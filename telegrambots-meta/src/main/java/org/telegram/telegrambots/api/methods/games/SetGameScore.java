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
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * Use this method to set the score of the specified user in a game.
 * On success, if the message was sent by the bot, returns the edited Message,
 * otherwise returns True.
 *
 * Returns an error, if the new score is not greater than the user's current score in
 * the chat and force is False.
 */
public class SetGameScore extends BotApiMethod<Serializable> {
    public static final String PATH = "setGameScore";

    private static final String USER_ID_FIELD = "user_id";
    private static final String SCORE_FIELD = "score";
    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String DISABLEEDITMESSAGE_FIELD = "disable_edit_message";
    private static final String FORCE_FIELD = "force";


    private String chat_id; ///< Optional	Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)

    private Integer message_id; ///< Optional	Required if inline_message_id is not specified. Unique identifier of the sent message

    private String inline_message_id; ///< Optional	Required if chat_id and message_id are not specified. Identifier of the inline message

    private Boolean disable_edit_message; ///< Optional	Pass True, if the game message should not be automatically edited to include the current scoreboard. Defaults to False

    private Integer user_id; ///< User identifier

    private Integer score; ///< New score, must be positive

    private Boolean force; ///< Opfional. Pass True, if the high score is allowed to decrease. This can be useful when fixing mistakes or banning cheaters

    public SetGameScore() {
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

    public Boolean getDisableEditMessage() {
        return disable_edit_message;
    }

    public Integer getUserId() {
        return user_id;
    }

    public Integer getScore() {
        return score;
    }

    public Boolean getForce() {
        return force;
    }

    public SetGameScore setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SetGameScore setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public SetGameScore setMessageId(Integer messageId) {
        this.message_id = messageId;
        return this;
    }

    public SetGameScore setInlineMessageId(String inlineMessageId) {
        this.inline_message_id = inlineMessageId;
        return this;
    }

    public SetGameScore setDisableEditMessage(Boolean disableEditMessage) {
        this.disable_edit_message = disableEditMessage;
        return this;
    }

    public SetGameScore setUserId(Integer user_id) {
        this.user_id = user_id;
        return this;
    }

    public SetGameScore setScore(Integer score) {
        this.score = score;
        return this;
    }

    public SetGameScore setForce(Boolean force) {
        this.force = force;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting game score", result);
            }
        } catch (IOException e) {
            try {
                ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                        new TypeReference<ApiResponse<Message>>() {
                        });
                if (result.getOk()) {
                    return result.getResult();
                } else {
                    throw new TelegramApiRequestException("Error setting game score", result);
                }
            } catch (IOException e2) {
                throw new TelegramApiRequestException("Unable to deserialize response", e2);
            }
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (user_id == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
        if (score == null) {
            throw new TelegramApiValidationException("Score parameter can't be empty", this);
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
        return "SetGameScore{" +
                "chatId='" + chat_id + '\'' +
                ", messageId=" + message_id +
                ", inlineMessageId='" + inline_message_id + '\'' +
                ", disableEditMessage=" + disable_edit_message +
                ", userId=" + user_id +
                ", score=" + score +
                ", force=" + force +
                '}';
    }
}
