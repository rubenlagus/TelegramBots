# CommandBot
This section of the tutorial will present a barebone example on creating your first CommandBot! It is highly recommended to write your very first bot via the [[Getting Started|Getting-Started]]. That will give you a sense of how the basic API allows you to handle commands and features.

## Dependencies
As with any Java project, you will need to set your dependencies.

* **Maven**
```xml
   <dependency>
      <groupId>org.telegram</groupId>
      <artifactId>telegrambotsextensions</artifactId>
      <version>6.7.0</version>
   </dependency>
```
* **Gradle**
```gradle
  implementation 'org.telegram:telegrambotsextensions:6.7.0'
```
* [JitPack](https://jitpack.io/#rubenlagus/TelegramBots)
    
* [Plain Imports/Jars](https://github.com/rubenlagus/TelegramBots/releases)

## Bot Declaration
To use the command module, you will need to extend `TelegramLongPollingCommandBot` or `TelegramWebhookCommandBot`.
```java
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;

public class SumLongPollingCommandBot extends TelegramLongPollingCommandBot {
  ...
}
```

## Bot Implementation
Bot token are passed via the constructor and don't require an override.
```java
public SumLongPollingCommandBot(String botToken) {
    super(new DefaultBotOptions(), false, botToken, new DefaultUserActivityHandler());
}
```

That's it to have a valid, compilable and ready to be deployed bot. However, your bot doesn't have a single command to use. Let's declare one!

## Sum Command
To add a feature to your bot, you need to extend `BotCommand`. Let's create a command to sum two numbers in two different messages.

```java
public class SumCommand extends BotCommand {
    public SumCommand() {
        super("/sum", "Sum two numbers");
    }
}
```

So, we need something to save a state of the command between messages.

```java
public class SumCommand extends BotCommand {
    
    // Constructor
    
    private static class State {
        // Current stage of the command (first or second number)
        private final int stage;
        // Saved first number
        private final Double firstNumber;
    
        public State(int stage, Double firstNumber) {
            this.stage = stage;
            this.firstNumber = firstNumber;
        }
    }
}
```

Finally, let's describe command logic.

```java
public class SumCommand extends BotCommand {
    
    // Constructor

    @Override
    public CommandState<?> processMessage(AbsSender absSender, Message message, String[] arguments, CommandState<?> commandState) {
        CommandState<State> currentState = (CommandState<State>) commandState; // Careful with this unchecked cast!
        if (currentState.getState() == null) {
            sendMessage(absSender, message, "Enter first number");
            return new CommandState<>(getCommandIdentifier(), new State(1, null));
        } else if (currentState.getState().stage == 1) {
            double i;
            try {
                i = Double.parseDouble(message.getText());
            } catch (Exception e) {
                sendMessage(absSender, message, "Incorrect format, enter again");
                return commandState;
            }
            sendMessage(absSender, message, "Enter second number");
            return new CommandState<>(getCommandIdentifier(), new State(2, i));
        } else if (currentState.getState().stage == 2) {
            double i;
            try {
                i = Double.parseDouble(message.getText());
            } catch (Exception e) {
                sendMessage(absSender, message, "Incorrect format, enter again");
                return commandState;
            }
            double result = currentState.getState().firstNumber + i;
            sendMessage(absSender, message, "Result: " + result);
            return new CommandState<>(getCommandIdentifier(), null);
        } else {
            return null;
        }
    }

    private void sendMessage(AbsSender absSender, Message message, String text) {
        SendMessage sendMessage = SendMessage.builder().chatId(message.getChatId()).text(text).build();
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Nested State class
}
```

## Running Your Bot
Running the bot is just like running the regular Telegram bots. Create a Java class similar to the one below.
```java
public class Application {
    public static void main(String[] args) {
        try {
          // Create the TelegramBotsApi object to register your bots
          TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
          
          // Create your bot
          SumLongPollingCommandBot bot = new SumLongPollingCommandBot(BOT_TOKEN);
          
          // Register your command in bot
          bot.register(new SumCommand());

          // Register your newly created CommandBot
          telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

## Testing Your Bot
Go ahead and "/sum" to your bot. It should respond back with "Enter first number".

## Conclusion
Congratulation on creating your first CommandBot. What's next? So far we've only considered the case of commands, but what about inline replies? CommandBots can also handle that!
