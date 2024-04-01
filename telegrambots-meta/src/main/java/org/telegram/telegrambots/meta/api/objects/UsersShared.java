package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object contains information about the users whose identifiers were shared with the bot using a KeyboardButtonRequestUsers button.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsersShared implements BotApiObject {
    private static final String REQUEST_ID_FIELD = "request_id";
    private static final String USERS_FIELD = "users";

    /**
     * Identifier of the request
     */
    @JsonProperty(REQUEST_ID_FIELD)
    private String requestId;
    /**
     * Information about users shared with the bot.
     */
    @JsonProperty(USERS_FIELD)
    private List<UserShared> users;

    /**
     * Use {{@link #getUsers()}}
     */
    @Deprecated
    public List<Long> getUserIds() {
        if (users == null) {
            return null;
        }
        return users.stream().map(UserShared::getUserId).collect(Collectors.toList());
    }
}
