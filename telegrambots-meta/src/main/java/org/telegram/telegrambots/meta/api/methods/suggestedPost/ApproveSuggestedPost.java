package org.telegram.telegrambots.meta.api.methods.suggestedpost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Use this method to approve a suggested post in a direct messages chat.
 * The bot must have the 'can_post_messages' administrator right in the corresponding channel chat.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApproveSuggestedPost extends BotApiMethod<Boolean> {
    public static final String PATH = "approveSuggestedPost";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String SEND_DATE_FIELD = "send_date";

    /**
     * Unique identifier for the target direct messages chat
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private Integer chatId;

    /**
     * Identifier of a suggested post message to approve
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    @NonNull
    private Integer messageId;

    /**
     * Optional
     * Point in time (Unix timestamp) when the post is expected to be published;
     * omit if the date has already been specified when the suggested post was created.
     * If specified, then the date must be not more than 2678400 seconds (30 days) in the future
     */
    @JsonProperty(SEND_DATE_FIELD)
    private Integer sendDate;

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (messageId == null) {
            throw new TelegramApiValidationException("MessageId parameter can't be empty", this);
        }
        if (sendDate != null) {
            long currentTime = System.currentTimeMillis() / 1000;
            if (sendDate > currentTime + 2678400) {
                throw new TelegramApiValidationException("SendDate can't be more than 30 days in the future", this);
            }
        }
    }
}
