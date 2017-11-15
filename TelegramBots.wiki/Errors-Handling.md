* [Terminated by other long poll or webhook](#terminted_by_other)
* ["No implementation for org.telegram.telegrambots.generics.BotSession was bound"](#no_implementation_was_bound)

## <a id="terminted_by_other"></a>Terminated by other long poll or webhook ##

It means that you have already a running instance of your bot. To solve it, close all running ones and then you can start a new instance.

## <a id="no_implementation_was_bound"></a>No implementation for org.telegram.telegrambots.generics.BotSession was bound ##
Please follow the steps as explained [here](https://github.com/rubenlagus/TelegramBots/wiki/How-To-Update#to-version-243) in "How To Update"
  > At the beginning of your program (before creating your TelegramBotsApi instance, add the following line:
    ```
    ApiContextInitializer.init();
    ```
