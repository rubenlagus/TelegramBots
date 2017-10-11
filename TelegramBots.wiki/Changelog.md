### <a id="3.4"></a>3.4 ###
1. Support for Api Version [3.4](https://core.telegram.org/bots/api-changelog#october-11-2017)
2. Use regular expressions to split parameters in `TelegramLongPollingCommandBot` (#309)
3. Option to handle bunch of updates at a time via `onUpdatesReceived` in `TelegramLongPollingBot` (#284)
4. Fix characters encoding (#275)
 

### <a id="3.3"></a>3.3 ###
1. Support for Api Version [3.3](https://core.telegram.org/bots/api-changelog#august-23-2017)


### <a id="3.2"></a>3.2 ###
1. Support for Api Version [3.2](https://core.telegram.org/bots/api-changelog#july-21-2017)
2. Deprecated all redundant methods in AbsSender, will be removed in next major release
3. New Abstract methods `addStickerToSet`, `createNewStickerSet` and `uploadStickerFile` in AbsSender.
4. Abilities module
5. Removed deprecated methods from previous versions
6. Bug fixing: #257, #270
7. Simplify code from DefaultAbsSender: #272

**[[How to update to version 3.2|How-To-Update#3.2]]**

### <a id="3.1.2"></a>3.1.2 ###
1. Fix bug #266

### <a id="3.1.1"></a>3.1.1 ###
1. Fix bug #264

### <a id="3.1.0"></a>3.1.0 ###
1. Support for Api Version [3.1](https://core.telegram.org/bots/api-changelog#june-30-2017)
2. Simplified `DefaultAbsSender`
3. Added new abstract method `setChatPhoto` to AbsSender.
4. Added new method `execute` and `executeAsync` that can be used to send any api method that extends `BotApiMethod` class.
5. Added new constructors to `GetChat`, `GetChatAdministrators`, `GetChatMember`, `GetChatMemberCount`, `KickChatMember`, `LeaveChat` and `UnbanChatMember` with mandatory fields as parameters.

**[[How to update to version 3.1.0|How-To-Update#3.1.0]]**

### <a id="3.0.2"></a>3.0.2 ###
1. Bug Fixing: #250
2. Added new module `telegrambots-extensions` that should contains any extensions of the API such as CommandBot or TimedBot.
3. `TelegramLongPollingCommandBot` receives now the bot username as constructor parameters, all deprecated constructors will be removed in next major release.
4. `getUsername` method from `TelegramLongPollingCommandBot` can be considered `final` and will be so in next major release.

**[[How to update to version 3.0.2|How-To-Update#3.0.2]]**

### <a id="3.0.1"></a>3.0.1 ###
1. Added `getLevel` to `BotLogger` class.
2. Fix wrong URL when setting webhook
3. Bug Fixing: #244, #233

### <a id="3.0"></a>3.0 ###
1. New field `gif_duration` and `mpeg4_duration` in `InlineQueryResultGif` and `InlineQueryResultMpeg4Gif`.
2. Field `new_chat_member` was replaced by `new_chat_members` in `Message` object.
3. Some methods gets now constructors with mandatory parameters to simplify their creation (including preconditions).
4. New `deleteMessage` method.
5. New field `language_code` in `User` object.
6. New Payments API methods
7. New Video Messages API methods

**[[How to update to version 3.0|How-To-Update#3.0]]**

### <a id="2.4.4.5"></a>2.4.4.5 ###
1. New validations for AnswerInlineQuery according to Telegram Bots API changes.
2. Added Maven-enforcer-plugin to Maven pom.
3. Added new How to send photos by file_id to FAQ.
4. Added reference to new gitbook about this library.
5. Added custom ExponentialBackOff waiting time when having network problems in long-polling mode. (Custom implementation is allowed via BotOptions)
6. Bug fixing: #184, #183

### <a id="2.4.4.4"></a>2.4.4.4 ###
1. EditMessageText, EditMessageCaption and EditMessageReplyMarkup now return a `Serializable` object that can be `Boolean` or `Message`

**[[How to update to version 2.4.4.4|How-To-Update#2.4.4.4]]**

### <a id="2.4.4.3"></a>2.4.4.3 ###
1. In `BotSession`, renamed `close` to `stop`. `Close` method is maintained for backward compatibility.
2. Support crating webhook with HTTP servers (HTTPS must be managed via external tools)

**[[How to update to version 2.4.4.3|How-To-Update#2.4.4.3]]**

### <a id="2.4.4.1"></a>2.4.4.1 ###
1. New `max_connections` in `setWebhook` method.
2. New `allowed_updates` in `setWebhook` and `getUpdates`
3. New `deleteWebhook` method
4. Added new configs to DefaultBotOptions to handle `max_connections` and `allowed_updates`

### <a id="2.4.4"></a>2.4.4 ###
1. Added `cache_time` to ÀnswerCallbackQuery method
2. Added field `forward_from_message_id` to `Message` object
3. Renamed `ReplyKeyboardHide` to `ReplyKeyboardRemove` and its field `hide_keyboard` to `remove_keyboard`
4. Added field `force` and `disable_edit_message` to `SetGameScore`, removed `edit_message` one.
5. Added `channel_post` and `edited_channel_post` to `Update` object.

**[[How to update to version 2.4.4|How-To-Update#2.4.4]]**

### <a id="2.4.3"></a>2.4.3 ###
1. Split library in two modules to allow custom implementations.
2. Use [Guice](https://github.com/google/guice) for dependency injection.
3. Use [Jackson](https://github.com/FasterXML/jackson) for json (de)serialization.
4. Added extra validation to methods before performing requests.
5. BotOptions has been renamed ot DefaultBotOptions. It allows now to set number of threads for async methods execution and the complete `RequestConfig` for customization purpose.
6. Added convenient method for `setChatId` using just a `Long` value instead of an String.
7. In `SentCallback` method `onError` changed second parameter to `TelegramApiRequestException` and `onResult` now receives the deserialized answer (of type `T`) instead of a `JSONObject` as second parameter
8. Moved to MIT license

**[[How to update to version 2.4.3|How-To-Update#2.4.3]]**