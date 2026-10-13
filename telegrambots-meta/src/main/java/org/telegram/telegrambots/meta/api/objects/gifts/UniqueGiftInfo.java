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
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

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
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String IS_PRIVATE_FIELD = "is_private";

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
     * Optional.
     * Text of the message that was added to the gift
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional.
     * Special entities that appear in the text
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * Optional.
     * True, if the sender and gift text are shown only to the gift receiver;
     * otherwise, everyone will be able to see them
     */
    @JsonProperty(IS_PRIVATE_FIELD)
    private Boolean isPrivate;
}
