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
 * Upgrades a given regular gift to a unique gift.
 * Requires the can_transfer_and_upgrade_gifts business bot right.
 * Additionally requires the can_transfer_stars business bot right if the upgrade is paid.
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
public class UpgradeGift extends BotApiMethodBoolean {
    public static final String PATH = "upgradeGift";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String OWNED_GIFT_ID_FIELD = "owned_gift_id";
    private static final String KEEP_ORIGINAL_DETAILS_FIELD = "keep_original_details";
    private static final String STAR_COUNT_FIELD = "star_count";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Unique identifier of the regular gift that should be upgraded to a unique one
     */
    @JsonProperty(OWNED_GIFT_ID_FIELD)
    @NonNull
    private String ownedGiftId;

    /**
     * Optional.
     * Pass True to keep the original gift text, sender and receiver in the upgraded gift
     */
    @JsonProperty(KEEP_ORIGINAL_DETAILS_FIELD)
    private Boolean keepOriginalDetails;

    /**
     * Optional.
     * The amount of Telegram Stars that will be paid for the upgrade from the business account balance.
     * If gift.prepaid_upgrade_star_count > 0, then pass 0, otherwise, the can_transfer_stars business bot right is required
     * and gift.upgrade_star_count must be passed.
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
