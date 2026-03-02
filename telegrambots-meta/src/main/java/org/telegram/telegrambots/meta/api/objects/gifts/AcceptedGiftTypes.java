package org.telegram.telegrambots.meta.api.objects.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * This object describes the types of gifts that can be gifted to a user or a chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcceptedGiftTypes implements BotApiObject {
    private static final String UNLIMITED_GIFTS_FIELD = "unlimited_gifts";
    private static final String LIMITED_GIFTS_FIELD = "limited_gifts";
    private static final String UNIQUE_GIFTS_FIELD = "unique_gifts";
    private static final String PREMIUM_SUBSCRIPTION_FIELD = "premium_subscription";
    private static final String GIFTS_FROM_CHANNELS_FIELD = "gifts_from_channels";

    /**
     * True, if unlimited regular gifts are accepted
     */
    @JsonProperty(UNLIMITED_GIFTS_FIELD)
    private Boolean unlimitedGifts;
    /**
     * True, if limited regular gifts are accepted
     */
    @JsonProperty(LIMITED_GIFTS_FIELD)
    private Boolean limitedGifts;
    /**
     * True, if unique gifts or gifts that can be upgraded to unique for free are accepted
     */
    @JsonProperty(UNIQUE_GIFTS_FIELD)
    private Boolean uniqueGifts;
    /**
     * True, if a Telegram Premium subscription is accepted
     */
    @JsonProperty(PREMIUM_SUBSCRIPTION_FIELD)
    private Boolean primalSubscription;
    /**
     * True, if transfers of unique gifts from channels are accepted
     */
    @JsonProperty(GIFTS_FROM_CHANNELS_FIELD)
    private Boolean giftsFromChannels;
}
