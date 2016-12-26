package org.telegram.telegrambots.test.apimethods;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.test.TelegramBotsHelper;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.objects.Message;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestSetGameScore {

    private SetGameScore setGameScore;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setGameScore = new SetGameScore();
        setGameScore.setChatId("12345");
        setGameScore.setDisableEditMessage(true);
        setGameScore.setMessageId(54321);
        setGameScore.setScore(12);
        setGameScore.setUserId(98765);
    }

    @Test
    public void TestGetUpdatesMustBeSerializable() throws Exception {
        String json = mapper.writeValueAsString(setGameScore);
        Assert.assertNotNull(json);
        Assert.assertEquals("{\"chat_id\":\"12345\",\"message_id\":54321,\"disable_edit_message\":true,\"user_id\":98765,\"score\":12,\"method\":\"setGameScore\"}", json);
    }

    @Test
    public void TestGetUpdatesMustDeserializeCorrectResponse() throws Exception {
        Serializable result =
                setGameScore.deserializeResponse(TelegramBotsHelper.GetSetGameScoreBooleanResponse());
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Boolean);
        Assert.assertTrue((Boolean) result);
    }

    @Test
    public void TestGetUpdatesMustThrowAnExceptionForInCorrectResponse() throws Exception {
        Serializable result = setGameScore.deserializeResponse(TelegramBotsHelper.GetSetGameScoreMessageResponse());
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Message);
    }
}
