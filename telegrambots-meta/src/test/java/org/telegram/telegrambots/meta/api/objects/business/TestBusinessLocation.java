package org.telegram.telegrambots.meta.api.objects.business;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.location.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestBusinessLocation {

    @Test
    public void testBusinessLocationConstructor() {
        Location location = mock(Location.class);
        
        BusinessLocation businessLocation = BusinessLocation.builder()
                .location(location)
                .address("123 Main St, New York, NY 10001")
                .build();

        assertEquals(location, businessLocation.getLocation());
        assertEquals("123 Main St, New York, NY 10001", businessLocation.getAddress());
    }

    @Test
    public void testBusinessLocationWithAllFields() {
        Location location = mock(Location.class);
        
        BusinessLocation businessLocation = BusinessLocation.builder()
                .location(location)
                .address("123 Main St, New York, NY 10001")
                .build();

        assertEquals(location, businessLocation.getLocation());
        assertEquals("123 Main St, New York, NY 10001", businessLocation.getAddress());
    }

    @Test
    public void testBusinessLocationSettersAndGetters() {
        Location location1 = mock(Location.class);
        Location location2 = mock(Location.class);
        
        BusinessLocation businessLocation = BusinessLocation.builder()
                .location(location1)
                .address("Initial Address")
                .build();

        assertEquals(location1, businessLocation.getLocation());
        assertEquals("Initial Address", businessLocation.getAddress());

        // Test setters
        businessLocation.setLocation(location2);
        businessLocation.setAddress("Updated Address");

        assertEquals(location2, businessLocation.getLocation());
        assertEquals("Updated Address", businessLocation.getAddress());
    }
}
