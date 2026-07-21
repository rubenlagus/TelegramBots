package org.telegram.telegrambots.meta.api.methods.groupadministration;

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
 * Use this method to process a received chat join request query. Returns True on success.
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
public class AnswerChatJoinRequestQuery extends BotApiMethodBoolean {
    public static final String PATH = "answerChatJoinRequestQuery";

    private static final String CHAT_JOIN_REQUEST_QUERY_ID_FIELD = "chat_join_request_query_id";
    private static final String RESULT_FIELD = "result";

    /**
     * Unique identifier of the join request query
     */
    @JsonProperty(CHAT_JOIN_REQUEST_QUERY_ID_FIELD)
    @NonNull
    private String chatJoinRequestQueryId;

    /**
     * Result of the query. Must be either "approve" to allow the user to join the chat,
     * "decline" to disallow the user to join the chat, or "queue" to leave the decision to other administrators.
     */
    @JsonProperty(RESULT_FIELD)
    @NonNull
    private String result;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatJoinRequestQueryId == null || chatJoinRequestQueryId.isEmpty()) {
            throw new TelegramApiValidationException("ChatJoinRequestQueryId parameter can't be empty", this);
        }
        if (result == null || result.isEmpty()) {
            throw new TelegramApiValidationException("Result parameter can't be empty", this);
        }
        if (!result.equals("approve") && !result.equals("decline") && !result.equals("queue")) {
            throw new TelegramApiValidationException("Result parameter must be one of: approve, decline, queue", this);
        }
    }
}
