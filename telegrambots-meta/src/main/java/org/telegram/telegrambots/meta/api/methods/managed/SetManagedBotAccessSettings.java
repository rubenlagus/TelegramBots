package org.telegram.telegrambots.meta.api.methods.managed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Use this method to change the access settings of a managed bot.
 * Returns True on success.
 */
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
public class SetManagedBotAccessSettings extends BotApiMethodBoolean {
    public static final String PATH = "setManagedBotAccessSettings";

    private static final String USER_ID_FIELD = "user_id";
    private static final String IS_ACCESS_RESTRICTED_FIELD = "is_access_restricted";
    private static final String ADDED_USER_IDS_FIELD = "added_user_ids";

    /**
     * User identifier of the managed bot whose access settings will be changed
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Pass True, if only selected users can access the bot. The bot's owner can always access it.
     */
    @JsonProperty(IS_ACCESS_RESTRICTED_FIELD)
    @NonNull
    private Boolean isAccessRestricted;
    /**
     * Optional
     * A JSON-serialized list of up to 10 identifiers of users who will have access to the bot in addition to its owner.
     * Ignored if is_access_restricted is false.
     */
    @JsonProperty(ADDED_USER_IDS_FIELD)
    @Singular
    private List<Long> addedUserIds;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null || userId == 0) {
            throw new TelegramApiValidationException("UserId can't be null or 0", this);
        }
        if (isAccessRestricted == null) {
            throw new TelegramApiValidationException("IsAccessRestricted can't be null", this);
        }
        if (addedUserIds != null && addedUserIds.size() > 10) {
            throw new TelegramApiValidationException("AddedUserIds can't have more than 10 identifiers", this);
        }
    }
}
