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

package org.telegram.telegrambots.meta.api.objects.games;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief A placeholder, currently holds no information. Use BotFather to set up your game.
 * @date 16 of September of 2016
 */
public class CallbackGame implements BotApiObject {
    public CallbackGame() {
        super();
    }

    @Override
    public String toString() {
        return "CallbackGame{}";
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof CallbackGame;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
