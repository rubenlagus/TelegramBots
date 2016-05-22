package org.telegram.telegrambots;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Exception thrown when something goes wrong in the api
 * @date 14 of January of 2016
 */
public class TelegramApiException extends Exception {
    private String apiResponse = null;
    private Integer errorCode;

    public TelegramApiException(String message) {
        super(message);
    }

    public TelegramApiException(String message, String apiResponse, Integer errorCode) {
        super(message);
        this.apiResponse = apiResponse;
    }

    public TelegramApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getApiResponse() {
        return apiResponse;
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
