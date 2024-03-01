package org.telegram.telegrambots.extensions.bots.commandbot.commands;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BotCommandTest
{

    @Test
    public void testToString()
    {
        // Act
        BotCommand commandidentifier = new TestBotCommand("test","Description");

        // Assert
        assertEquals("<b>/test</b>\nDescription",commandidentifier.toString());
    }

    @Test
    public void testcommandIdentifierStartsWithCOMMAND_INIT_CHARACTER()
    {
        // Arrange
        String commandidentifier = "/YuktaGurnani";

        // Act
        String result =  commandidentifier.substring(1);

        // Assert
        assertEquals("YuktaGurnani",result);
    }
    private static class TestBotCommand extends BotCommand
    {
        public TestBotCommand(String commandIdentifier, String description){
            super(commandIdentifier,description);
        }
        public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
        {

        }
    }
}
