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
    
### <a id="2.4.4"></a>To version 2.4.4 ###
1. Replace `ReplyKeyboardHide` by `ReplyKeyboardRemove` and its field `hideKeyboard` by `removeKeyboard` (remember getter and setters)
2. Replace usage of `edit_message` by `disable_edit_message` (see [this post](https://telegram.me/BotNews/22))
3. Removed deprecated stuff from version 2.4.3

### <a id="2.4.4.3"></a>To version 2.4.4.3 ###
1. Replace `BotSession.close()` by `BotSession.stop()`.

### <a id="2.4.4.4"></a>To version 2.4.4.4 ###
1. All calls to `editMessageText`, `editMessageCaption` or `editMessageReplyMarkup` in `AbsSender` return value is changed to `Serializable`
2. In `editMessageTextAsync`, `editMessageCaptionAsync` or `editMessageReplyMarkupAsync` in `AbsSender`, second parameter should become `SentCallback<Serializable>` due to new return type. 