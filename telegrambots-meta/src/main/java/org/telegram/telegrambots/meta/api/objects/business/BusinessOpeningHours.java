package org.telegram.telegrambots.meta.api.objects.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.2
 * Describes the opening hours of a business.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessOpeningHours implements BotApiObject {
    private static final String TIME_ZONE_NAME_FIELD = "time_zone_name";
    private static final String OPENING_HOURS_FIELD = "opening_hours";

    /**
     * Unique name of the time zone for which the opening hours are defined
     */
    @JsonProperty(TIME_ZONE_NAME_FIELD)
    @NonNull
    private String timeZone;
    /**
     * List of time intervals describing business opening hours
     */
    @JsonProperty(OPENING_HOURS_FIELD)
    @NonNull
    private List<BusinessOpeningHoursInterval> openingHours;
}
