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
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * This object describes a unique gift that was upgraded from a regular gift.
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
public class UniqueGift implements BotApiObject {
    private static final String BASE_NAME_FIELD = "base_name";
    private static final String NAME_FIELD = "name";
    private static final String NUMBER_FIELD = "number";
    private static final String MODEL_FIELD = "model";
    private static final String SYMBOL_FIELD = "symbol";
    private static final String BACKDROP_FIELD = "backdrop";
    private static final String PUBLISHER_CHAT_FIELD = "publisher_chat";
    private static final String GIFT_ID_FIELD = "gift_id";
    private static final String IS_PREMIUM_FIELD = "is_premium";
    private static final String IS_FROM_BLOCKCHAIN_FIELD = "is_from_blockchain";
    private static final String COLORS_FIELD = "colors";
    private static final String IS_BURNED_FIELD = "is_burned";

    /**
     * Human-readable name of the regular gift from which this unique gift was upgraded
     */
    @JsonProperty(BASE_NAME_FIELD)
    @NonNull
    private String baseName;
    /**
     * Unique name of the gift. This name can be used in https://t.me/nft/... links and story areas
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
    /**
     * Unique number of the upgraded gift among gifts upgraded from the same regular gift
     */
    @JsonProperty(NUMBER_FIELD)
    @NonNull
    private Integer number;
    /**
     * Model of the gift
     */
    @JsonProperty(MODEL_FIELD)
    private UniqueGiftModel model;
    /**
     * Symbol of the gift
     */
    @JsonProperty(SYMBOL_FIELD)
    private UniqueGiftSymbol symbol;
    /**
     * Backdrop of the gift
     */
    @JsonProperty(BACKDROP_FIELD)
    private UniqueGiftBackdrop backdrop;

    /**
     * Optional.
     * Information about the chat that published the gift
     */
    @JsonProperty(PUBLISHER_CHAT_FIELD)
    private Chat publisherChat;
    /**
     * Identifier of the regular gift from which the gift was upgraded
     */
    @JsonProperty(GIFT_ID_FIELD)
    @NonNull
    private String giftId;
    /**
     * Optional.
     * True, if the original regular gift was exclusively purchaseable by Telegram Premium subscribers
     */
    @JsonProperty(IS_PREMIUM_FIELD)
    private Boolean isPremium;
    /**
     * Optional.
     * True, if the gift is assigned from the TON blockchain and can't be resold or transferred in Telegram
     */
    @JsonProperty(IS_FROM_BLOCKCHAIN_FIELD)
    private Boolean isFromBlockchain;
    /**
     * Optional.
     * The color scheme that can be used by the gift's owner for the chat's name, replies to messages and link previews;
     * for business account gifts and gifts that are currently on sale only
     */
    @JsonProperty(COLORS_FIELD)
    private UniqueGiftColors colors;
    /**
     * Optional.
     * True, if the gift was used to craft another gift and isn't available anymore
     */
    @JsonProperty(IS_BURNED_FIELD)
    private Boolean isBurned;
}
