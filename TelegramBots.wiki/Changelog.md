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

### <a id="2.4.4"></a>2.4.4 ###
1. Added `cache_time` to ÀnswerCallbackQuery method
2. Added field `forward_from_message_id` to `Message` object
3. Renamed `ReplyKeyboardHide` to `ReplyKeyboardRemove` and its field `hide_keyboard` to `remove_keyboard`
4. Added field `force` and `disable_edit_message` to `SetGameScore`, removed `edit_message` one.
5. Added `channel_post` and `edited_channel_post` to `Update` object.

**[[How to update to version 2.4.4|How-To-Update#2.4.4]]**

### <a id="2.4.4.1"></a>2.4.4.1 ###
1. New `max_connections` in `setWebhook` method.
2. New `allowed_updates` in `setWebhook` and `getUpdates`
3. New `deleteWebhook` method
4. Added new configs to DefaultBotOptions to handle `max_connections` and `allowed_updates`

### <a id="2.4.4.3"></a>2.4.4.3 ###
1. In `BotSession`, renamed `close` to `stop`. `Close` method is maintained for backward compatibility.
2. Support crating webhook with HTTP servers (HTTPS must be managed via external tools)

**[[How to update to version 2.4.4.3|How-To-Update#2.4.4.3]]**

### <a id="2.4.4.4"></a>2.4.4.4 ###
1. EditMessageText, EditMessageCaption and EditMessageReplyMarkup now return a `Serializable` object that can be `Boolean` or `Message`

**[[How to update to version 2.4.4.4|How-To-Update#2.4.4.4]]**