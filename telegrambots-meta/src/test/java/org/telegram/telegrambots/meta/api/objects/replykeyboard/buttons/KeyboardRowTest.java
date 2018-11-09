package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class KeyboardRowTest {

    private static final List<String> BUTTON_NAMES = asList("Carlotta Valdes", "Jimmy Stewart");

    @Test
    public void shouldAddAllButtons() {
        final KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(BUTTON_NAMES);
        assertThat(keyboardRow.size(), is(2));
        assertThat(keyboardRow.get(0).getText(), is("Carlotta Valdes"));
        assertThat(keyboardRow.get(1).getText(), is("Jimmy Stewart"));
    }

    @Test
    public void shouldAddNoButtons() {
        final KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(new ArrayList<String>());
        assertTrue(keyboardRow.isEmpty());
    }

}
