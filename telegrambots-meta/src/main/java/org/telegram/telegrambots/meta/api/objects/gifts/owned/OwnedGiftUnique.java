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
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGift;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes a unique gift received and owned by a user or a chat.
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
public class OwnedGiftUnique extends OwnedGift {
    private static final String TYPE = "unique";
    
    private static final String GIFT_FIELD = "gift";
    private static final String CAN_BE_TRANSFERRED_FIELD = "can_be_transferred";
    private static final String TRANSFER_STAR_COUNT_FIELD = "transfer_star_count";
    private static final String NEXT_TRANSFER_DATE_FIELD = "next_transfer_date";

    /**
     * Information about the unique gift
     */
    @JsonProperty(GIFT_FIELD)
    @NonNull
    private UniqueGift gift;
    
    /**
     * Optional. True, if the gift can be transferred to another owner; 
     * for gifts received on behalf of business accounts only
     */
    @JsonProperty(CAN_BE_TRANSFERRED_FIELD)
    private Boolean canBeTransferred;
    
    /**
     * Optional. Number of Telegram Stars that must be paid to transfer the gift; 
     * omitted if the bot cannot transfer the gift
     */
    @JsonProperty(TRANSFER_STAR_COUNT_FIELD)
    private Integer transferStarCount;

    /**
     * Optional.
     * Point in time (Unix timestamp) when the gift can be transferred.
     * If it is in the past, then the gift can be transferred now
     */
    @JsonProperty(NEXT_TRANSFER_DATE_FIELD)
    private String nextTransferDate;

    @Override
    public String getType() {
        return TYPE;
    }
}
