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
    private static final String USER_IDS_FIELD = "user_ids";

    /**
     * Identifier of the request
     */
    @JsonProperty(REQUEST_ID_FIELD)
    private String requestId;
    /**
     * Identifiers of the shared users. These numbers may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting them.
     * But they have at most 52 significant bits, so 64-bit integers or double-precision float types are safe for storing these identifiers.
     * @apiNote The bot may not have access to the users and could be unable to use these identifiers, unless the users are already known to the bot by some other means.
     */
    @JsonProperty(USER_IDS_FIELD)
    private List<Long> userIds;
}
