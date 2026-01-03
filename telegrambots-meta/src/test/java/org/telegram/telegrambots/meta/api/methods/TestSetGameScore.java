package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.games.SetGameScore;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.test.TelegramBotsHelper;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestSetGameScore {

    private SetGameScore setGameScore;
    private ObjectMapper mapper = JsonMapper.builder()
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .build();

    @BeforeEach
    void setUp() {
        setGameScore = new SetGameScore(98765L, 12);
        setGameScore.setChatId(12345L);
        setGameScore.setDisableEditMessage(true);
        setGameScore.setMessageId(54321);
    }

    @Test
    void TestGetUpdatesMustBeSerializable() throws Exception {
        String json = mapper.writeValueAsString(setGameScore);
        assertNotNull(json);
        assertEquals("{\"chat_id\":\"12345\",\"disable_edit_message\":true,\"message_id\":54321,\"method\":\"setGameScore\",\"score\":12,\"user_id\":98765}", json);
    }

    @Test
    void TestGetUpdatesMustDeserializeCorrectBooleanResponse() throws Exception {
        Serializable result =
                setGameScore.deserializeResponse(TelegramBotsHelper.GetSetGameScoreBooleanResponse());
        assertNotNull(result);
        assertTrue(result instanceof Boolean);
        assertTrue((Boolean) result);
    }

    @Test
    void TestGetUpdatesMustDeserializeCorrectMessageResponse() throws Exception {
        Serializable result = setGameScore.deserializeResponse(TelegramBotsHelper.GetSetGameScoreMessageResponse());
        assertNotNull(result);
        assertTrue(result instanceof Message);
    }
}
