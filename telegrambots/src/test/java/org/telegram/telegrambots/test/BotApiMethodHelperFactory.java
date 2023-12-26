package org.telegram.telegrambots.test;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.meta.api.methods.games.SetGameScore;
import org.telegram.telegrambots.meta.api.methods.groupadministration.BanChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.meta.api.methods.groupadministration.LeaveChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendGame;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVenue;
import org.telegram.telegrambots.meta.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.games.GameHighScore;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public final class BotApiMethodHelperFactory {
    private BotApiMethodHelperFactory() {
    }

    public static BotApiMethod<Message> getSendMessage() {
        return SendMessage
                .builder()
                .chatId("@test")
                .text("Hithere")
                .replyToMessageId(12)
                .parseMode(ParseMode.HTML)
                .replyMarkup(ForceReplyKeyboard.builder().forceReply(true).build())
                .build();
    }

    public static BotApiMethod<Boolean> getAnswerCallbackQuery() {
        return AnswerCallbackQuery
                .builder()
                .callbackQueryId("id")
                .text("text")
                .showAlert(true)
                .build();
    }

    public static BotApiMethod<Boolean> getAnswerInlineQuery() {
        return AnswerInlineQuery
                .builder()
                .inlineQueryId("id")
                .isPersonal(true)
                .results(Arrays.asList(getInlineQueryResultArticle(), getInlineQueryResultPhoto()))
                .cacheTime(100)
                .nextOffset("3")
                .switchPmParameter("PmParameter")
                .switchPmText("pmText")
                .build();
    }

    public static BotApiMethod<Serializable> getEditMessageCaption() {
        return EditMessageCaption
                .builder()
                .chatId("ChatId")
                .messageId(1)
                .caption("Caption")
                .replyMarkup(getInlineKeyboardMarkup())
                .build();
    }


    public static BotApiMethod<Serializable> getEditMessageText() {
        return EditMessageText
                .builder()
                .chatId("ChatId")
                .messageId(1)
                .text("Text")
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(getInlineKeyboardMarkup())
                .build();
    }

    public static BotApiMethod<Serializable> getEditMessageReplyMarkup() {
        return EditMessageReplyMarkup
                .builder()
                .inlineMessageId("12345")
                .replyMarkup(getInlineKeyboardMarkup())
                .build();
    }

    public static BotApiMethod<Message> getForwardMessage() {
        return ForwardMessage
                .builder()
                .fromChatId("From")
                .chatId("To")
                .messageId(15)
                .disableNotification(true)
                .build();
    }

    public static BotApiMethod<Chat> getGetChat() {
        return GetChat
                .builder()
                .chatId("12345")
                .build();
    }

    public static BotApiMethod<ArrayList<ChatMember>> getChatAdministrators() {
        return GetChatAdministrators
                .builder()
                .chatId("12345")
                .build();
    }

    public static BotApiMethod<ChatMember> getChatMember() {
        return GetChatMember
                .builder()
                .chatId("12345")
                .userId(98765L)
                .build();
    }

    public static BotApiMethod<Integer> getChatMemberCount() {
        return GetChatMemberCount
                .builder()
                .chatId("12345")
                .build();
    }

    public static BotApiMethod<File> getGetFile() {
        return GetFile
                .builder()
                .fileId("FileId")
                .build();
    }

    public static BotApiMethod<ArrayList<GameHighScore>> getGetGameHighScores() {
        return GetGameHighScores
                .builder()
                .chatId("12345")
                .messageId(67890)
                .userId(98765L)
                .build();
    }

    public static BotApiMethod<User> getGetMe() {
        return new GetMe();
    }

    public static BotApiMethod<UserProfilePhotos> getGetUserProfilePhotos() {
        return GetUserProfilePhotos
                .builder()
                .userId(98765L)
                .limit(10)
                .offset(3)
                .build();
    }

    public static BotApiMethod<WebhookInfo> getGetWebhookInfo() {
        return new GetWebhookInfo();
    }

    public static BotApiMethod<Boolean> getBanChatMember() {
        return BanChatMember
                .builder()
                .chatId("12345")
                .userId(98765L)
                .build();
    }

    public static BotApiMethod<Boolean> getLeaveChat() {
        return LeaveChat
                .builder()
                .chatId("12345")
                .build();
    }

    public static BotApiMethod<Boolean> getSendChatAction() {
        return SendChatAction
                .builder()
                .chatId("12345")
                .action(ActionType.RECORDVIDEO.toString())
                .build();
    }

    public static BotApiMethod<Message> getSendContact() {
        return SendContact
                .builder()
                .chatId("12345")
                .firstName("First Name")
                .lastName("Last Name")
                .phoneNumber("123456789")
                .replyMarkup(getKeyboardMarkup())
                .replyToMessageId(54)
                .build();
    }

    private static ReplyKeyboard getKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText("Button1");
        button.setRequestContact(true);
        row.add(button);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    private static InlineQueryResult getInlineQueryResultArticle() {
        return InlineQueryResultArticle
                .builder()
                .id("0")
                .title("Title")
                .url("Url")
                .hideUrl(false)
                .description("Description")
                .thumbnailUrl("ThumbUrl")
                .thumbnailWidth(10)
                .thumbnailHeight(20)
                .inputMessageContent(getInputMessageContent())
                .replyMarkup(getInlineKeyboardMarkup())
                .build();
    }

    private static InlineQueryResult getInlineQueryResultPhoto() {
        return InlineQueryResultPhoto
                .builder()
                .id("1")
                .photoUrl("PhotoUrl")
                .photoWidth(10)
                .photoHeight(20)
                .mimeType("image/jpg")
                .thumbnailUrl("ThumbUrl")
                .title("Title")
                .description("Description")
                .caption("Caption")
                .inputMessageContent(getInputMessageContent())
                .replyMarkup(getInlineKeyboardMarkup())
                .build();
    }

    private static InputMessageContent getInputMessageContent() {
        return InputTextMessageContent
                .builder()
                .messageText("Text")
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text("Button1")
                .callbackData("Callback")
                .build();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        return InlineKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .build();
    }

    public static BotApiMethod<Message> getSendGame() {
        return SendGame
                .builder()
                .chatId("12345")
                .gameShortName("MyGame")
                .build();
    }

    public static BotApiMethod<Message> getSendLocation() {
        return SendLocation
                .builder()
                .chatId("12345")
                .latitude(12.5)
                .longitude(21.5)
                .replyToMessageId(53)
                .build();
    }

    public static BotApiMethod<Message> getSendVenue() {
        return SendVenue
                .builder()
                .chatId("12345")
                .latitude(12.5)
                .longitude(21.5)
                .replyToMessageId(53)
                .title("Venue Title")
                .address("Address")
                .foursquareId("FourId")
                .build();
    }

    public static BotApiMethod<Serializable> getSetGameScore() {
        return SetGameScore
                .builder()
                .inlineMessageId("12345")
                .disableEditMessage(true)
                .score(12)
                .userId(98765L)
                .build();
    }

    public static BotApiMethod<Boolean> getUnbanChatMember() {
        return UnbanChatMember
                .builder()
                .chatId("12345")
                .userId(98765L)
                .build();
    }

    public static BotApiMethod<Message> getSendInvoice() {
        List<LabeledPrice> prices = new ArrayList<>();
        prices.add(new LabeledPrice("LABEL", 1000));

        return SendInvoice
                .builder()
                .chatId("12345")
                .title("Random title")
                .description("Random description")
                .payload("Random Payload")
                .providerToken("Random provider token")
                .startParameter("STARTPARAM")
                .maxTipAmount(100)
                .suggestedTipAmount(10)
                .suggestedTipAmount(50)
                .suggestedTipAmount(75)
                .currency("EUR")
                .prices(prices)
                .build();
    }
}
