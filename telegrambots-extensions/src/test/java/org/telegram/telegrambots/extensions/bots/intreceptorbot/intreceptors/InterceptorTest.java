package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterceptorTest {

    private Interceptor interceptor;

    @BeforeEach
    void setUp() {
        interceptor = new Interceptor() {};
    }

    private List<Object> savingObjects;

    @Test
    void getSendMessages() {
        List<Object> savingObjects = new ArrayList<>();
        savingObjects.add(new SendMessage());
        savingObjects.add(new SendSticker());
        savingObjects.add(new SendAnimation());
        interceptor.setSentMessages(savingObjects);

        assertEquals(1, interceptor.getSendMessages(SendMessage.class).size());
        assertEquals(1, interceptor.getSendMessages(SendSticker.class).size());

        interceptor.setSentMessages(new ArrayList<>());

        assertDoesNotThrow(() -> interceptor.getSendMessages(SendMessage.class));

        assertThrows(IllegalArgumentException.class, () -> interceptor.getSendMessages(InterceptorTest.class));
    }

    @Test
    void checkFeatureEnable() {
        interceptor.setSentMessages(null);

        assertThrows(FeatureDisable.class, () -> interceptor.getSendMessages(SendMessage.class));
    }
}