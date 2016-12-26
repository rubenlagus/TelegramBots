package org.telegram.telegrambots.test;

import org.telegram.telegrambots.api.methods.ActionType;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.GetMe;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.LeaveChat;
import org.telegram.telegrambots.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.api.methods.send.SendChatAction;
import org.telegram.telegrambots.api.methods.send.SendContact;
import org.telegram.telegrambots.api.methods.send.SendGame;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendVenue;
import org.telegram.telegrambots.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 02 of November of 2016
 */
public final class BotApiMethodHelperFactory {
    private BotApiMethodHelperFactory() {
    }

    public static BotApiMethod getSendMessage() {
        return new SendMessage()
                .setChatId("@test")
                .setText("Hithere")
                .setReplyToMessageId(12)
                .setParseMode(ParseMode.HTML)
                .setReplyMarkup(new ForceReplyKeyboard());
    }

    public static BotApiMethod getAnswerCallbackQuery() {
        return new AnswerCallbackQuery()
                .setCallbackQueryId("id")
                .setText("text")
                .setShowAlert(true);
    }

    public static BotApiMethod getAnswerInlineQuery() {
        return new AnswerInlineQuery()
                .setInlineQueryId("id")
                .setPersonal(true)
                .setResults(getInlineQueryResultArticle(), getInlineQueryResultPhoto())
                .setCacheTime(100)
                .setNextOffset("3")
                .setSwitchPmParameter("PmParameter")
                .setSwitchPmText("pmText");
    }

    public static BotApiMethod getEditMessageCaption() {
        return new EditMessageCaption()
                .setChatId("ChatId")
                .setMessageId(1)
                .setCaption("Caption")
                .setReplyMarkup(getInlineKeyboardMarkup());
    }


    public static BotApiMethod getEditMessageText() {
        return new EditMessageText()
                .setChatId("ChatId")
                .setMessageId(1)
                .setText("Text")
                .setParseMode(ParseMode.MARKDOWN)
                .setReplyMarkup(getInlineKeyboardMarkup());
    }

    public static BotApiMethod getEditMessageReplyMarkup() {
        return new EditMessageReplyMarkup()
                .setInlineMessageId("12345")
                .setReplyMarkup(getInlineKeyboardMarkup());
    }

    public static BotApiMethod getForwardMessage() {
        return new ForwardMessage()
                .setFromChatId("From")
                .setChatId("To")
                .setMessageId(15)
                .disableNotification();
    }

    public static BotApiMethod getGetChat() {
        return new GetChat()
                .setChatId("12345");
    }

    public static BotApiMethod getChatAdministrators() {
        return new GetChatAdministrators()
                .setChatId("12345");
    }

    public static BotApiMethod getChatMember() {
        return new GetChatMember()
                .setChatId("12345")
                .setUserId(98765);
    }

    public static BotApiMethod getChatMemberCount() {
        return new GetChatMemberCount()
                .setChatId("12345");
    }

    public static BotApiMethod getGetFile() {
        return new GetFile()
                .setFileId("FileId");
    }

    public static BotApiMethod getGetGameHighScores() {
        return new GetGameHighScores()
                .setChatId("12345")
                .setMessageId(67890)
                .setUserId(98765);
    }

    public static BotApiMethod getGetMe() {
        return new GetMe();
    }

    public static BotApiMethod getGetUserProfilePhotos() {
        return new GetUserProfilePhotos()
                .setUserId(98765)
                .setLimit(10)
                .setOffset(3);
    }

    public static BotApiMethod getGetWebhookInfo() {
        return new GetWebhookInfo();
    }

    public static BotApiMethod getKickChatMember() {
        return new KickChatMember()
                .setChatId("12345")
                .setUserId(98765);
    }

    public static BotApiMethod getLeaveChat() {
        return new LeaveChat()
                .setChatId("12345");
    }

    public static BotApiMethod getSendChatAction() {
        return new SendChatAction()
                .setChatId("12345")
                .setAction(ActionType.RECORDVIDEO);
    }

    public static BotApiMethod getSendContact() {
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
        keyboardMarkup.setOneTimeKeyboad(true);
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
        return new InlineQueryResultPhoto()
                .setId("1")
                .setPhotoUrl("PhotoUrl")
                .setPhotoWidth(10)
                .setPhotoHeight(20)
                .setMimeType("image/jpg")
                .setThumbUrl("ThumbUrl")
                .setTitle("Title")
                .setDescription("Description")
                .setCaption("Caption")
                .setInputMessageContent(getInputMessageContent())
                .setReplyMarkup(getInlineKeyboardMarkup());
    }

    private static InputMessageContent getInputMessageContent() {
        return new InputTextMessageContent()
                .setMessageText("Text")
                .setParseMode(ParseMode.MARKDOWN);
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

    public static BotApiMethod getSendGame() {
        return new SendGame()
                .setChatId("12345")
                .setGameShortName("MyGame");
    }

    public static BotApiMethod getSendLocation() {
        return new SendLocation()
                .setChatId("12345")
                .setLatitude(12.5F)
                .setLongitude(21.5F)
                .setReplyToMessageId(53);
    }

    public static BotApiMethod getSendVenue() {
        return new SendVenue()
                .setChatId("12345")
                .setLatitude(12.5F)
                .setLongitude(21.5F)
                .setReplyToMessageId(53)
                .setTitle("Venue Title")
                .setAddress("Address")
                .setFoursquareId("FourId");
    }

    public static BotApiMethod getSetGameScore() {
        return new SetGameScore()
                .setInlineMessageId("12345")
                .setDisableEditMessage(true)
                .setScore(12)
                .setUserId(98765);
    }

    public static BotApiMethod getUnbanChatMember() {
        return new UnbanChatMember()
                .setChatId("12345")
                .setUserId(98765);
    }
}
