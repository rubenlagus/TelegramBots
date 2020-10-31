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
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMembersCount;
import org.telegram.telegrambots.meta.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.LeaveChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendGame;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVenue;
import org.telegram.telegrambots.meta.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.meta.api.objects.WebhookInfo;
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
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public final class BotApiMethodHelperFactory {
    private BotApiMethodHelperFactory() {
    }

    public static BotApiMethod<Message> getSendMessage() {
        return new SendMessage()
                .setChatId("@test")
                .setText("Hithere")
                .setReplyToMessageId(12)
                .setParseMode(ParseMode.HTML)
                .setReplyMarkup(new ForceReplyKeyboard());
    }

    public static BotApiMethod<Boolean> getAnswerCallbackQuery() {
        return new AnswerCallbackQuery()
                .setCallbackQueryId("id")
                .setText("text")
                .setShowAlert(true);
    }

    public static BotApiMethod<Boolean> getAnswerInlineQuery() {
        return new AnswerInlineQuery()
                .setInlineQueryId("id")
                .setPersonal(true)
                .setResults(getInlineQueryResultArticle(), getInlineQueryResultPhoto())
                .setCacheTime(100)
                .setNextOffset("3")
                .setSwitchPmParameter("PmParameter")
                .setSwitchPmText("pmText");
    }

    public static BotApiMethod<Serializable> getEditMessageCaption() {
        return new EditMessageCaption()
                .setChatId("ChatId")
                .setMessageId(1)
                .setCaption("Caption")
                .setReplyMarkup(getInlineKeyboardMarkup());
    }


    public static BotApiMethod<Serializable> getEditMessageText() {
        return new EditMessageText()
                .setChatId("ChatId")
                .setMessageId(1)
                .setText("Text")
                .setParseMode(ParseMode.MARKDOWN)
                .setReplyMarkup(getInlineKeyboardMarkup());
    }

    public static BotApiMethod<Serializable> getEditMessageReplyMarkup() {
        return new EditMessageReplyMarkup()
                .setInlineMessageId("12345")
                .setReplyMarkup(getInlineKeyboardMarkup());
    }

    public static BotApiMethod<Message> getForwardMessage() {
        return new ForwardMessage(54L, 123L, 55)
                .setFromChatId("From")
                .setChatId("To")
                .setMessageId(15)
                .disableNotification();
    }

    public static BotApiMethod<Chat> getGetChat() {
        return new GetChat()
                .setChatId("12345");
    }

    public static BotApiMethod<ArrayList<ChatMember>> getChatAdministrators() {
        return new GetChatAdministrators()
                .setChatId("12345");
    }

    public static BotApiMethod<ChatMember> getChatMember() {
        return new GetChatMember()
                .setChatId("12345")
                .setUserId(98765);
    }

    public static BotApiMethod<Integer> getChatMembersCount() {
        return new GetChatMembersCount()
                .setChatId("12345");
    }

    public static BotApiMethod<File> getGetFile() {
        return new GetFile()
                .setFileId("FileId");
    }

    public static BotApiMethod<ArrayList<GameHighScore>> getGetGameHighScores() {
        return new GetGameHighScores()
                .setChatId("12345")
                .setMessageId(67890)
                .setUserId(98765);
    }

    public static BotApiMethod<User> getGetMe() {
        return new GetMe();
    }

    public static BotApiMethod<UserProfilePhotos> getGetUserProfilePhotos() {
        return new GetUserProfilePhotos()
                .setUserId(98765)
                .setLimit(10)
                .setOffset(3);
    }

    public static BotApiMethod<WebhookInfo> getGetWebhookInfo() {
        return new GetWebhookInfo();
    }

    public static BotApiMethod<Boolean> getKickChatMember() {
        return new KickChatMember()
                .setChatId("12345")
                .setUserId(98765);
    }

    public static BotApiMethod<Boolean> getLeaveChat() {
        return new LeaveChat()
                .setChatId("12345");
    }

    public static BotApiMethod<Boolean> getSendChatAction() {
        return new SendChatAction()
                .setChatId("12345")
                .setAction(ActionType.RECORDVIDEO);
    }

    public static BotApiMethod<Message> getSendContact() {
        return new SendContact()
                .setChatId("12345")
                .setFirstName("First Name")
                .setLastName("Last Name")
                .setPhoneNumber("123456789")
                .setReplyMarkup(getKeyboardMarkup())
                .setReplyToMessageId(54);
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
        return new InlineQueryResultArticle()
                .setId("0")
                .setTitle("Title")
                .setUrl("Url")
                .setHideUrl(false)
                .setDescription("Description")
                .setThumbUrl("ThumbUrl")
                .setThumbWidth(10)
                .setThumbHeight(20)
                .setInputMessageContent(getInputMessageContent())
                .setReplyMarkup(getInlineKeyboardMarkup());
    }

    private static InlineQueryResult getInlineQueryResultPhoto() {
        return InlineQueryResultPhoto
                .builder()
                .id("1")
                .photoUrl("PhotoUrl")
                .photoWidth(10)
                .photoHeight(20)
                .mimeType("image/jpg")
                .thumbUrl("ThumbUrl")
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
        InlineKeyboardButton button = new InlineKeyboardButton()
                .setText("Button1")
                .setCallbackData("Callback");
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        return new InlineKeyboardMarkup()
                .setKeyboard(keyboard);
    }

    public static BotApiMethod<Message> getSendGame() {
        return new SendGame()
                .setChatId("12345")
                .setGameShortName("MyGame");
    }

    public static BotApiMethod<Message> getSendLocation() {
        return new SendLocation()
                .setChatId("12345")
                .setLatitude(12.5F)
                .setLongitude(21.5F)
                .setReplyToMessageId(53);
    }

    public static BotApiMethod<Message> getSendVenue() {
        return new SendVenue()
                .setChatId("12345")
                .setLatitude(12.5F)
                .setLongitude(21.5F)
                .setReplyToMessageId(53)
                .setTitle("Venue Title")
                .setAddress("Address")
                .setFoursquareId("FourId");
    }

    public static BotApiMethod<Serializable> getSetGameScore() {
        return new SetGameScore()
                .setInlineMessageId("12345")
                .setDisableEditMessage(true)
                .setScore(12)
                .setUserId(98765);
    }

    public static BotApiMethod<Boolean> getUnbanChatMember() {
        return new UnbanChatMember()
                .setChatId("12345")
                .setUserId(98765);
    }

    public static BotApiMethod<Message> getSendInvoice() {
        List<LabeledPrice> prices = new ArrayList<>();
        prices.add(new LabeledPrice("LABEL", 1000));

        return new SendInvoice()
                .setChatId(12345)
                .setTitle("Random title")
                .setDescription("Random description")
                .setPayload("Random Payload")
                .setProviderToken("Random provider token")
                .setStartParameter("STARTPARAM")
                .setCurrency("EUR")
                .setPrices(prices);
    }
}
