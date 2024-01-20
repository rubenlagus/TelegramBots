### <a id="6.9.7.0"></a>To version 6.9.7.0 ###
1. `CallbackQuery` method `getMessage` now return an instance of `MaybeInaccessibleMessage`. Check type to identify if it is a `Message` or a `InaccessibleMessage`.
2. `Message` method `getPinnedMessage` now return an instance of `MaybeInaccessibleMessage`. Check type to identify if it is a `Message` or a `InaccessibleMessage`.

### <a id="6.8.0"></a>To version 6.8.0 ###
1. Api methods with thumbnails have changed the field, use getThumbnail()/setThumbnail() instead of getThumb()/setThumb() 
2. In `AddStickerToSet`/`CreateNewStickerSet`/`UploadStickerFile`/etc, use field `sticker` instead of the deprecated fields.
3. `ChatMember` has more details permissions, use those instead of the legacy general ones.
4. All classes with mandatory fields will lose the default no-arg constructor in the future.
5. In `AnswerInlineQuery`, start using the `button` field instead of deprecated parameters.

### <a id="6.1.0"></a>To version 6.1.0 ###
1. As per API guidelines, FileSize can now have 64 bits size, hence they are now using Long datatype instead of Integer.
2. Methods accept chatId as Long or String.

### <a id="5.3.0"></a>To version 5.3.0 ###
1. As per API guidelines, ChatMember method has been divided in different classed. 
   Where used in your code, replace old import with new one 
   (GetChatAdministrators.java, GetChatMember.java, ChatMemberUpdated.java):
   
   `import org.telegram.telegrambots.meta.api.objects.ChatMember;`
   
   to
   
   `import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;`
2. ChatMember is an interface now, you'll need to cast it to the corresponding implementation when using it.
3. `GetChatMembersCount` renamed to `GetChatMemberCount`, old version will still work until next major version.
4. `KickChatMember` renamed to `BanChatMember`, old version will still work until next major version.
   

### <a id="5.1.0"></a>To version 5.1.0 ###
1. All users IDs fields are now Long type as per API guidelines.

### <a id="5.0.0"></a>To version 5.0.0 ###
1. ApiContextInitializer.init(); has been removed and is not required anymore, instead:
    ```java
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
   
    // When using webhook, create your own version of DefaultWebhook with all your parameters set.
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, defaultWebhookInstance);
    ```
2. For location related class, change from Float to Double type, i.e:
    ```java
       Double latitude = location.getLatitude()
    ```
3. Instead of chain set method, use builder pattern:
    ```java
       // Before
       new SendMessage()
                       .setChatId("@test")
                       .setText("Hithere")
                       .setReplyToMessageId(12)
                       .setParseMode(ParseMode.HTML)
                       .setReplyMarkup(new ForceReplyKeyboard())
       // After
       SendMessage
                       .builder()
                       .chatId("@test")
                       .text("Hithere")
                       .replyToMessageId(12)
                       .parseMode(ParseMode.HTML)
                       .replyMarkup(new ForceReplyKeyboard())
                       .build();
    ```
4. Method doesn't accept chatId as Long any more, only as a String. Use Long.toString(...) when needed I.e:
    ```java
       Long chatIdLong = message.getChatId();
       SendMessage
                  .builder()
                  .chatId(Long.toString(chatIdLong))
                  .text("Hithere")
                  .build();
    ```
5. When registering a Webhook bot, provide the SetWebhook method object:
    ```java
       TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, defaultWebhookInstance);
       telegramApi.registerBot(myWebhookBot, mySetWebhook);
    ```
6. When using Spring with a webhook bot, make your bot inherit form SpringWebhookBot instead of WebhookBot and provide your SetWebhook method in the constructor:
    ```java
       // Extend correct class
       public class TestSpringWebhookBot extends SpringWebhookBot {
       
               public TestSpringWebhookBot(SetWebhook setWebhook) {
                   super(setWebhook);
               }
       
               public TestSpringWebhookBot(DefaultBotOptions options, SetWebhook setWebhook) {
                   super(options, setWebhook);
               }
       
               @Override
               public String getBotUsername() {
                   return null;
               }
       
               @Override
               public String getBotToken() {
                   return null;
               }
       
               @Override
               public BotApiMethod onWebhookUpdateReceived(Update update) {
                   return null;
               }
       
               @Override
               public String getBotPath() {
                   return null;
               }
           }
   
       // Create your SetWebhook method
       @Bean
       public SetWebhook setWebhookInstance() {
           return SetWebhook.builder()....build();
       }
   
       // Create it as
       @Bean
       public TestSpringWebhookBot testSpringWebhookBot(SetWebhook setWebhookInstance) {
           return new TestSpringWebhookBot(setWebhookInstance);
       }
    ```
7. Use InputFile to set files to upload instead of different setters, i.e:
    ```java
       // With a file
       SendDocument
           .builder()
           .chatId("123456")
           .document(new InputFile(new File("Filename.pdf")))  
           .build()  
       // With a Stream
       SendDocument
           .builder()
           .chatId("123456")
           .document(new InputFile("FileName", new FileInputStream("Filename.pdf")))  
           .build()
    ```


### <a id="4.4.0.2"></a>To version 4.4.0.2 ###
1. Logging framework has been replaced by slf4j, so now you'll need to manage your own implementation.

### <a id="4.0.0"></a>To version 4.0.0 ###
1. Replace removed method from AbsSender with `execute` requests.
2. Everything under "Telegrambots-meta" has been moved to package `org.telegram.telegrambots.meta`.
3. `close` method has been removed from `BotSession`, use `stop` instead.
4. All methods that are intended to upload files are using now `InputMedia` and `InputFile`.

### <a id="2.4.3"></a>To version 2.4.3 ###
1. Replace `BotOptions` by `DefaultBotOptions`.
2. At the beginning of your program (before creating your `TelegramBotsApi` or `Bot` instance, add the following line:
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

### <a id="3.0"></a>To version 3.0 ###
1. In `Message` object, field `new_chat_member` was replaced by `new_chat_members` that is now an array of users.

### <a id="3.0.2"></a>To version 3.0.2 ###
1. If you were using `TelegramLongPollingCommandBot`, add the new [extensions dependency](https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-extensions) to your maven and fix import statements in your project.
2. If you were using `TelegramLongPollingCommandBot`, make sure you start using constructors with username and prevent overriding `getUsername` method.


### <a id="3.2"></a>To version 3.2 ###
1. Replace usage of all deprecated methods from AbsSender with methods `execute` or `executeAsync`.
2. If you are extending AbsSender class, implement new added methods.