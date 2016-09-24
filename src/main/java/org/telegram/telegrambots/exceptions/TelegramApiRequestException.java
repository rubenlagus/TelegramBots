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

package org.telegram.telegrambots.exceptions;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Exception thrown when something goes wrong in the api
 * @date 14 of January of 2016
 */
public class TelegramApiRequestException extends TelegramApiException {
    private String apiResponse = null;
    private Integer errorCode = 0;

    public TelegramApiRequestException(String message) {
        super(message);
    }

    public TelegramApiRequestException(String message, String apiResponse, Integer errorCode) {
        super(message);
        this.apiResponse = apiResponse;
        this.errorCode = errorCode;
    }

    public TelegramApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        if (apiResponse == null) {
            return super.toString();
        } else if (errorCode == null) {
            return super.toString() + ": " + apiResponse;
        } else {
            return super.toString() + ": [" + errorCode + "] " + apiResponse;
        }
    }
}
