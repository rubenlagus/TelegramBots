package org.telegram.telegrambots.meta.api.methods.groupadministration;

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
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.9
 *
 * Use this method to edit a subscription invite link created by the bot.
 * Returns the edited invite link as a ChatInviteLink object.
 *
 * @apiNote The bot must have can_invite_users administrator rights.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditChatSubscriptionInviteLink extends BotApiMethod<ChatInviteLink> {
    public static final String PATH = "editChatSubscriptionInviteLink";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String INVITE_LINK_FIELD = "invite_link";
    private static final String NAME_FIELD = "name";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * The number of seconds the subscription will be active for before the next payment.
     * Currently, it must always be 2592000 (30 days).
     */
    @JsonProperty(INVITE_LINK_FIELD)
    @NonNull
    private String inviteLink;
    /**
     * Optional
     * Invite link name; 0-32 characters
     */
    @JsonProperty(NAME_FIELD)
    private String name;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ChatInviteLink deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, ChatInviteLink.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (name != null && name.length() > 32) {
            throw new TelegramApiValidationException("Name must be between 0 and 32 characters", this);
        }
        if (inviteLink.isEmpty()) {
            throw new TelegramApiValidationException("InviteLink must not be empty", this);
        }
    }

    public static abstract class EditChatSubscriptionInviteLinkBuilder<C extends EditChatSubscriptionInviteLink, B extends EditChatSubscriptionInviteLinkBuilder<C, B>> extends BotApiMethodBuilder<ChatInviteLink, C, B> {
        @Tolerate
        public EditChatSubscriptionInviteLinkBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
