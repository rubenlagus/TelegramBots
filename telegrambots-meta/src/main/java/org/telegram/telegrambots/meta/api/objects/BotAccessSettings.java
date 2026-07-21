package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * This object describes the access settings of a bot.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BotAccessSettings implements BotApiObject {
    private static final String IS_ACCESS_RESTRICTED_FIELD = "is_access_restricted";
    private static final String ADDED_USERS_FIELD = "added_users";

    /**
     * True, if only selected users can access the bot. The bot's owner can always access it.
     */
    @JsonProperty(IS_ACCESS_RESTRICTED_FIELD)
    private Boolean isAccessRestricted;
    /**
     * Optional. The list of other users who have access to the bot if the access is restricted
     */
    @JsonProperty(ADDED_USERS_FIELD)
    private List<User> addedUsers;
}
