package org.telegram.telegrambots.meta.api.methods.business;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestSetBusinessAccountName {

    @Test
    public void testSetBusinessAccountNameGetPath() {
        SetBusinessAccountName setBusinessAccountName = SetBusinessAccountName.builder()
                .businessConnectionId("test-connection-id")
                .firstName("Test First Name")
                .build();

        assertEquals("setBusinessAccountName", setBusinessAccountName.getMethod());
    }

    @Test
    public void testSetBusinessAccountNameValidation() {
        SetBusinessAccountName setBusinessAccountName = SetBusinessAccountName.builder()
                .businessConnectionId("test-connection-id")
                .firstName("Test First Name")
                .build();

        // Should not throw an exception
        try {
            setBusinessAccountName.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty business connection ID
        SetBusinessAccountName emptyConnectionId = SetBusinessAccountName.builder()
                .businessConnectionId("")
                .firstName("Test First Name")
                .build();

        assertThrows(TelegramApiValidationException.class, emptyConnectionId::validate);

        // Test with empty first name
        SetBusinessAccountName emptyFirstName = SetBusinessAccountName.builder()
                .businessConnectionId("test-connection-id")
                .firstName("")
                .build();

        assertThrows(TelegramApiValidationException.class, emptyFirstName::validate);

        // Test with first name exceeding max length
        StringBuilder longFirstName = new StringBuilder();
        for (int i = 0; i < 65; i++) {
            longFirstName.append("a");
        }

        SetBusinessAccountName longFirstNameObj = SetBusinessAccountName.builder()
                .businessConnectionId("test-connection-id")
                .firstName(longFirstName.toString())
                .build();

        assertThrows(TelegramApiValidationException.class, longFirstNameObj::validate);

        // Test with last name exceeding max length
        StringBuilder longLastName = new StringBuilder();
        for (int i = 0; i < 65; i++) {
            longLastName.append("a");
        }

        SetBusinessAccountName longLastNameObj = SetBusinessAccountName.builder()
                .businessConnectionId("test-connection-id")
                .firstName("Test First Name")
                .lastName(longLastName.toString())
                .build();

        assertThrows(TelegramApiValidationException.class, longLastNameObj::validate);
    }

    @Test
    public void testSetBusinessAccountNameWithLastName() {
        SetBusinessAccountName setBusinessAccountName = SetBusinessAccountName.builder()
                .businessConnectionId("test-connection-id")
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .build();

        assertEquals("test-connection-id", setBusinessAccountName.getBusinessConnectionId());
        assertEquals("Test First Name", setBusinessAccountName.getFirstName());
        assertEquals("Test Last Name", setBusinessAccountName.getLastName());
    }
}
