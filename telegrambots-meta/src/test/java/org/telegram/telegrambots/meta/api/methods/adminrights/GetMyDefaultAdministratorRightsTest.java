package org.telegram.telegrambots.meta.api.methods.adminrights;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.adminrights.ChatAdministratorRights;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 6.0
 */
public class GetMyDefaultAdministratorRightsTest {
    @Test
    public void testGetMyDefaultAdministratorRightsWithAllSetForChannel() {
        GetMyDefaultAdministratorRights getMyDefaultAdministratorRights = GetMyDefaultAdministratorRights
                .builder()
                .forChannels(true)
                .build();
        assertEquals("getMyDefaultAdministratorRights", getMyDefaultAdministratorRights.getMethod());
        assertDoesNotThrow(getMyDefaultAdministratorRights::validate);
    }

    @Test
    public void testGetMyDefaultAdministratorRightsWithAllSetForGroups() {
        GetMyDefaultAdministratorRights getMyDefaultAdministratorRights = GetMyDefaultAdministratorRights
                .builder()
                .build();
        assertEquals("getMyDefaultAdministratorRights", getMyDefaultAdministratorRights.getMethod());
        assertDoesNotThrow(getMyDefaultAdministratorRights::validate);
    }

    @Test
    public void testGetMyDefaultAdministratorRightsDeserializeValidResponse() {
        String responseText = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"is_anonymous\": true,\n" +
                "        \"can_manage_chat\": true,\n" +
                "        \"can_delete_messages\": true,\n" +
                "        \"can_manage_video_chats\": true,\n" +
                "        \"can_restrict_members\": true,\n" +
                "        \"can_promote_members\": true,\n" +
                "        \"can_change_info\": true,\n" +
                "        \"can_invite_users\": true,\n" +
                "        \"can_post_messages\": true,\n" +
                "        \"can_edit_messages\": true,\n" +
                "        \"can_pin_messages\": true\n" +
                "    }\n" +
                "}";

        GetMyDefaultAdministratorRights getMyDefaultAdministratorRights = GetMyDefaultAdministratorRights
                .builder()
                .build();

        try {
            ChatAdministratorRights result = getMyDefaultAdministratorRights.deserializeResponse(responseText);
            assertNotNull(result);
            assertEquals(true, result.getIsAnonymous());
            assertEquals(true, result.getCanManageChat());
            assertEquals(true, result.getCanPostMessages());
            assertEquals(true, result.getCanEditMessages());
            assertEquals(true, result.getCanDeleteMessages());
            assertEquals(true, result.getCanManageVideoChats());
            assertEquals(true, result.getCanRestrictMembers());
            assertEquals(true, result.getCanPromoteMembers());
            assertEquals(true, result.getCanChangeInfo());
            assertEquals(true, result.getCanInviteUsers());
            assertEquals(true, result.getCanPinMessages());
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }
}
