package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayloadStorageTest {

    private final Payload<Integer> testPayload = Payload.of(41);
    private final PayloadStorage storage = new PayloadStorage();

    @Test
    void addPayload() {
        storage.addPayload(Integer.class, testPayload);
        assertEquals(storage.getPayload(Integer.class).getData(), 41);
        assertTrue(storage.getPayload(Integer.class).getData() instanceof Integer);
    }

    @Test
    void clearStorage() {
        storage.addPayload(Integer.class, testPayload);
        storage.clearStorage();
        assertEquals(0, storage.getPayloadSize());
    }
}