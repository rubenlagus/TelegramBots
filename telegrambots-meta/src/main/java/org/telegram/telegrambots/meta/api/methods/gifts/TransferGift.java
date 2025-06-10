package org.telegram.telegrambots.meta.api.methods.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Transfers an owned unique gift to another user.
 * Requires the can_transfer_and_upgrade_gifts business bot right.
 * Requires can_transfer_stars business bot right if the transfer is paid.
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
public class TransferGift extends BotApiMethodBoolean {
    public static final String PATH = "transferGift";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String OWNED_GIFT_ID_FIELD = "owned_gift_id";
    private static final String NEW_OWNER_CHAT_ID_FIELD = "new_owner_chat_id";
    private static final String STAR_COUNT_FIELD = "star_count";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Unique identifier of the regular gift that should be transferred
     */
    @JsonProperty(OWNED_GIFT_ID_FIELD)
    @NonNull
    private String ownedGiftId;

    /**
     * Unique identifier of the chat which will own the gift.
     * The chat must be active in the last 24 hours.
     */
    @JsonProperty(NEW_OWNER_CHAT_ID_FIELD)
    @NonNull
    private Long newOwnerChatId;

    /**
     * Optional.
     * The amount of Telegram Stars that will be paid for the transfer from the business account balance.
     * If positive, then the can_transfer_stars business bot right is required.
     */
    @JsonProperty(STAR_COUNT_FIELD)
    private Integer starCount;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId can't be empty", this);
        }
        if (ownedGiftId.isEmpty()) {
            throw new TelegramApiValidationException("OwnedGiftId can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
