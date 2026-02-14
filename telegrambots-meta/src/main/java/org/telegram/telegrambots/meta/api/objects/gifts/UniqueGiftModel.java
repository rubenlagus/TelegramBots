package org.telegram.telegrambots.meta.api.objects.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * This object describes the model of a unique gift.
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
public class UniqueGiftModel implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String STICKER_FIELD = "sticker";
    private static final String RARITY_PER_MILLE_FIELD = "rarity_per_mille";
    private static final String RARITY_FIELD = "rarity";

    /**
     * Name of the model
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
    /**
     * The sticker that represents the unique gift
     */
    @JsonProperty(STICKER_FIELD)
    @NonNull
    private Sticker sticker;
    /**
     * The number of unique gifts that receive this model for every 1000 gifts upgraded.
     * Always 0 for crafted gifts.
     */
    @JsonProperty(RARITY_PER_MILLE_FIELD)
    @NonNull
    private Integer rarityPerMille;
    /**
     * Optional.
     * Rarity of the model if it is a crafted model.
     * Currently, can be "uncommon", "rare", "epic", or "legendary".
     */
    @JsonProperty(RARITY_FIELD)
    private String rarity;
}
