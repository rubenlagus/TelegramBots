package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send answers to callback queries sent from inline keyboards. The answer
 * will be displayed to the user as a notification at the top of the chat screen or as an alert. On
 * success, True is returned.
 *
 * @apiNote  Alternatively, the user can be redirected to the specified URL. For this option to work,
 * you must enable /setcustomurls for your bot via BotFather and accept the terms.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerCallbackQuery extends BotApiMethodBoolean {
    public static final String PATH = "answercallbackquery";

    private static final String CALLBACKQUERYID_FIELD = "callback_query_id";
    private static final String TEXT_FIELD = "text";
    private static final String SHOWALERT_FIELD = "show_alert";
    private static final String URL_FIELD = "url";
    private static final String CACHETIME_FIELD = "cache_time";

    @JsonProperty(CALLBACKQUERYID_FIELD)
    @NonNull
    private String callbackQueryId; ///< Unique identifier for the query to be answered
    @JsonProperty(TEXT_FIELD)
    private String text; ///< Optional	Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
    @JsonProperty(SHOWALERT_FIELD)
    private Boolean showAlert; ///< Optional. If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
    /**
     * Optional. URL that will be opened by the user's client.
     * If you have created a Game and accepted the conditions via @Botfather,
     * specify the URL that opens your game. Otherwise you may use links
     * like telegram.me/your_bot?start=XXXX that open your bot with a parameter.
     */
    @JsonProperty(URL_FIELD)
    private String url;
    /**
     * Optional	The maximum amount of time in seconds that the result of the callback query
     * may be cached client-side.
     *
     * @apiNote Telegram apps will support caching starting in version 3.14. Defaults to 0.
     */
    @JsonProperty(CACHETIME_FIELD)
    private Integer cacheTime;

    @Override
    public String getMethod() {
        return PATH;
    }
}
