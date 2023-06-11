package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 6.7
 *
 * This object represents an inline button that switches the current user to inline mode in a chosen chat,
 * with an optional default inline query.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchInlineQueryChosenChat implements Validable, BotApiObject {
    private static final String QUERY_FIELD = "query";
    private static final String ALLOW_USER_CHATS_FIELD = "allow_user_chats";
    private static final String ALLOW_BOT_CHATS_FIELD = "allow_bot_chats";
    private static final String ALLOW_GROUP_CHATS_FIELD = "allow_group_chats";
    private static final String ALLOW_CHANNEL_CHATS_FIELD = "allow_channel_chats";

    /**
     * Optional.
     * The default inline query to be inserted in the input field.
     * If left empty, only the bot's username will be inserted
     */
    @JsonProperty(QUERY_FIELD)
    private String requestId;
    /**
     * Optional.
     * True, if private chats with users can be chosen
     */
    @JsonProperty(ALLOW_USER_CHATS_FIELD)
    @NonNull
    private Boolean allowUserChats;
    /**
     * Optional.
     * True, if private chats with bots can be chosen
     */
    @JsonProperty(ALLOW_BOT_CHATS_FIELD)
    private Boolean allowBotChats;
    /**
     * Optional.
     * True, if group and supergroup chats can be chosen
     */
    @JsonProperty(ALLOW_GROUP_CHATS_FIELD)
    private Boolean allowGroupChats;
    /**
     * Optional.
     * True, if channel chats can be chosen
     */
    @JsonProperty(ALLOW_CHANNEL_CHATS_FIELD)
    private Boolean allowChannelChats;
}
