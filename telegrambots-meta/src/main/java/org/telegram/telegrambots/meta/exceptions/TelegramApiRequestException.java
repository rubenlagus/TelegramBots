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

package org.telegram.telegrambots.meta.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.ResponseParameters;


/**
 * @author Ruben Bermudez
 * @version 1.0
 * Exception thrown when something goes wrong in the api
 */
public class TelegramApiRequestException extends TelegramApiException {
    private static final Logger log = LoggerFactory.getLogger(TelegramApiRequestException.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String ERRORDESCRIPTIONFIELD = "description";
    private static final String ERRORCODEFIELD = "error_code";
    private static final String PARAMETERSFIELD = "parameters";

    private String apiResponse = null;
    private Integer errorCode = 0;
    private ResponseParameters parameters;

    public TelegramApiRequestException(String message) {
        super(message);
    }

    public TelegramApiRequestException(String message, ApiResponse<?> response) {
        super(message);
        apiResponse = response.getErrorDescription();
        errorCode = response.getErrorCode();
        parameters = response.getParameters();
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

    public ResponseParameters getParameters() {
        return parameters;
    }

    /**
     * More detailed error description.
     * 
     * @return assembled response code and reason
     */
    @Override
    public String getMessage() {
        if (apiResponse == null) {
            return super.getMessage();
        } else if (errorCode == null) {
            return super.getMessage() + ": " + apiResponse;
        } else {
            return super.getMessage() + ": [" + errorCode + "] " + apiResponse;
        }
    }
    
    /**
     * Just left as backward compatibility if anybody used this in version 5.2.0 or older.
     *
     * @return the getMessage string
     */
    @Override
    public String toString() {
        return getMessage();
    }
    
}
