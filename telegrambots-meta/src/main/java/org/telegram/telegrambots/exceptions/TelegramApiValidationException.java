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

package org.telegram.telegrambots.exceptions;

import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Exception from method validations
 * @date 16 of September of 2016
 */
public class TelegramApiValidationException extends TelegramApiException {
    private PartialBotApiMethod method;
    private InputBotApiObject object;

    public TelegramApiValidationException(String message, PartialBotApiMethod method) {
        super(message);
        this.method = method;
    }

    public TelegramApiValidationException(String message, InputBotApiObject object) {
        super(message);
        this.object = object;
    }

    public PartialBotApiMethod getMethod() {
        return method;
    }

    public InputBotApiObject getObject() {
        return object;
    }

    @Override
    public String toString() {
        if (method != null) {
            return super.toString() + " in method: " + method.toString();
        }
        if (object != null) {
            return super.toString() + " in object: " + object.toString();
        }
        return super.toString();
    }
}
