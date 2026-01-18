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
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

/**
 * @author Ruben Bermudez
 * @version 8.0
 *
 * This object represents a gift that can be sent by the bot.
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
public class Gift implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String STICKER_FIELD = "sticker";
    private static final String STAR_COUNT_FIELD = "star_count";
    private static final String TOTAL_COUNT_FIELD = "total_count";
    private static final String REMAINING_COUNT_FIELD = "remaining_count";
    private static final String PERSONAL_TOTAL_COUNT_FIELD = "personal_total_count";
    private static final String PERSONAL_REMAINING_COUNT_FIELD = "personal_remaining_count";
    private static final String UPGRADE_STAR_COUNT_FIELD = "upgrade_star_count";
    private static final String PUBLISHER_CHAT_FIELD = "publisher_chat";
    private static final String IS_PREMIUM_FIELD = "is_premium";
    private static final String HAS_COLORS_FIELD = "has_colors";
    private static final String BACKGROUND_FIELD = "background";
    private static final String UNIQUE_GIFT_VARIANT_COUNT_FIELD = "unique_gift_variant_count";

    /**
     * Unique identifier of the gift
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id;
    /**
     * The sticker that represents the gift
     */
    @JsonProperty(STICKER_FIELD)
    @NonNull
    private Sticker sticker;
    /**
     * The number of Telegram Stars that must be paid to send the sticker
     */
    @JsonProperty(STAR_COUNT_FIELD)
    @NonNull
    private Integer starCount;
    /**
     * Optional.
     * The total number of the gifts of this type that can be sent; for limited gifts only
     */
    @JsonProperty(TOTAL_COUNT_FIELD)
    private Integer totalCount;
    /**
     * Optional.
     * The number of remaining gifts of this type that can be sent; for limited gifts only
     */
    @JsonProperty(REMAINING_COUNT_FIELD)
    private Integer remainingCount;
    /**
     * Optional.
     * The total number of gifts of this type that can be sent by the bot; for limited gifts only
     */
    @JsonProperty(PERSONAL_TOTAL_COUNT_FIELD)
    private Integer personalTotalCount;
    /**
     * Optional.
     * The number of remaining gifts of this type that can be sent by the bot; for limited gifts only
     */
    @JsonProperty(PERSONAL_REMAINING_COUNT_FIELD)
    private Integer personalRemainingCount;
    /**
     * Optional.
     * The number of Telegram Stars that must be paid to upgrade the gift to a unique one
     */
    @JsonProperty(UPGRADE_STAR_COUNT_FIELD)
    private Integer upgradeStarCount;
    /**
     * Optional.
     * Information about the chat that published the gift
     */
    @JsonProperty(PUBLISHER_CHAT_FIELD)
    private Chat publisherChat;
    /**
     * Optional.
     * True, if the gift can only be purchased by Telegram Premium subscribers
     */
    @JsonProperty(IS_PREMIUM_FIELD)
    private Boolean isPremium;
    /**
     * Optional.
     * True, if the gift can be used (after being upgraded) to customize a user's appearance
     */
    @JsonProperty(HAS_COLORS_FIELD)
    private Boolean hasColors;
    /**
     * Optional.
     * Background of the gift
     */
    @JsonProperty(BACKGROUND_FIELD)
    private GiftBackground background;
    /**
     * Optional.
     * The total number of different unique gifts that can be obtained by upgrading the gift
     */
    @JsonProperty(UNIQUE_GIFT_VARIANT_COUNT_FIELD)
    private Integer uniqueGiftVariantCount;
}
