package org.telegram.telegrambots.meta.api.methods.user;

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
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Use this method to get the last messages from the personal chat (i.e., the chat currently added to their profile) of a given user.
 * On success, an array of Message objects is returned.
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
public class GetUserPersonalChatMessages extends BotApiMethod<ArrayList<Message>> {
    public static final String PATH = "getUserPersonalChatMessages";

    private static final String USER_ID_FIELD = "user_id";
    private static final String LIMIT_FIELD = "limit";

    /**
     * Unique identifier for the target user
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * The maximum number of messages to return; 1-20
     */
    @JsonProperty(LIMIT_FIELD)
    @NonNull
    private Integer limit;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null || userId == 0) {
            throw new TelegramApiValidationException("UserId can't be null or 0", this);
        }
        if (limit == null || limit < 1 || limit > 20) {
            throw new TelegramApiValidationException("Limit must be between 1 and 20", this);
        }
    }

    @Override
    public ArrayList<Message> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, Message.class);
    }
}
