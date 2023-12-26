package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author Ruben Bermudez
 * @version 5.3
 * Use this method to kick a user from a group, a supergroup or a channel. In the case of supergroups,
 * the user will not be able to return to the group on their own using invite links, etc., unless
 * unbanned first. The bot must be an administrator in the group for this to work. Returns True on
 * success.
 * @apiNote  This will method only work if the ‘All Members Are Admins’ setting is off in the target
 * group. Otherwise members may only be removed by the group's creator or by the member that added
 * them.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class BanChatMember extends BotApiMethodBoolean {
    public static final String PATH = "banChatMember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String UNTILDATE_FIELD = "until_date";
    private static final String REVOKEMESSAGES_FIELD = "revoke_messages";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId; ///< Required. Unique identifier of the target user
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate; ///< Optional. Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever
    /**
     * Optional
     *
     * Pass True to delete all messages from the chat for the user that is being removed.
     *
     * If False, the user will be able to see messages in the group that were sent before the user was removed.
     *
     * Always True for supergroups and channels.
     */
    @JsonProperty(REVOKEMESSAGES_FIELD)
    private Boolean revokeMessages;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

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
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (userId == 0) {
            throw new TelegramApiValidationException("UserId can't be null or 0", this);
        }
    }

    public static class BanChatMemberBuilder {

        @Tolerate
        public BanChatMemberBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

    }
}
