package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyboardRowTest {

    private static final List<String> BUTTON_NAMES = asList("Carlotta Valdes", "Jimmy Stewart");

    @Test
    void shouldAddAllButtons() {
        final KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(BUTTON_NAMES);
        assertEquals(2, keyboardRow.size());
        assertEquals("Carlotta Valdes", keyboardRow.get(0).getText());
        assertEquals("Jimmy Stewart", keyboardRow.get(1).getText());
    }

    @Test
    void shouldAddNoButtons() {
        final KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(new ArrayList<String>());
        assertTrue(keyboardRow.isEmpty());
    }

}
