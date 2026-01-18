package org.telegram.telegrambots.meta.api.objects.gifts;

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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes a service message about a unique gift that was sent or received.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniqueGiftInfo implements BotApiObject {
    private static final String GIFT_FIELD = "gift";
    private static final String ORIGIN_FIELD = "origin";
    private static final String OWNED_GIFT_ID_FIELD = "owned_gift_id";
    private static final String TRANSFER_STAR_COUNT_FIELD = "transfer_star_count";
    private static final String LAST_RESALE_CURRENCY_FIELD = "last_resale_currency";
    private static final String LAST_RESALE_AMOUNT_FIELD = "last_resale_amount";
    private static final String NEXT_TRANSFER_DATE_FIELD = "next_transfer_date";

    /**
     * Information about the gift
     */
    @JsonProperty(GIFT_FIELD)
    @NonNull
    private UniqueGift gift;
    /**
     * Origin of the gift.
     * Currently, either "upgrade" for gifts upgraded from regular gifts, "transfer" for gifts transferred from other users or channels,
     * "resale" for gifts bought from other users, "gifted_upgrade" for upgrades purchased after the gift was sent,
     * or "offer" for gifts bought or sold through gift purchase offers
     */
    @JsonProperty(ORIGIN_FIELD)
    @NonNull
    private String origin;
    /**
     * Optional.
     * Unique identifier of the received gift for the bot; only present for gifts received on behalf of business accounts
     */
    @JsonProperty(OWNED_GIFT_ID_FIELD)
    private String ownedGiftId;
    /**
     * Optional.
     * Number of Telegram Stars that must be paid to transfer the gift; omitted if the bot cannot transfer the gift
     */
    @JsonProperty(TRANSFER_STAR_COUNT_FIELD)
    private Integer transferStarCount;
    /**
     * Optional.
     * For gifts bought from other users, the currency in which the payment for the gift was done.
     * Currently, one of "XTR" for Telegram Stars or "TON" for toncoins.
     */
    @JsonProperty(LAST_RESALE_CURRENCY_FIELD)
    private String lastResaleCurrency;
    /**
     * Optional.
     * For gifts bought from other users, the price paid for the gift in either Telegram Stars or nanotoncoins
     */
    @JsonProperty(LAST_RESALE_AMOUNT_FIELD)
    private Integer lastResaleAmount;
    /**
     * Optional.
     * Point in time (Unix timestamp) when the gift can be transferred. If it is in the past, then the gift can be transferred now
     */
    @JsonProperty(NEXT_TRANSFER_DATE_FIELD)
    private String nextTransferDate;

    /**
     * @deprecated Use lastResaleCurrency and lastResaleAmount fields instead
     */
    @Deprecated
    public Integer getLastResaleStarCount() {
        if ("XTR".equals(lastResaleCurrency)) {
            return lastResaleAmount;
        }
        return null;
    }

    /**
     * @deprecated Use lastResaleCurrency and lastResaleAmount fields instead
     */
    @Deprecated
    public void setLastResaleStarCount(Integer lastResaleStarCount) {
        if (lastResaleStarCount != null) {
            this.lastResaleCurrency = "XTR";
            this.lastResaleAmount = lastResaleStarCount;
        } else {
            this.lastResaleCurrency = null;
            this.lastResaleAmount = null;
        }
    }

    /**
     * Builder helper class for SuperBuilder pattern
     */
    public abstract static class UniqueGiftInfoBuilder<C extends UniqueGiftInfo, B extends UniqueGiftInfoBuilder<C, B>> {
        /**
         * @deprecated Use lastResaleCurrency and lastResaleAmount builder methods instead
         */
        @Deprecated
        public B lastResaleStarCount(Integer lastResaleStarCount) {
            if (lastResaleStarCount != null) {
                this.lastResaleCurrency = "XTR";
                this.lastResaleAmount = lastResaleStarCount;
            } else {
                this.lastResaleCurrency = null;
                this.lastResaleAmount = null;
            }
            return self();
        }
    }
}
