package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Upon receiving a message with this object, Telegram clients will display a reply interface
 * to the user (act as if the user has selected the bot‘s message and tapped ’Reply'). This can be
 * extremely useful if you want to create user-friendly step-by-step interfaces without having to
 * sacrifice privacy mode.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ForceReplyKeyboard implements ReplyKeyboard {
    private static final String FORCEREPLY_FIELD = "force_reply";
    private static final String SELECTIVE_FIELD = "selective";

    /**
     * Shows reply interface to the user, as if they manually selected the bot‘s message and tapped
     * ’Reply'
     */
    @JsonProperty(FORCEREPLY_FIELD)
    @NonNull
    private Boolean forceReply;
    /**
     * Optional.
     *
     * Use this parameter if you want to force reply from specific users only. Targets: 1) users
     * that are @mentioned in the text of the Message object; 2) if the bot's message is a reply
     * (has reply_to_message_id), sender of the original message.
     */
    @JsonProperty(SELECTIVE_FIELD)
    private Boolean selective;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (forceReply == null) {
            throw new TelegramApiValidationException("ForceReply parameter can't not be null", this);
        }
    }
}
