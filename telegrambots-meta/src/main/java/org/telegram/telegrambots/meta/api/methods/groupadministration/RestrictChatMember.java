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
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to restrict a user in a supergroup.
 * The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights.
 * Pass True for all boolean parameters to lift restrictions from a user. Returns True on success.
 *
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RestrictChatMember extends BotApiMethodBoolean {
    public static final String PATH = "restrictchatmember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String UNTILDATE_FIELD = "until_date";
    private static final String CANSENDMESSAGES_FIELD = "can_send_messages";
    private static final String CANSENDMEDIAMESSAGES_FIELD = "can_send_media_messages";
    private static final String CANSENDOTHERMESSAGES_FIELD = "can_send_other_messages";
    private static final String CANADDWEBPAGEPREVIEWS_FIELD = "can_add_web_page_previews";
    private static final String PERMISSIONS_FIELD = "permissions";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId; ///< Required. Unique identifier of the target user
    /**
     * Optional
     * Date when restrictions will be lifted for the user, unix time.
     * If user is restricted for more than 366 days or less than 30 seconds
     * from the current time, they are considered to be restricted forever
     */
    @JsonProperty(PERMISSIONS_FIELD)
    @NonNull
    private ChatPermissions permissions;
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate; ///< Optional. Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be banned forever

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
    }

    public static class RestrictChatMemberBuilder {

        @Tolerate
        public RestrictChatMemberBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
