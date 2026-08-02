package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestBotSubscriptionUpdated {
    private static final String SUBSCRIPTION_JSON = "{\"user\":{\"id\":12345,\"is_bot\":false,\"first_name\":\"John\"},"
            + "\"invoice_payload\":\"monthly-plan\",\"state\":\"canceled\"}";

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testBotSubscriptionUpdatedDeserialization() throws IOException {
        BotSubscriptionUpdated subscription = mapper.readValue(SUBSCRIPTION_JSON, BotSubscriptionUpdated.class);

        assertNotNull(subscription.getUser());
        assertEquals(12345L, subscription.getUser().getId());
        assertEquals("monthly-plan", subscription.getInvoicePayload());
        assertEquals("canceled", subscription.getState());
    }

    @Test
    public void testBotSubscriptionUpdatedSerialization() throws IOException {
        BotSubscriptionUpdated subscription = BotSubscriptionUpdated.builder()
                .user(User.builder().id(12345L).isBot(false).firstName("John").build())
                .invoicePayload("monthly-plan")
                .state("active")
                .build();

        String json = mapper.writeValueAsString(subscription);

        assertTrue(json.contains("\"invoice_payload\":\"monthly-plan\""), json);
        assertTrue(json.contains("\"state\":\"active\""), json);
    }

    @Test
    public void testUpdateWithSubscriptionDeserialization() throws IOException {
        Update update = mapper.readValue("{\"update_id\":1,\"subscription\":" + SUBSCRIPTION_JSON + "}", Update.class);

        assertTrue(update.hasSubscription());
        assertNotNull(update.getSubscription());
        assertEquals("canceled", update.getSubscription().getState());
        assertEquals(12345L, update.getSubscription().getUser().getId());
    }

    @Test
    public void testUpdateWithoutSubscription() throws IOException {
        Update update = mapper.readValue("{\"update_id\":1}", Update.class);

        assertFalse(update.hasSubscription());
    }
}
