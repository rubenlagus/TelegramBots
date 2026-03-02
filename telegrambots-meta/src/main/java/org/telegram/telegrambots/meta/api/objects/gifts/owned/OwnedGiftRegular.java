package org.telegram.telegrambots.meta.api.objects.gifts.owned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.gifts.Gift;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes a regular gift owned by a user or a chat.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class OwnedGiftRegular extends OwnedGift {
    private static final String TYPE = "regular";
    
    private static final String GIFT_FIELD = "gift";
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String IS_PRIVATE_FIELD = "is_private";
    private static final String WAS_REFUNDED_FIELD = "was_refunded";
    private static final String CAN_BE_UPGRADED_FIELD = "can_be_upgraded";
    private static final String CONVERT_STAR_COUNT_FIELD = "convert_star_count";
    private static final String PREPAID_UPGRADE_STAR_COUNT_FIELD = "prepaid_upgrade_star_count";
    private static final String IS_UPGRADE_SEPARATE_FIELD = "is_upgrade_separate";
    private static final String UNIQUE_GIFT_NUMBER_FIELD = "unique_gift_number";

    /**
     * Information about the regular gift
     */
    @JsonProperty(GIFT_FIELD)
    @NonNull
    private Gift gift;
    
    /**
     * Optional. Text of the message that was added to the gift
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    
    /**
     * Optional. Special entities that appear in the text
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    
    /**
     * Optional. True, if the sender and gift text are shown only to the gift receiver; 
     * otherwise, everyone will be able to see them
     */
    @JsonProperty(IS_PRIVATE_FIELD)
    private Boolean isPrivate;
    
    /**
     * Optional. True, if the gift was refunded and isn't available anymore
     */
    @JsonProperty(WAS_REFUNDED_FIELD)
    private Boolean wasRefunded;
    
    /**
     * Optional. True, if the gift can be upgraded to a unique gift; 
     * for gifts received on behalf of business accounts only
     */
    @JsonProperty(CAN_BE_UPGRADED_FIELD)
    private Boolean canBeUpgraded;
    
    /**
     * Optional. Number of Telegram Stars that can be claimed by the receiver instead of the gift; 
     * omitted if the gift cannot be converted to Telegram Stars
     */
    @JsonProperty(CONVERT_STAR_COUNT_FIELD)
    private Integer convertStarCount;
    
    /**
     * Optional. Number of Telegram Stars that were paid by the sender for the ability to upgrade the gift
     */
    @JsonProperty(PREPAID_UPGRADE_STAR_COUNT_FIELD)
    private Integer prepaidUpgradeStarCount;

    /**
     * Optional. True, if the gift's upgrade was purchased after the gift was sent;
     * for gifts received on behalf of business accounts only
     */
    @JsonProperty(IS_UPGRADE_SEPARATE_FIELD)
    private Boolean isUpgradeSeparate;

    /**
     * Optional. Unique number reserved for this gift when upgraded. See the number field in UniqueGift
     */
    @JsonProperty(UNIQUE_GIFT_NUMBER_FIELD)
    private Integer uniqueGiftNumber;

    @Override
    public String getType() {
        return TYPE;
    }
}
