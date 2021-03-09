So, you just wanna program your own Telegram bot with TelegramBots? Let's see the fast version.

## Grab the library
First you need ot get the library and add it to your project. There are few possibilities for this:

1. If you use [Maven](https://maven.apache.org/), [Gradle](https://gradle.org/), etc; you should be able to import the dependency directly from [Maven Central Repository](http://mvnrepository.com/artifact/org.telegram/telegrambots). For example:

    * With **Maven**:
    
        ```xml
           <dependency>
              <groupId>org.telegram</groupId>
              <artifactId>telegrambots</artifactId>
              <version>5.0.1</version>
           </dependency>
        ```
    * With **Gradle**:
    
        ```gradle
          implementation 'org.telegram:telegrambots:5.0.1'
        ```
 
2. Don't like **Maven Central Repository**? It can also be taken from [Jitpack](https://jitpack.io/#rubenlagus/TelegramBots).
3. Import the library *.jar* direclty to your project. You can find it [here](https://github.com/rubenlagus/TelegramBots/releases), don't forget to take last version, it usually is a good idea. Depending on the IDE you are using, the process to add a library is different, here is a video that may help with [Intellij](https://www.youtube.com/watch?v=NZaH4tjwMYg) or [Eclipse](https://www.youtube.com/watch?v=VWnfHkBgO1I)


## Build our first bot
Now that we have the library, we can start coding. There are few steps to follow, in this tutorial (for the sake of simplicity), we are going to build a [Long Polling Bot](http://en.wikipedia.org/wiki/Push_technology#Long_polling):

1. **Create your actual bot:**
    The class must extends `TelegramLongPollingBot` and implement necessary methods:

    ```java

    public class MyAmazingBot extends TelegramLongPollingBot {
        @Override
        public void onUpdateReceived(Update update) {
            // TODO
        }

        @Override
        public String getBotUsername() {
            // TODO
            return null;
        }

        @Override
        public String getBotToken() {
            // TODO
            return null;
        }
    }

    ```

    * `getBotUsername()`:  This method must always return your **Bot username**. May look like:


        ```java

            @Override
            public String getBotUsername() {
                return "myamazingbot";
            }

        ```

    * `getBotToken()`: This method must always return your **Bot Token** (If you don't know it, you may want to talk with [@BotFather](https://telegram.me/BotFather)). May look like:

        ```java

            @Override
            public String getBotToken() {
                return "123456789:qwertyuioplkjhgfdsazxcvbnm";
            }

        ```

    * `onUpdateReceived`: This method will be called when an [Update](https://core.telegram.org/bots/api#update) is received by your bot. In this example, this method will just read messages and echo the same text:

        ```java

        @Override
        public void onUpdateReceived(Update update) {
            // We check if the update has a message and the message has text
            if (update.hasMessage() && update.getMessage().hasText()) {
                SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                        .setChatId(update.getMessage().getChatId())
                        .setText(update.getMessage().getText());
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        ```

2. **Instantiate `TelegramBotsApi` and register our new bot:**
    For this part, we need to actually perform 2 steps: _Instantiate Telegram Api_ and _Register our Bot_. In this tutorial, we are going to make it in our `main` method:

    ```java

    public class Main {
        public static void main(String[] args) {

            // TODO Instantiate Telegram Bots API

            // TODO Register our bot
        }
    }

    ```

    * **Instantiate Telegram Bots API**: Easy as well, just create a new instance. Remember that a single instance can handle different bots but each bot can run only once (Telegram doesn't support concurrent calls to `GetUpdates`):

        ```java
      
        public class Main {
            public static void main(String[] args) {
                // You can use your own BotSession implementation if needed.
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

                // TODO Register our bot
            }
        }

        ```

    * **Register our bot**: Now we need to register a new instance of our previously created bot class in the api:

        ```java

        public class Main {
            public static void main(String[] args) {

                try {
                    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                    botsApi.registerBot(new MyAmazingBot());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        ```

3. **Play with your bot:**
    Done, now you just need to run this `main` method and your Bot should start working.
