package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * This object contains information about the user whose identifier was shared with the bot using a KeyboardButtonRequestUser button.
 * @author Ruben Bermudez
 * @version 6.5
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
public class UserShared implements BotApiObject {
    private static final String USER_ID_FIELD = "user_id";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";
    private static final String PHOTO_FIELD = "photo";

    /**
     * Identifier of the shared user.
     * This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so 64-bit integers or double-precision float types are safe for storing these identifiers.
     *
     * @apiNote The bot may not have access to the user and could be unable to use this identifier, unless the user is already known to the bot by some other means.
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Optional.
     * First name of the user, if the name was requested by the bot
     */
    @JsonProperty(FIRST_NAME_FIELD)
    private String firstName;
    /**
     * Optional.
     * Last name of the user, if the name was requested by the bot
     */
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName;
    /**
     * Optional.
     * Username of the user, if the username was requested by the bot
     */
    @JsonProperty(USERNAME_FIELD)
    private String username;
    /**
     * Optional.
     * Available sizes of the chat photo, if the photo was requested by the bot
     */
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo;
}
