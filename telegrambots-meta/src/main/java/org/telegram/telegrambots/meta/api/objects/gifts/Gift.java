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
    private static final String UPGRADE_STAR_COUNT_FIELD = "upgrade_star_count";

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
     * The number of Telegram Stars that must be paid to upgrade the gift to a unique one
     */
    @JsonProperty(UPGRADE_STAR_COUNT_FIELD)
    private Integer upgradeStarCount;
}
