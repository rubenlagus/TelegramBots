package org.telegram.telegrambots.meta.api.methods;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.UserProfileAudios;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestGetUserProfileAudios {

    @Test
    public void testGetUserProfileAudiosMethod() {
        GetUserProfileAudios getUserProfileAudios = GetUserProfileAudios.builder()
                .userId(12345L)
                .build();

        assertEquals("getuserprofileaudios", getUserProfileAudios.getMethod());
        assertEquals(12345L, getUserProfileAudios.getUserId());
    }

    @Test
    public void testGetUserProfileAudiosWithOffsetAndLimit() {
        GetUserProfileAudios getUserProfileAudios = GetUserProfileAudios.builder()
                .userId(67890L)
                .offset(5)
                .limit(50)
                .build();

        assertEquals("getuserprofileaudios", getUserProfileAudios.getMethod());
        assertEquals(67890L, getUserProfileAudios.getUserId());
        assertEquals(5, getUserProfileAudios.getOffset());
        assertEquals(50, getUserProfileAudios.getLimit());
    }

    @Test
    public void testGetUserProfileAudiosDeserializeValidResponse() {
        String responseText = "{\"ok\":true,\"result\":{\"total_count\":2,\"audios\":[{\"file_id\":\"audio1\",\"file_unique_id\":\"unique1\",\"duration\":180},{\"file_id\":\"audio2\",\"file_unique_id\":\"unique2\",\"duration\":240}]}}";

        GetUserProfileAudios getUserProfileAudios = GetUserProfileAudios.builder()
                .userId(12345L)
                .build();

        try {
            UserProfileAudios result = getUserProfileAudios.deserializeResponse(responseText);
            assertNotNull(result);
            assertEquals(2, result.getTotalCount());
            assertNotNull(result.getAudios());
            assertEquals(2, result.getAudios().size());
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetUserProfileAudiosErrorResponse() {
        String responseText = "{\"ok\":false,\"error_code\":404,\"description\":\"User not found\"}";

        GetUserProfileAudios getUserProfileAudios = GetUserProfileAudios.builder()
                .userId(12345L)
                .build();

        try {
            getUserProfileAudios.deserializeResponse(responseText);
            fail();
        } catch (TelegramApiRequestException e) {
            assertEquals(404, e.getErrorCode());
            assertEquals("User not found", e.getApiResponse());
        }
    }
}
