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

package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Contains information about why a request was unsuccessfull.
 * @date 28 of September of 2016
 */
public class ResponseParameters implements BotApiObject {
    private static final String MIGRATETOCHATID_FIELD = "migrate_to_chat_id";
    private static final String RETRYAFTER_FIELD = "retry_after";

    /**
     * Optional. The group has been migrated to a supergroup with the specified identifier.
     * This number may be greater than 32 bits and some programming languages may have
     * difficulty/silent defects in interpreting it. But it is smaller than 52 bits,
     * so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
     */
    @JsonProperty(MIGRATETOCHATID_FIELD)
    private Integer migrateToChatId;
    /**
     * Optional. In case of exceeding flood control a number of seconds to
     * wait before the request can be repeated
     */
    @JsonProperty(RETRYAFTER_FIELD)
    private Integer retryAfter;

    public ResponseParameters() {
        super();
    }

    public Integer getMigrateToChatId() {
        return migrateToChatId;
    }

    public Integer getRetryAfter() {
        return retryAfter;
    }

    @Override
    public String toString() {
        return "ResponseParameters{" +
                "migrateToChatId=" + migrateToChatId +
                ", retryAfter=" + retryAfter +
                '}';
    }
}
