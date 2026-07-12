package org.telegram.telegrambots.meta.api.methods;

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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.SentGuestMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Use this method to reply to a received guest message.
 * On success, a SentGuestMessage object is returned.
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
public class AnswerGuestQuery extends BotApiMethod<SentGuestMessage> {
    public static final String PATH = "answerGuestQuery";

    private static final String GUEST_QUERY_ID_FIELD = "guest_query_id";
    private static final String RESULT_FIELD = "result";

    /**
     * Unique identifier for the query to be answered
     */
    @JsonProperty(GUEST_QUERY_ID_FIELD)
    @NonNull
    private String guestQueryId;
    /**
     * A JSON-serialized object describing the message to be sent
     */
    @JsonProperty(RESULT_FIELD)
    @NonNull
    private InlineQueryResult result;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (guestQueryId.isEmpty()) {
            throw new TelegramApiValidationException("GuestQueryId can't be empty", this);
        }
        result.validate();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public SentGuestMessage deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, SentGuestMessage.class);
    }
}
