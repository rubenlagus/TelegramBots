package org.telegram.telegrambots.api.methods;



import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send answers to callback queries sent from inline keyboards. The answer
 * will be displayed to the user as a notification at the top of the chat screen or as an alert. On
 * success, True is returned.
 *
 * @note Alternatively, the user can be redirected to the specified URL. For this option to work,
 * you must enable /setcustomurls for your bot via BotFather and accept the terms.
 *
 * @date 10 of April of 2016
 */
public class AnswerCallbackQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answercallbackquery";

    private static final String CALLBACKQUERYID_FIELD = "callback_query_id";
    private static final String TEXT_FIELD = "text";
    private static final String SHOWALERT_FIELD = "show_alert";
    private static final String URL_FIELD = "url";
    private static final String CACHETIME_FIELD = "cache_time";


    private String callback_query_id; ///< Unique identifier for the query to be answered

    private String text; ///< Optional	Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters

    private Boolean show_alert; ///< Optional. If true, an alert will be shown by the client instead of a notificaiton at the top of the chat screen. Defaults to false.

    /**
     * Optional. URL that will be opened by the user's client.
     * If you have created a Game and accepted the conditions via @Botfather,
     * specify the URL that opens your game. Otherwise you may use links
     * InlineQueryResultGamelike telegram.me/your_bot?start=XXXX that open your bot with a parameter.
     */
    private String url;

    /**
     * Optional	The maximum amount of time in seconds that the result of the callback query
     * may be cached client-side.
     *
     * @note Telegram apps will support caching starting in version 3.14. Defaults to 0.
     */
    private Integer cache_time;

    public AnswerCallbackQuery() {
        super();
    }

    public String getCallbackQueryId() {
        return this.callback_query_id;
    }

    public AnswerCallbackQuery setCallbackQueryId(String callbackQueryId) {
        this.callback_query_id = callbackQueryId;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public AnswerCallbackQuery setText(String text) {
        this.text = text;
        return this;
    }

    public Boolean getShowAlert() {
        return this.show_alert;
    }

    public AnswerCallbackQuery setShowAlert(Boolean showAlert) {
        this.show_alert = showAlert;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public AnswerCallbackQuery setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getCacheTime() {
        return cache_time;
    }

    public void setCacheTime(Integer cacheTime) {
        this.cache_time = cacheTime;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error answering callback query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (callback_query_id == null) {
            throw new TelegramApiValidationException("CallbackQueryId can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "AnswerCallbackQuery{" +
                "callbackQueryId='" + callback_query_id + '\'' +
                ", text='" + text + '\'' +
                ", showAlert=" + show_alert +
                ", url='" + url + '\'' +
                ", cacheTime=" + cache_time +
                '}';
    }
}
