package org.telegram.telegrambots.meta.api.methods.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.webapp.SentWebAppMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Use this method to set result of interaction with web app and send corresponding
 * message on behalf of the user to the chat from which the query originated.
 *
 *
 * On success, SentWebAppMessage is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class AnswerWebAppQuery  extends BotApiMethod<SentWebAppMessage> {
    public static final String PATH = "answerWebAppQuery";

    private static final String WEBAPPQUERYID_FIELD = "web_app_query_id";
    private static final String RESULT_FIELD = "result";

    @JsonProperty(WEBAPPQUERYID_FIELD)
    @NonNull
    private String webAppQueryId; ///< Unique identifier for the answered query
    @JsonProperty(RESULT_FIELD)
    @NonNull
    private InlineQueryResult queryResult; ///<  A JSON-serialized object with a description of the message to send

    @Override
    public void validate() throws TelegramApiValidationException {
        if (webAppQueryId.isEmpty()) {
            throw new TelegramApiValidationException("WebAppQueryId can't be empty", this);
        }

        queryResult.validate();
    }

    @Override
    public String getMethod() {
        return PATH;
    }


    @Override
    public SentWebAppMessage deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, SentWebAppMessage.class);
    }
}
