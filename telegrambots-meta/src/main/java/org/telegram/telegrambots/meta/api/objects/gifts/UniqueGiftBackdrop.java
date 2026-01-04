package org.telegram.telegrambots.meta.api.objects.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * This object describes the backdrop of a unique gift.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniqueGiftBackdrop implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String COLORS_FIELD = "colors";
    private static final String RARITY_PER_MILLE_FIELD = "rarity_per_mille";

    /**
     * Name of the backdrop
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
    /**
     * Colors of the backdrop
     */
    @JsonProperty(COLORS_FIELD)
    @NonNull
    private UniqueGiftBackdropColors colors;
    /**
     * The number of unique gifts that receive this backdrop for every 1000 gifts upgraded
     */
    @JsonProperty(RARITY_PER_MILLE_FIELD)
    @NonNull
    private Integer rarityPerMille;
}
