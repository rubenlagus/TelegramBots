package org.telegram.telegrambots.meta.api.objects.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestLocation {

    @Test
    public void testLocationConstructor() {
        Location location = Location.builder()
                .latitude(40.7128d)
                .longitude(-74.0060d)
                .build();

        assertEquals(40.7128d, location.getLatitude());
        assertEquals(-74.0060d, location.getLongitude());
        assertNull(location.getHorizontalAccuracy());
        assertNull(location.getLivePeriod());
        assertNull(location.getHeading());
        assertNull(location.getProximityAlertRadius());
    }

    @Test
    public void testLocationWithAllFields() {
        Location location = Location.builder()
                .latitude(40.7128d)
                .longitude(-74.0060d)
                .horizontalAccuracy(50.0d)
                .livePeriod(300)
                .heading(90)
                .proximityAlertRadius(200)
                .build();

        assertEquals(40.7128d, location.getLatitude());
        assertEquals(-74.0060d, location.getLongitude());
        assertEquals(50.0f, location.getHorizontalAccuracy());
        assertEquals(300, location.getLivePeriod());
        assertEquals(90, location.getHeading());
        assertEquals(200, location.getProximityAlertRadius());
    }

    @Test
    public void testLocationSettersAndGetters() {
        Location location = Location.builder()
                .latitude(40.7128d)
                .longitude(-74.0060d)
                .build();

        location.setHorizontalAccuracy(50.0d);
        location.setLivePeriod(300);
        location.setHeading(90);
        location.setProximityAlertRadius(200);

        assertEquals(40.7128d, location.getLatitude());
        assertEquals(-74.0060d, location.getLongitude());
        assertEquals(50.0f, location.getHorizontalAccuracy());
        assertEquals(300, location.getLivePeriod());
        assertEquals(90, location.getHeading());
        assertEquals(200, location.getProximityAlertRadius());
    }
}
