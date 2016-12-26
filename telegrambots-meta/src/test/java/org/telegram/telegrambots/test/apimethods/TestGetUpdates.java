package org.telegram.telegrambots.test.apimethods;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.test.TelegramBotsHelper;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestGetUpdates {

    private GetUpdates getUpdates;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        getUpdates = new GetUpdates();
        getUpdates.setOffset(15);
        getUpdates.setTimeout(50);
        getUpdates.setLimit(100);
    }

    @Test
    public void TestGetUpdatesMustBeSerializable() throws Exception {
        String json = mapper.writeValueAsString(getUpdates);
        Assert.assertNotNull(json);
        Assert.assertEquals("{\"offset\":15,\"limit\":100,\"timeout\":50,\"method\":\"getupdates\"}", json);
    }

    @Test
    public void TestGetUpdatesMustDeserializeCorrectResponse() throws Exception {
        ArrayList<Update> result =
                getUpdates.deserializeResponse(TelegramBotsHelper.GetResponseWithoutError());
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void TestGetUpdatesMustThrowAnExceptionForInCorrectResponse() {
        try {
            getUpdates.deserializeResponse(TelegramBotsHelper.GetResponseWithError());
        } catch (TelegramApiRequestException e) {
            Assert.assertNotNull(e.getParameters());
            Assert.assertEquals(Integer.valueOf(400), e.getErrorCode());
            Assert.assertEquals("Error descriptions", e.getApiResponse());
        }
    }
}
