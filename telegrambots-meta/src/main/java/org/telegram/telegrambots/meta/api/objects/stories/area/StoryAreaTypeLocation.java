package org.telegram.telegrambots.meta.api.objects.stories.area;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.objects.location.LocationAddress;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Describes a story area pointing to a location. 
 * Currently, a story can have up to 10 location areas.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
public class StoryAreaTypeLocation extends StoryAreaType {
    private static final String TYPE = "location";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String ADDRESS_FIELD = "address";

    /**
     * Location latitude in degrees
     */
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Float latitude;

    /**
     * Location longitude in degrees
     */
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Float longitude;

    /**
     * Optional. Address of the location
     */
    @JsonProperty(ADDRESS_FIELD)
    @NonNull
    private LocationAddress address;

    @Override
    public void validate() throws TelegramApiValidationException {
        address.validate();
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
