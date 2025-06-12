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
 * Describes a service message about a regular gift that was sent or received.
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
public class GiftInfo implements BotApiObject {
    private static final String GIFT_FIELD = "gift";
    private static final String OWNED_GIFT_ID_FIELD = "owned_gift_id";
    private static final String CONVERT_STAR_COUNT_FIELD = "convert_star_count";
    private static final String PREPAID_UPGRADE_STAR_COUNT_FIELD = "prepaid_upgrade_star_count";
    private static final String CAN_BE_UPGRADED_FIELD = "can_be_upgraded";
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String IS_PRIVATE_FIELD = "is_private";

    /**
     * Information about the gift
     */
    @JsonProperty(GIFT_FIELD)
    @NonNull
    private Gift gift;
    /**
     * Optional.
     * Unique identifier of the received gift for the bot; only present for gifts received on behalf of business accounts
     */
    @JsonProperty(OWNED_GIFT_ID_FIELD)
    private String ownedGiftId;
    /**
     * Optional.
     * Number of Telegram Stars that can be claimed by the receiver by converting the gift; omitted if conversion to Telegram Stars is impossible
     */
    @JsonProperty(CONVERT_STAR_COUNT_FIELD)
    @NonNull
    private Integer convertStarCount;
    /**
     * Optional.
     * Number of Telegram Stars that were prepaid by the sender for the ability to upgrade the gift
     */
    @JsonProperty(PREPAID_UPGRADE_STAR_COUNT_FIELD)
    private Integer prepaidUpgradeStarCount;
    /**
     * Optional.
     * True, if the gift can be upgraded to a unique gift
     */
    @JsonProperty(CAN_BE_UPGRADED_FIELD)
    private Boolean canBeUpgraded;
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
     * True, if the sender and gift text are shown only to the gift receiver; otherwise, everyone will be able to see them
     */
    @JsonProperty(IS_PRIVATE_FIELD)
    private Boolean isPrivate;
}
