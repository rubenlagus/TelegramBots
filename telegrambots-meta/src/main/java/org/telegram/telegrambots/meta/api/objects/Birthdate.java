package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @version 7.2
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
public class Birthdate implements BotApiObject {
    private static final String DAY_FIELD = "day";
    private static final String MONTH_FIELD = "month";
    private static final String YEAR_FIELD = "year";

    /**
     * Day of the user's birth; 1-31
     */
    @JsonProperty(DAY_FIELD)
    @NonNull
    private Integer day;
    /**
     * Month of the user's birth; 1-12
     */
    @JsonProperty(MONTH_FIELD)
    @NonNull
    private Integer month;
    /**
     * Optional.
     * Year of the user's birth
     */
    @JsonProperty(YEAR_FIELD)
    private Integer year;
}
