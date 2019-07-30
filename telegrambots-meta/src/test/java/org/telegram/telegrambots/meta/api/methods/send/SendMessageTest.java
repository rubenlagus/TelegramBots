package org.telegram.telegrambots.meta.api.methods.send;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SendMessageTest {

    @Test
    void comparison() {
        SendMessage sm1 = new SendMessage().setChatId(1L).setText("Hello World");
        SendMessage sm2 = new SendMessage().setChatId(1L).setText("Hello World");
        SendMessage noMessage = new SendMessage().setChatId(1L);
        SendMessage disabledNotification = new SendMessage().setChatId(1L).setText("Hello World").disableNotification();

        assertEquals(sm1, sm2);
        assertNotEquals(sm1, noMessage);
        assertNotEquals(sm1, disabledNotification);

        assertEquals(sm1.hashCode(), sm2.hashCode());
        assertNotEquals(sm1.hashCode(), noMessage.hashCode());
        assertNotEquals(sm1.hashCode(), disabledNotification.hashCode());
    }

}