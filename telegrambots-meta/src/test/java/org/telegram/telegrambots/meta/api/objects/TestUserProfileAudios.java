package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 9.4
 */
public class TestUserProfileAudios {

    @Test
    public void testUserProfileAudiosBuilder() {
        Audio audio1 = Audio.builder()
                .fileId("audio1_file_id")
                .fileUniqueId("audio1_unique_id")
                .duration(180)
                .build();

        Audio audio2 = Audio.builder()
                .fileId("audio2_file_id")
                .fileUniqueId("audio2_unique_id")
                .duration(240)
                .build();

        List<Audio> audios = Arrays.asList(audio1, audio2);

        UserProfileAudios userProfileAudios = UserProfileAudios.builder()
                .totalCount(2)
                .audios(audios)
                .build();

        assertNotNull(userProfileAudios);
        assertEquals(2, userProfileAudios.getTotalCount());
        assertNotNull(userProfileAudios.getAudios());
        assertEquals(2, userProfileAudios.getAudios().size());
        assertEquals("audio1_file_id", userProfileAudios.getAudios().get(0).getFileId());
        assertEquals("audio2_file_id", userProfileAudios.getAudios().get(1).getFileId());
    }

    @Test
    public void testUserProfileAudiosWithMoreTotalCount() {
        Audio audio = Audio.builder()
                .fileId("audio_file_id")
                .fileUniqueId("audio_unique_id")
                .duration(200)
                .build();

        UserProfileAudios userProfileAudios = UserProfileAudios.builder()
                .totalCount(10)
                .audios(Collections.singletonList(audio))
                .build();

        assertNotNull(userProfileAudios);
        assertEquals(10, userProfileAudios.getTotalCount());
        assertEquals(1, userProfileAudios.getAudios().size());
    }
}
