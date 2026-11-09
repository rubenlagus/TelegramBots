## To version 10.4.0
1. The `Message`-returning explicit send overloads for `SendRichMessage`, `SendAnimation`, `SendAudio`, `SendDocument`, `SendLivePhoto`, `SendPhoto`, `SendSticker`, `SendVideo`, `SendVideoNote`, and `SendVoice` are deprecated but remain available for normal sends. The generic `execute(BotApiMethod)` API and normal `SendMessage`, `SendContact`, `SendLocation`, and `SendVenue` calls are not deprecated. Built-in clients reject callback-only replies through legacy calls before making a request.
2. Use `executeWithResult` or `executeWithResultAsync` to handle either result. For a callback-only ephemeral reply, provide `callbackQueryId` without `receiverUserId`; `chatId` may be omitted and `businessConnectionId` must be omitted. Telegram ignores the chat ID and uses only content, `protectContent`, and `replyMarkup` in this mode. The bot must still answer the callback query.

   ```java
   SendMessage request = SendMessage.builder()
           .text("Processing")
           .ephemeralMessageParameters(EphemeralMessageParameters.builder().callbackQueryId(queryId).build())
           .build();
   SendMessageResult result = client.executeWithResult(request);
   if (result.isCallbackSuccess()) {
       // Callback-only reply accepted; no Message was returned.
   } else {
       Message sent = result.getMessage();
   }
   ```

3. Custom `TelegramClient` implementations retain their existing contract, but must override both result methods to support callback-only replies; the defaults throw `UnsupportedOperationException`. Use the generic API for normal `SendMessage`, `SendContact`, `SendLocation`, and `SendVenue` requests; no request-specific overloads are added for these methods because they could change dispatch in existing custom clients. Deprecated 10.3 per-field ephemeral setters and builders remain supported.
4. `AreaPosition` is available for wallet cards and story areas. Existing `StoryAreaPosition` constructors, getters, setters, and builders remain usable; `StoryArea.getAreaPosition`, `setAreaPosition`, and the `areaPosition` builder method expose the new type.
5. In production, widgets currently can only be sent to private chats, are automatically protected against forwarding, and cannot be sent through inline queries. Widget messages cannot be copied. These server restrictions are not enforced locally.

## To version 10.1.0
1. No changes to the `TelegramClient` interface are required. The four new methods (`sendRichMessage`, `sendRichMessageDraft`, `answerChatJoinRequestQuery`, `sendChatJoinRequestWebApp`) are pure JSON methods and are available via the existing generic `execute(BotApiMethod)` overload. Custom `TelegramClient` implementations do not need to be updated.
2. `EditMessageText` now accepts either `text` or `richMessage` (exactly one is required). Previously `text` was mandatory; passing neither will now throw a `TelegramApiValidationException`.
3. New classes are available for rich message support: `RichText`, `RichBlock`, `RichMessage`, `InputRichMessage`, `InputRichMessageContent` — see the Bot API 10.1 changelog for details.
4. New `Link` class and `InputMediaLink` are available as `InputPollOptionMedia` for polls with HTTP links.

## To version 10.0.0
1. This version removes most of the deprecated fields and methods. Specifically:
   - Deprecated constructors in the `User` class have been removed. Use the `User.builder()` pattern instead.
   - Legacy reply fields `replyToMessageId` and `allowSendingWithoutReply` have been deprecated or removed in many methods. Use `ReplyParameters` instead.
2. `TelegramClient` interface has new methods for `SendLivePhoto`. If you have a custom implementation, you must implement `execute(SendLivePhoto)` and `executeAsync(SendLivePhoto)`.
3. `SendPoll` validation now requires at least 1 option (previously 2), reflecting the new API capabilities.
4. New media support: `Poll` now supports `question_entities` and `options` with `text_entities`.
5. Support for Guest Mode: `AbilityUtils` and `Flag` in `telegrambots-abilities` now support `GuestMessage`.

## To version 7.6.0
1. If you are using a custom TelegramClient, you'll need to implement the new methods for `SendPaidMedia`

## To version 7.3.0
1. Class `org.telegram.telegrambots.meta.api.objects.Chat` has been split (as per API guidelines) into `org.telegram.telegrambots.meta.api.objects.chat.Chat` and `org.telegram.telegrambots.meta.api.objects.chat.ChatFullInfo`, old class is still available but unused.

## To version 7.2.1
1. Instead of using `TelegramOkHttpClientFactory.ProxyOkHttpClientCreator` use either `TelegramOkHttpClientFactory.HttpProxyOkHttpClientCreator` or `TelegramOkHttpClientFactory.SocksProxyOkHttpClientCreator`

## To version 7.2.0
1. When using `CreateNewStickerSet`, instead of providing a common format using `setStickerFormat`, provide each `InputSticker` with its own format. Methods `setStickerFormat` and `getStickerFormat` are kept only for backward compatibility and work only when all stickers have the same format.
2. `SetStickerSetThumbnail` and `InputSticker` contain a `format` field mandatory.
3. `ChatMemberUpdated` and `MemberStatus` have been moved to package `org.telegram.telegrambots.meta.api.objects.chatmember`. Old classes are maintained for backward compatibility.
4. `UsersShared` contains an array of `users`. Old `getUserIds` is maintained for backward compatibility.

## To version 7.0.0
1. This is a huge update, so the proper documentation is a WIP [here](How-To-Update-7.md)

## To version 6.9.7.0
1. `CallbackQuery` method `getMessage` now return an instance of `MaybeInaccessibleMessage`. Check type to identify if it is a `Message` or a `InaccessibleMessage`.
2. `Message` method `getPinnedMessage` now return an instance of `MaybeInaccessibleMessage`. Check type to identify if it is a `Message` or a `InaccessibleMessage`.

## To version 6.8.0
1. Api methods with thumbnails have changed the field, use getThumbnail()/setThumbnail() instead of getThumb()/setThumb()
2. In `AddStickerToSet`,`CreateNewStickerSet`,`UploadStickerFile`, etc., use field `sticker` instead of the deprecated fields.
3. `ChatMember` has more details permissions, use those instead of the legacy general ones.
4. All classes with mandatory fields will lose the default no-arg constructor in the future.
5. In `AnswerInlineQuery`, start using the `button` field instead of deprecated parameters.

## To version 6.1.0
1. As per API guidelines, FileSize can now have 64 bits size, hence they are now using Long datatype instead of Integer.
2. Methods accept chatId as Long or String.

## To version 5.3.0
1. As per API guidelines, ChatMember method has been divided in different classed.
   Where used in your code, replace old import with new one
   (GetChatAdministrators.java, GetChatMember.java, ChatMemberUpdated.java):

   `import org.telegram.telegrambots.meta.api.objects.ChatMember;`

   to

   `import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;`
2. ChatMember is an interface now, you'll need to cast it to the corresponding implementation when using it.
3. `GetChatMembersCount` renamed to `GetChatMemberCount`, old version will still work until next major version.
4. `KickChatMember` renamed to `BanChatMember`, old version will still work until next major version.