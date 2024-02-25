package org.telegram.telegrambots.meta.test.apimethods;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.games.SetGameScore;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.test.TelegramBotsHelper;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestSetGameScore {

    private SetGameScore setGameScore;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        setGameScore = new SetGameScore();
        setGameScore.setChatId(12345L);
        setGameScore.setDisableEditMessage(true);
        setGameScore.setMessageId(54321);
        setGameScore.setScore(12);
        setGameScore.setUserId(98765L);
    }

    @Test
    void TestGetUpdatesMustBeSerializable() throws Exception {
        String json = mapper.writeValueAsString(setGameScore);
        assertNotNull(json);
        assertEquals("{\"chat_id\":\"12345\",\"message_id\":54321,\"disable_edit_message\":true,\"user_id\":98765,\"score\":12,\"method\":\"setGameScore\"}", json);
    }

    @Test
    void TestGetUpdatesMustDeserializeCorrectBooleanResponse() throws Exception {
        Serializable result =
                setGameScore.deserializeResponse(TelegramBotsHelper.GetSetGameScoreBooleanResponse());
        assertNotNull(result);
        assertInstanceOf(Boolean.class, result);
        assertTrue((Boolean) result);
    }

    @Test
    void TestGetUpdatesMustDeserializeCorrectMessageResponse() throws Exception {
        Serializable result = setGameScore.deserializeResponse(TelegramBotsHelper.GetSetGameScoreMessageResponse());
        assertNotNull(result);
        assertInstanceOf(Message.class, result);
    }
}
