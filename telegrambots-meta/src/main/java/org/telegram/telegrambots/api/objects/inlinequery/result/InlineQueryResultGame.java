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

package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * Represents a Game
 *
 * @apiNote  This will only work in Telegram versions released after 1 October, 2016. Older clients will ignore them.
 */
public class InlineQueryResultGame implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String GAMESHORTNAME_FIELD = "game_short_name";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "game"; ///< Type of the result, must be "game"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(GAMESHORTNAME_FIELD)
    private String gameShortName; ///< Short name of the game
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultGame() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultGame setId(String id) {
        this.id = id;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultGame setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public String getGameShortName() {
        return gameShortName;
    }

    public void setGameShortName(String gameShortName) {
        this.gameShortName = gameShortName;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (gameShortName == null || gameShortName.isEmpty()) {
            throw new TelegramApiValidationException("GameShortName parameter can't be empty", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultGame{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", gameShortName='" + gameShortName + '\'' +
                ", replyMarkup=" + replyMarkup +
                '}';
    }
}
