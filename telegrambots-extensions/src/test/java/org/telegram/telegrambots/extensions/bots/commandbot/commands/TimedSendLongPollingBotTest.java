package org.telegram.telegrambots.extensions.bots.commandbot.commands;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.extensions.bots.timedbot.TimedSendLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.mockito.Mockito.mock;


public class TimedSendLongPollingBotTest
{
    @Test
    public void testMessageProcessing()
    {
        TimedSendLongPollingBot testbot = new TimedSendLongPollingBot()
        {
            @Override
            public String getBotUsername() {
                return null;
            }

            @Override
            public void onUpdateReceived(Update update)
            {

            }
            @Override
            public void sendMessageCallback(Long chatId, Object messageRequest)
            {

            }
        };

        Long chatId = 2L;
        Object messageRequest = mock(Object.class);

        testbot.sendTimed(chatId,messageRequest);
        testbot.finish();
    }
}
