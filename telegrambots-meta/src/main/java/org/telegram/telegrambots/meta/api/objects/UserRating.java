package org.telegram.telegrambots.meta.api.objects;

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

/**
 * @author Ruben Bermudez
 * @version 9.3
 * This object describes the rating of a user based on their Telegram Star spendings.
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
public class UserRating implements BotApiObject {
    private static final String LEVEL_FIELD = "level";
    private static final String RATING_FIELD = "rating";
    private static final String CURRENT_LEVEL_RATING_FIELD = "current_level_rating";
    private static final String NEXT_LEVEL_RATING_FIELD = "next_level_rating";

    /**
     * Current level of the user, indicating their reliability when purchasing digital goods and services.
     * A higher level suggests a more trustworthy customer; a negative level is likely reason for concern.
     */
    @JsonProperty(LEVEL_FIELD)
    @NonNull
    private Integer level;
    /**
     * Numerical value of the user's rating; the higher the rating, the better
     */
    @JsonProperty(RATING_FIELD)
    @NonNull
    private Integer rating;
    /**
     * The rating value required to get the current level
     */
    @JsonProperty(CURRENT_LEVEL_RATING_FIELD)
    @NonNull
    private Integer currentLevelRating;
    /**
     * Optional.
     * The rating value required to get to the next level; omitted if the maximum level was reached
     */
    @JsonProperty(NEXT_LEVEL_RATING_FIELD)
    private Integer nextLevelRating;
}
