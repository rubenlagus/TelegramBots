package org.telegram.telegrambots.meta.api.methods;


import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author Roman Bondar
 */

public class TestPartialBotApiMethod {

    @Test
    public void deserializeUpdateWithWriteAccessAllowedNotThrowApiException() throws IOException {

        String answer = new String(Files.readAllBytes(Paths.get("src/test/resources/fixtures/update-with-write-access-allowed-for-webapp.json")));

        PartialBotApiMethod<Serializable> method = new PartialBotApiMethod<>() {
            @Override
            public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
                return deserializeResponseArray(answer, Update.class);
            }

            @Override
            public String getMethod() {
                return "method";
            }
        };

        assertDoesNotThrow(() -> method.deserializeResponse(answer),
                "deserializeResponse should not throw if write access allowed passed into it ");

    }
}
