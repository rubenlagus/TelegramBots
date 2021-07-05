package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayloadTest {

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class, () -> Payload.of(null));
        assertDoesNotThrow(() -> Payload.of(41));
    }
}