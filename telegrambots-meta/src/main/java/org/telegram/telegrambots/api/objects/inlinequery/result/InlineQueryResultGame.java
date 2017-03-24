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



import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a Game
 * @note This will only work in Telegram versions released after 1 October, 2016. Older clients will ignore them.
 * @date 27 of September 2016
 */
public class InlineQueryResultGame implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String GAMESHORTNAME_FIELD = "game_short_name";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";


    private final String type = "game"; ///< Type of the result, must be "game"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String game_short_name; ///< Short name of the game

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

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
        return reply_markup;
    }

    public InlineQueryResultGame setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public String getGameShortName() {
        return game_short_name;
    }

    public void setGameShortName(String gameShortName) {
        this.game_short_name = gameShortName;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
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
        return "InlineQueryResultGame{" +
                "id='" + id + '\'' +
                ", gameShortName='" + game_short_name + '\'' +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
