package org.telegram.telegrambots.meta.api.methods.webapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Use this method to process a received chat join request query by showing a Mini App to the user
 * before deciding the outcome. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendChatJoinRequestWebApp extends BotApiMethodBoolean {
    public static final String PATH = "sendChatJoinRequestWebApp";

    private static final String CHAT_JOIN_REQUEST_QUERY_ID_FIELD = "chat_join_request_query_id";
    private static final String WEB_APP_URL_FIELD = "web_app_url";

    /**
     * Unique identifier of the join request query
     */
    @JsonProperty(CHAT_JOIN_REQUEST_QUERY_ID_FIELD)
    @NonNull
    private String chatJoinRequestQueryId;

    /**
     * The URL of the Mini App to be opened
     */
    @JsonProperty(WEB_APP_URL_FIELD)
    @NonNull
    private String webAppUrl;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatJoinRequestQueryId == null || chatJoinRequestQueryId.isEmpty()) {
            throw new TelegramApiValidationException("ChatJoinRequestQueryId parameter can't be empty", this);
        }
        if (webAppUrl == null || webAppUrl.isEmpty()) {
            throw new TelegramApiValidationException("WebAppUrl parameter can't be empty", this);
        }
    }
}
