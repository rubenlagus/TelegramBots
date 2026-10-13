package org.telegram.telegrambots.meta.api.objects.ephemeral;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestEphemeralMessageParameters {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testSerializeAllFields() throws IOException {
        EphemeralMessageParameters parameters = EphemeralMessageParameters.builder()
                .receiverUserId(12345L)
                .callbackQueryId("query-id")
                .replaceCallbackQueryMessage(true)
                .build();

        String json = mapper.writeValueAsString(parameters);

        assertTrue(json.contains("\"receiver_user_id\":12345"), json);
        assertTrue(json.contains("\"callback_query_id\":\"query-id\""), json);
        assertTrue(json.contains("\"replace_callback_query_message\":true"), json);
    }

    @Test
    public void testOptionalFieldsAreOmitted() throws IOException {
        EphemeralMessageParameters parameters = EphemeralMessageParameters.builder()
                .receiverUserId(12345L)
                .build();

        String json = mapper.writeValueAsString(parameters);

        assertFalse(json.contains("callback_query_id"), json);
        assertFalse(json.contains("replace_callback_query_message"), json);
    }

    @Test
    public void testDeserialize() throws IOException {
        String json = "{\"receiver_user_id\":777,\"callback_query_id\":\"q\",\"replace_callback_query_message\":true}";

        EphemeralMessageParameters parameters = mapper.readValue(json, EphemeralMessageParameters.class);

        assertEquals(777L, parameters.getReceiverUserId());
        assertEquals("q", parameters.getCallbackQueryId());
        assertTrue(parameters.getReplaceCallbackQueryMessage());
    }

    /**
     * The deprecation bridge in the send methods fills this object field-by-field in caller order.
     * A no-arg instance must therefore be constructible and mutable.
     */
    @Test
    public void testNoArgConstructionAllowsOutOfOrderPopulation() {
        EphemeralMessageParameters parameters = new EphemeralMessageParameters();

        parameters.setCallbackQueryId("q");
        parameters.setReceiverUserId(42L);

        assertEquals(42L, parameters.getReceiverUserId());
        assertEquals("q", parameters.getCallbackQueryId());
        assertDoesNotThrow(parameters::validate);
    }

    @Test
    public void testValidateRejectsMissingReceiverUserId() {
        EphemeralMessageParameters parameters = new EphemeralMessageParameters();
        parameters.setCallbackQueryId("q");

        assertThrows(TelegramApiValidationException.class, parameters::validate);
    }
}
