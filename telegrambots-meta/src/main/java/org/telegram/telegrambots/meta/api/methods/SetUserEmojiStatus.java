package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 8.0
 * Changes the emoji status for a given user that previously allowed the bot to manage their emoji status via the
 * Mini App method requestEmojiStatusAccess.
 *
 * Returns True on success.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetUserEmojiStatus extends BotApiMethodBoolean {
    public static final String PATH = "setUserEmojiStatus";

    private static final String USER_ID_FIELD = "user_id";
    private static final String EMOJI_STATUS_CUSTOM_EMOJI_ID_FIELD = "emoji_status_custom_emoji_id";
    private static final String EMOJI_STATUS_EXPIRATION_DATE_FIELD = "emoji_status_expiration_date";


    /**
     * Unique identifier of the target user
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Optional
     * Custom emoji identifier of the emoji status to set.
     * Pass an empty string to remove the status.
     */
    @JsonProperty(EMOJI_STATUS_CUSTOM_EMOJI_ID_FIELD)
    private String emojiStatusCustomEmojiId;
    /**
     * Optional
     * Expiration date of the emoji status, if any
     */
    @JsonProperty(EMOJI_STATUS_EXPIRATION_DATE_FIELD)
    private Integer emojiStatusExpirationDate;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
    }
}
