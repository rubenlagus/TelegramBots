package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
 * Use this method to create a subscription invite link for a channel chat.
 * Returns the new invite link as a ChatInviteLink object.
 *
 * @apiNote The bot must have can_invite_users administrator rights.
 * @apiNote The link can be edited using the method editChatSubscriptionInviteLink or revoked using the method revokeChatInviteLink.
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
public class CreateChatSubscriptionInviteLink extends BotApiMethod<ChatInviteLink> {
    public static final String PATH = "createChatSubscriptionInviteLink";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String SUBSCRIPTION_PERIOD_FIELD = "subscription_period";
    private static final String SUBSCRIPTION_PRICE_FIELD = "subscription_price";
    private static final String NAME_FIELD = "name";

    /**
     * Unique identifier for the target channel chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * The number of seconds the subscription will be active for before the next payment.
     * Currently, it must always be 2592000 (30 days).
     */
    @JsonProperty(SUBSCRIPTION_PERIOD_FIELD)
    @NonNull
    @Builder.Default
    private Integer subscriptionPeriod = 2592000;
    /**
     * The amount of Telegram Stars a user must pay initially and after each subsequent subscription period to be a member of the chat; 1-2500
     */
    @JsonProperty(SUBSCRIPTION_PRICE_FIELD)
    @NonNull
    private Integer subscriptionPrice;
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
        if (subscriptionPeriod != 2592000) {
            throw new TelegramApiValidationException("SubscriptionPeriod must be 2592000", this);
        }
        if (subscriptionPrice < 1 || subscriptionPrice > 2500) {
            throw new TelegramApiValidationException("SubscriptionPrice must be between 1 and 2500", this);
        }
    }

    public static abstract class CreateChatSubscriptionInviteLinkBuilder<C extends CreateChatSubscriptionInviteLink, B extends CreateChatSubscriptionInviteLinkBuilder<C, B>> extends BotApiMethodBuilder<ChatInviteLink, C, B> {
        @Tolerate
        public CreateChatSubscriptionInviteLinkBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
