package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author Ruben Bermudez
 * @version 5.5
 * Use this method to ban a channel chat in a supergroup or a channel.
 * The owner of the chat will not be able to send messages and join live streams on behalf of the chat, unless it is unbanned first.
 * The bot must be an administrator in the supergroup or channel for this to work and must have the appropriate administrator rights.
 * Returns True on success.
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class BanChatSenderChat extends BotApiMethod<Boolean> {
    public static final String PATH = "banChatSenderChat";

    private static final String CHATID_FIELD = "chat_id";
    private static final String SENDER_CHAT_ID_FIELD = "sender_chat_id";
    private static final String UNTILDATE_FIELD = "until_date";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(SENDER_CHAT_ID_FIELD)
    @NonNull
    private Long senderChatId; ///< Required. Unique identifier of the target sender chat
    /**
     * Date when the sender chat will be unbanned, unix time.
     * If the chat is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever.
     */
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate;

    @JsonIgnore
    public void setUntilDateInstant(Instant instant) {
        setUntilDate((int) instant.getEpochSecond());
    }

    @JsonIgnore
    public void setUntilDateDateTime(ZonedDateTime date) {
        setUntilDateInstant(date.toInstant());
    }

    @JsonIgnore
    public void forTimePeriodDuration(Duration duration) {
        setUntilDateInstant(Instant.now().plusMillis(duration.toMillis()));
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
                throw new TelegramApiRequestException("Error banning chat sender", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (senderChatId == 0) {
            throw new TelegramApiValidationException("SenderChatId can't be null or 0", this);
        }
    }
}
