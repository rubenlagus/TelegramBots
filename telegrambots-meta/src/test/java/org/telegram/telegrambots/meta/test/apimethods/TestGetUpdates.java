package org.telegram.telegrambots.meta.test.apimethods;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.test.TelegramBotsHelper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestGetUpdates {

    private GetUpdates getUpdates;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        getUpdates = new GetUpdates();
        getUpdates.setOffset(15);
        getUpdates.setTimeout(50);
        getUpdates.setLimit(100);
    }

    @Test
    void TestGetUpdatesMustBeSerializable() throws Exception {
        String json = mapper.writeValueAsString(getUpdates);
        assertNotNull(json);
        assertEquals("{\"offset\":15,\"limit\":100,\"timeout\":50,\"method\":\"getupdates\"}", json);
    }

    @Test
    void TestGetUpdatesMustDeserializeCorrectResponse() throws Exception {
        ArrayList<Update> result =
                getUpdates.deserializeResponse(TelegramBotsHelper.GetResponseWithoutError());
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void TestGetUpdatesMustThrowAnExceptionForInCorrectResponse() {
        try {
            getUpdates.deserializeResponse(TelegramBotsHelper.GetResponseWithError());
        } catch (TelegramApiRequestException e) {
            assertNotNull(e.getParameters());
            assertEquals(Integer.valueOf(400), e.getErrorCode());
            assertEquals("Error descriptions", e.getApiResponse());
        }
    }
}
