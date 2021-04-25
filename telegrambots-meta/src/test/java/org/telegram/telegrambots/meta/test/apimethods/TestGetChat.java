package org.telegram.telegrambots.meta.test.apimethods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.test.TelegramBotsHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestGetChat {

    private GetChat getChat;

    @BeforeEach
    void setUp() {
        getChat = new GetChat();
    }

    @Test
    void TestChatWithSlowModeDelay() throws TelegramApiRequestException {
        Chat result=getChat.deserializeResponse(TelegramBotsHelper.GetChatWithSlowModeDelay());
        assertEquals(10,result.getSlowModeDelay());
    }

    @Test
    void TestChatWithoutSlowModeDelay() throws TelegramApiRequestException {
        Chat result=getChat.deserializeResponse(TelegramBotsHelper.GetChatWithoutSlowModeDelay());
        assertEquals(null,result.getSlowModeDelay());
    }
}
