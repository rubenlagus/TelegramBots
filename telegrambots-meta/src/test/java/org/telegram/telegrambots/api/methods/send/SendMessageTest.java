package org.telegram.telegrambots.api.methods.send;

import org.junit.Test;

import static org.junit.Assert.*;

public class SendMessageTest {

    @Test
    public void comparison() throws Exception {
        SendMessage sm1 = new SendMessage().setChatId(1L).setText("Hello World");
        SendMessage sm2 = new SendMessage().setChatId(1L).setText("Hello World");
        SendMessage noMessage = new SendMessage().setChatId(1L);
        SendMessage disabledNotification = new SendMessage().setChatId(1L).setText("Hello World").disableNotification();

        assertTrue(sm1.equals(sm2));
        assertFalse(sm1.equals(noMessage));
        assertFalse(sm1.equals(disabledNotification));

        assertTrue(sm1.hashCode() == sm2.hashCode());
        assertFalse(sm1.hashCode() == noMessage.hashCode());
        assertFalse(sm1.hashCode() == disabledNotification.hashCode());
    }

}