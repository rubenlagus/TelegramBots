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
 * This object contains information about the chat whose identifier was shared with the bot using a KeyboardButtonRequestChat button.
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
public class ChatShared implements BotApiObject {

    private static final String REQUEST_ID_FIELD = "request_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String TITLE_FIELD = "title";
    private static final String USERNAME_FIELD = "username";
    private static final String PHOTO_FIELD = "photo";

    /**
     * Identifier of the request
     */
    @JsonProperty(REQUEST_ID_FIELD)
    @NonNull
    private String requestId;
    /**
     * Identifier of the shared chat.
     * This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
     * @apiNote The bot may not have access to the user and could be unable to use this identifier, unless the user is already known to the bot by some other means.
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private Long chatId;
    /**
     * Optional.
     * Title of the chat, if the title was requested by the bot.
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * Optional.
     * Username of the chat, if the username was requested by the bot and available.
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
