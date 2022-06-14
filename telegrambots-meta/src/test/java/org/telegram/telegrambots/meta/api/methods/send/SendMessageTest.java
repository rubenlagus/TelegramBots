package org.telegram.telegrambots.meta.api.methods.send;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SendMessageTest {

    @Test
    void comparison() {
        SendMessage sm1 = SendMessage
                .builder()
                .chatId(1L)
                .text("Hello World")
                .build();
        SendMessage sm2 = SendMessage
                .builder()
                .chatId("1")
                .text("Hello World")
                .build();
        SendMessage disabledNotification = SendMessage
                .builder()
                .chatId("1")
                .text("Hello World")
                .disableNotification(true)
                .build();

        assertEquals(sm1, sm2);
        assertNotEquals(sm1, disabledNotification);

        assertEquals(sm1.hashCode(), sm2.hashCode());
        assertNotEquals(sm1.hashCode(), disabledNotification.hashCode());
    }

}