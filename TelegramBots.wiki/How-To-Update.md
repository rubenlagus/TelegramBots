### <a id="2.4.3"></a>To version 2.4.3 ###
1. Replace `BotOptions` by `DefaultBotOptions`.
2. At the beginning of your program (before creating your `TelegramBotsApi` instance, add the following line:
    ```java
    ApiContextInitializer.init();
    ```
3. In `SentCallback`, update parameter types of `onResult` and `onError`. Inside those two method, you don't need to deserialize anything now, it comes already done.
3. **Deprecated** (will be removed in next version):
    * `org.telegram.telegrambots.bots.BotOptions`. Use `org.telegram.telegrambots.bots.DefaultBotOptions` instead.
    * `getPersonal` from `AnswerInlineQuery`. Use `isPersonal` instead.
    * `FILEBASEURL` from `File`. Use `getFileUrl` instead.