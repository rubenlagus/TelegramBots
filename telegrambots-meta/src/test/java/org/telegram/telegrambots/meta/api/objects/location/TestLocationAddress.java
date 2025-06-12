package org.telegram.telegrambots.meta.api.objects.location;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestLocationAddress {

    @Test
    public void testLocationAddressConstructor() {
        LocationAddress locationAddress = LocationAddress.builder()
                .street("123 Main St")
                .city("New York")
                .countryCode("US")
                .build();

        assertEquals("123 Main St", locationAddress.getStreet());
        assertEquals("New York", locationAddress.getCity());
        assertEquals("US", locationAddress.getCountryCode());
    }

    @Test
    public void testLocationAddressWithAllFields() {
        LocationAddress locationAddress = LocationAddress.builder()
                .street("123 Main St")
                .city("New York")
                .state("NY")
                .countryCode("US")
                .build();

        assertEquals("123 Main St", locationAddress.getStreet());
        assertEquals("New York", locationAddress.getCity());
        assertEquals("NY", locationAddress.getState());
        assertEquals("US", locationAddress.getCountryCode());
    }

    @Test
    public void testLocationAddressValidation() {
        LocationAddress locationAddress = LocationAddress.builder()
                .street("123 Main St")
                .city("New York")
                .countryCode("US")
                .build();

        // Should not throw an exception
        try {
            locationAddress.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty country code
        LocationAddress emptyCountryCode = LocationAddress.builder()
                .street("123 Main St")
                .city("New York")
                .countryCode("")
                .build();

        assertThrows(TelegramApiValidationException.class, emptyCountryCode::validate);
    }
}
