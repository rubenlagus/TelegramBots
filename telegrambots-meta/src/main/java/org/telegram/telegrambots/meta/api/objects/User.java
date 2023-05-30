package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a Telegram user or bot.
 * @author Ruben Bermudez
 * @version 6.1
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String ISBOT_FIELD = "is_bot";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";
    private static final String LANGUAGECODE_FIELD = "language_code";
    private static final String CANJOINGROUPS_FIELD = "can_join_groups";
    private static final String CANREADALLGROUPMESSAGES_FIELD = "can_read_all_group_messages";
    private static final String SUPPORTINLINEQUERIES_FIELD = "supports_inline_queries";
    private static final String ISPREMIUM_FIELD = "is_premium";
    private static final String ADDEDTOATTACHMENTMENU_FIELD = "added_to_attachment_menu";

    /**
     * Unique identifier for this user or bot.
     *
     * @apiNote This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Long id;
    /**
     * User‘s or bot’s first name
     */
    @JsonProperty(FIRSTNAME_FIELD)
    @NonNull
    private String firstName;
    /**
     * True, if this user is a bot
     */
    @JsonProperty(ISBOT_FIELD)
    @NonNull
    private Boolean isBot;
    /**
     * Optional. User‘s or bot’s last name
     */
    @JsonProperty(LASTNAME_FIELD)
    private String lastName;
    /**
     * Optional. User‘s or bot’s username
     */
    @JsonProperty(USERNAME_FIELD)
    private String userName;
    /**
     * Optional. IETF language tag of the user's language
     */
    @JsonProperty(LANGUAGECODE_FIELD)
    private String languageCode;
    /**
     * Optional. True, if the bot can be invited to groups. Returned only in getMe.
     */
    @JsonProperty(CANJOINGROUPS_FIELD)
    private Boolean canJoinGroups;
    /**
     * Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
     */
    @JsonProperty(CANREADALLGROUPMESSAGES_FIELD)
    private Boolean canReadAllGroupMessages;
    /**
     * Optional. True, if the bot supports inline queries. Returned only in getMe.
     */
    @JsonProperty(SUPPORTINLINEQUERIES_FIELD)
    private Boolean supportInlineQueries;
    /**
     * Optional. True, if this user is a Telegram Premium user
     */
    @JsonProperty(ISPREMIUM_FIELD)
    private Boolean isPremium;
    /**
     * Optional. True, if this user added the bot to the attachment menu
     */
    @JsonProperty(ADDEDTOATTACHMENTMENU_FIELD)
    private Boolean addedToAttachmentMenu;
}
