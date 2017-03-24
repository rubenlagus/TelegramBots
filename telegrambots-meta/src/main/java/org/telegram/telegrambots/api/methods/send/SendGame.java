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

package org.telegram.telegrambots.api.methods.send;



import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send a game. On success, the sent Message is returned.
 * @date 27 of September of 2016
 */
public class SendGame extends BotApiMethod<Message> {
    public static final String PATH = "sendGame";

    private static final String CHATID_FIELD = "chat_id";
    private static final String GAMESHORTNAME_FIELD = "game_short_name";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    private String game_short_name; ///< Short name of the game
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */

    private Boolean disable_notification;

    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message

    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendGame() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendGame setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendGame setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendGame setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendGame setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendGame enableNotification() {
        this.disable_notification = null;
        return this;
    }

    public SendGame disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public String getGameShortName() {
        return game_short_name;
    }

    public SendGame setGameShortName(String gameShortName) {
        this.game_short_name = gameShortName;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending game", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chat_id == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (game_short_name == null || game_short_name.isEmpty()) {
            throw new TelegramApiValidationException("GameShortName parameter can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendGame{" +
                "chatId='" + chat_id + '\'' +
                ", gameShortName='" + game_short_name + '\'' +
                ", disableNotification=" + disable_notification +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
