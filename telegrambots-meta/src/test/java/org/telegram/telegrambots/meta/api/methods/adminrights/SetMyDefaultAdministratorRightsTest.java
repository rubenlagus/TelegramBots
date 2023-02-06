package org.telegram.telegrambots.meta.api.methods.adminrights;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.adminrights.ChatAdministratorRights;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ruben Bermudez
 * @version 6.0
 */
public class SetMyDefaultAdministratorRightsTest {

    @Test
    public void testSetMyDefaultAdministratorRightsWithNone() {
        SetMyDefaultAdministratorRights setMyDefaultAdministratorRights = SetMyDefaultAdministratorRights
                .builder()
                .build();
        assertEquals("setMyDefaultAdministratorRights", setMyDefaultAdministratorRights.getMethod());
        assertDoesNotThrow(setMyDefaultAdministratorRights::validate);
    }

    @Test
    public void testSetMyDefaultAdministratorRightsForChannels() {
        SetMyDefaultAdministratorRights setMyDefaultAdministratorRights = SetMyDefaultAdministratorRights
                .builder()
                .forChannels(true)
                .build();
        assertEquals("setMyDefaultAdministratorRights", setMyDefaultAdministratorRights.getMethod());
        assertDoesNotThrow(setMyDefaultAdministratorRights::validate);
    }

    @Test
    public void testSetMyDefaultAdministratorRightsWithAllSet() {
        SetMyDefaultAdministratorRights setMyDefaultAdministratorRights = SetMyDefaultAdministratorRights
                .builder()
                .forChannels(true)
                .rights(ChatAdministratorRights
                        .builder()
                        .isAnonymous(true)
                        .canManageChat(false)
                        .canDeleteMessages(false)
                        .canManageVideoChats(false)
                        .canRestrictMembers(false)
                        .canPromoteMembers(true)
                        .canChangeInfo(true)
                        .canInviteUsers(false)
                        .build())
                .build();
        assertEquals("setMyDefaultAdministratorRights", setMyDefaultAdministratorRights.getMethod());
        assertDoesNotThrow(setMyDefaultAdministratorRights::validate);
    }

    @Test
    public void testSetMyDefaultAdministratorRightsDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\": true}";

        SetMyDefaultAdministratorRights setMyDefaultAdministratorRights = SetMyDefaultAdministratorRights
                .builder()
                .build();

        try {
            boolean result = setMyDefaultAdministratorRights.deserializeResponse(responseText);
            assertTrue(result);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetMyDefaultAdministratorRightsDeserializeErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\": 404,\"description\": \"Error message\"}";

        SetMyDefaultAdministratorRights setMyDefaultAdministratorRights = SetMyDefaultAdministratorRights
                .builder()
                .build();

        try {
            setMyDefaultAdministratorRights.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(404, e.getErrorCode());
            assertEquals("Error message", e.getApiResponse());
        }
    }

    @Test
    public void testSetMyDefaultAdministratorRightsDeserializeWithWrongObject() {
        String responseText = "{\"ok\":false\"error_code\": 404,\"description\": \"Error message\"}";

        SetMyDefaultAdministratorRights setMyDefaultAdministratorRights = SetMyDefaultAdministratorRights
                .builder()
                .build();

        try {
            setMyDefaultAdministratorRights.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals("Unable to deserialize response", e.getMessage());
        }
    }
}
