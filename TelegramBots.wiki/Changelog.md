### <a id="5.1.0"></a>5.1.0 ###
1. Update Api version [5.1](https://core.telegram.org/bots/api-changelog#march-9-2021)
2. Bug fixing: #832, #841, #844, #851, #857
3. Update Spring boot version 2.4.3
4. Update Gradle docs
5. Added CommandMessage to extensions
6. Abilities: Inject bot instance to reply update consumer support for multiple reply declarations.

**[[How to update to version 5.1.0|How-To-Update#5.1.0]]**

### <a id="5.0.1"></a>5.0.1 ###
1. Fixing couple of bugs from 5.0.0
2. Bug fixing: #794 
3. Docs updated to reflect usage for version 5.0.0
4. EditMessageText setChatIId(Long) is removed to keep consistency 

### <a id="5.0.0"></a>5.0.0 ###
1. Update Api version [5.0](https://core.telegram.org/bots/api-changelog#november-4-2020)
2. Added Builders for many of the API methods and objects (hopefully all of them unless I missed something)
3. Some setters/getters may have change name. They no longer return a reference to itself, use Builder for that.
4. Simplified methods to set files in methods. Only InputFile is available now (this class contains constructors for all the cases)  
5. Locations now use Double instead of Float to avoid rounding.
6. When using a TelegramApi for webhook usage, a Webhook instance has to be provided in constructor (i.e. DefaultWebhook class)
6. When registering a Webhook Bot, a SetWebhook object must be provided.
7. When using Webhook with Spring, extends class SpringWebhookBot instead of WebhookBot 
8. New Async methods returning CompletableFutures (yes, we still have the existing callback methods)
9. Added new Async methods for missing cases returning CompletableFutures. Like for sendAudio or sendVideo.
10. No more Guice to define custom class
11. Bug fixes: #795

**[[How to update to version 5.0.0|How-To-Update#5.0.0]]**

### <a id="4.9.2"></a>4.9.2 ###
1. Bug fixing: #792, #801, #804, #810, #812, #813, #820 and #814

### <a id="4.9.1"></a>4.9.1 ###
1. Bug fixing: #767, #766, #761, #763, #776, #772, #771, #780

### <a id="4.9"></a>4.9 ###
1. Update Api version [4.9](https://core.telegram.org/bots/api-changelog#june-4-2020)
2. Bug fixing: #731, #749, #752 and #753

### <a id="4.8.1"></a>4.8.1 ###
1. Update Api version [4.8](https://core.telegram.org/bots/api-changelog#april-24-2020)
2. Add stats for Abilities
3. New and updated wiki page
4. Spring-boot support for version 2.2.2 
5. Bug fixing: #745, #716, #629, #749, #730

### <a id="4.7"></a>4.7 ###
1. Update Api version [4.7](https://core.telegram.org/bots/api-changelog#march-30-2020)

### <a id="4.6"></a>4.6 ###
1. Update Api version [4.6](https://core.telegram.org/bots/api-changelog#january-23-2020)

### <a id="4.5"></a>4.5 ###
1. Update Api version [4.5](https://core.telegram.org/bots/api-changelog#december-31-2019)
2. Fixes: #697, #710

### <a id="4.4.0.2"></a>4.4.0.2 ###
1. Use SLF4J
2. Support case-insensitive usernames
3. Add Ability toggles and export default abilities to their own class
4. Add state machine capability to AbilityBot via ReplyFlow
5. Support backup and recovery of db vars
6. Fixes: #602, #641, #652, #691

### <a id="4.4.0.1"></a>4.4.0.1 ###
1. Bug fix when importing the project

### <a id="4.4.0"></a>4.4.0 ###
1. Update Api version [4.4](https://core.telegram.org/bots/api-changelog#july-29-2019)
2. Removed BotLogger, replaced with [log4j2](https://logging.apache.org/log4j/2.x/)
3. Library is now built using [Java11](https://www.oracle.com/technetwork/java/javase/overview/index.html)
4. Updated dependencies to use last versions 
5. Files can be downloaded into a stream. Allowing it to be processed immediately. 
6. A java.io.File can be passed into the methods. The downloaded file is copied into that file instead of a temp file then (does not work with the async methods)

### <a id="4.3.1"></a>4.3.1 ###
1. Fix bug #625
2. Moved ApiResponse to different package, deprecated old one (will be removed in next mayor version)
3. Deprecated InputBotApiObject (It will be removed in next mayor update). And all usages moved to basic BotApiObject type.
4. Updated jackson dependency to avoid security bug

### <a id="4.3"></a>4.3 ###
1. Update to Api version [4.3](https://core.telegram.org/bots/api-changelog#may-31-2019)
2. Fixed: #615, #621

### <a id="4.2"></a>4.2 ###
1. Update to Api version [4.2](https://core.telegram.org/bots/api-changelog#april-14-2019)
2. Fixed: #498, #578

### <a id="4.1.2"></a>4.1.2 ###
1. Removed unsafe dependencies
2. Fix bugs: #535, #524, #563, #562 and #557

### <a id="4.1"></a>4.1 ###
1. Support for Api Version [4.1](https://core.telegram.org/bots/api-changelog#august-27-2018)
2. Fix #507 and #512

### <a id="4.0.1"></a>4.0.1 ###
1. Fix bug #499

### <a id="4.0.0"></a>4.0.0 ###
1. Support for Api Version [4.0](https://core.telegram.org/bots/api-changelog#july-26-2018)
2. Abilities: Internationalization
3. Socks 5 support
4. Improved spring boot start configuration
5. Removed previously deprecated methods
6. Support usage in Java 9 (library is still using java 8)
7. Added chat-session bot module

**[[How to update to version 4.0.0|How-To-Update#4.0.0]]**

### <a id="3.6.1"></a>3.6.1 ###
1. Support for proxy connections
2. New module for Spring
3. Bug fixing

### <a id="3.6"></a>3.6 ###
1. Support for Api Version [3.6](https://core.telegram.org/bots/api-changelog#february-13-2018)
2. Bug fixing and other improvements

### <a id="3.5"></a>3.5 ###
1. Support for Api Version [3.5](https://core.telegram.org/bots/api-changelog#november-17-2017)
2. Bug fixing: #168, #329 and #335
3. Added processInvalidCommandUpdate (#337)
4. AbilitiyBot update and tutorial (#324)
5. Add DefaultBotCommand with message ID (#330)
6. New wiki content (#326 and #327)

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