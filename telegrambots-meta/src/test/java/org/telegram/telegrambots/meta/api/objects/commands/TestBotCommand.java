package org.telegram.telegrambots.meta.api.objects.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.2
 */
public class TestBotCommand {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testIsEphemeralDeserialization() throws IOException {
        BotCommand command = mapper.readValue(
                "{\"command\":\"secret\",\"description\":\"A secret command\",\"is_ephemeral\":true}", BotCommand.class);

        assertEquals("secret", command.getCommand());
        assertTrue(command.getIsEphemeral());
    }

    @Test
    public void testIsEphemeralSerialization() throws IOException {
        BotCommand command = BotCommand.builder()
                .command("secret")
                .description("A secret command")
                .isEphemeral(true)
                .build();

        String json = mapper.writeValueAsString(command);

        assertTrue(json.contains("\"is_ephemeral\":true"), json);
        assertDoesNotThrow(command::validate);
    }

    @Test
    public void testIsEphemeralOmittedWhenNotSet() throws IOException {
        BotCommand command = BotCommand.builder()
                .command("help")
                .description("Show help")
                .build();

        assertNull(command.getIsEphemeral());
        assertFalse(mapper.writeValueAsString(command).contains("is_ephemeral"));
    }
}
