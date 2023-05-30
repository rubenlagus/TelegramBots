package org.telegram.telegrambots.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.test.Fakes.FakeWebhook;
import org.telegram.telegrambots.updatesreceivers.ServerlessWebhook;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Andrey Korsakov
 * @version 1.0
 */
public class ServerlessWebhookTest {

    final FakeWebhook webhookBot = new FakeWebhook();
    ServerlessWebhook serverlessWebhook;

    @BeforeEach
    void setUp() {
        serverlessWebhook = new ServerlessWebhook();
        serverlessWebhook.registerWebhook(webhookBot);
    }

    @Test
    public void TestSendMessage() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendMessage());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"@test\",\"text\":\"Hithere\",\"parse_mode\":\"html\",\"reply_to_message_id\":12,\"reply_markup\":{\"force_reply\":true},\"method\":\"sendmessage\"}", map(result));
    }

    @Test
    public void TestAnswerCallbackQuery() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getAnswerCallbackQuery());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"callback_query_id\":\"id\",\"text\":\"text\",\"show_alert\":true,\"method\":\"answercallbackquery\"}", map(result));
    }

    @Test
    public void TestAnswerInlineQuery() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getAnswerInlineQuery());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"inline_query_id\":\"id\",\"results\":[{\"type\":\"article\",\"id\":\"0\",\"title\":\"Title\",\"input_message_content\":{\"message_text\":\"Text\",\"parse_mode\":\"Markdown\"},\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"Button1\",\"callback_data\":\"Callback\"}]]},\"url\":\"Url\",\"hide_url\":false,\"description\":\"Description\",\"thumbnail_url\":\"ThumbUrl\",\"thumbnail_width\":10,\"thumbnail_height\":20},{\"type\":\"photo\",\"id\":\"1\",\"photo_url\":\"PhotoUrl\",\"mime_type\":\"image/jpg\",\"photo_width\":10,\"photo_height\":20,\"thumbnail_url\":\"ThumbUrl\",\"title\":\"Title\",\"description\":\"Description\",\"caption\":\"Caption\",\"input_message_content\":{\"message_text\":\"Text\",\"parse_mode\":\"Markdown\"},\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"Button1\",\"callback_data\":\"Callback\"}]]},\"caption_entities\":[]}],\"cache_time\":100,\"is_personal\":true,\"next_offset\":\"3\",\"switch_pm_text\":\"pmText\",\"switch_pm_parameter\":\"PmParameter\",\"method\":\"answerInlineQuery\"}",
                map(result));
    }

    @Test
    public void TestEditMessageCaption() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getEditMessageCaption());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"ChatId\",\"message_id\":1,\"caption\":\"Caption\"," +
                "\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"Button1\",\"callback_data\":\"Callback\"}]]}," +
                "\"caption_entities\":[],\"method\":\"editmessagecaption\"}", map(result));
    }

    @Test
    public void TestEditMessageReplyMarkup() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getEditMessageReplyMarkup());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"inline_message_id\":\"12345\",\"reply_markup\":{" +
                "\"inline_keyboard\":[[{\"text\":\"Button1\"," +
                "\"callback_data\":\"Callback\"}]]},\"method\":\"editmessagereplymarkup\"}",
                map(result));
    }

    @Test
    public void TestEditMessageText() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getEditMessageText());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"ChatId\",\"message_id\":1,\"text\":\"Text\"," +
                "\"parse_mode\":\"Markdown\",\"reply_markup\":{\"" +
                "inline_keyboard\":[[{\"text\":\"Button1\",\"callback_data\"" +
                ":\"Callback\"}]]},\"method\":\"editmessagetext\"}", map(result));
    }

    @Test
    public void TestForwardMessage() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getForwardMessage());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"To\",\"from_chat_id\":\"From\",\"message_id\":15," +
                "\"disable_notification\":true,\"method\":\"forwardmessage\"}", map(result));
    }

    @Test
    public void TestGetChat() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetChat());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"getChat\"}", map(result));
    }

    @Test
    public void TestGetChatAdministrators() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getChatAdministrators());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"getChatAdministrators\"}", map(result));
    }

    @Test
    public void TestGetChatMember() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getChatMember());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"user_id\":98765,\"method\":\"getChatMember\"}", map(result));
    }

    @Test
    public void TestGetChatMembersCount() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getChatMemberCount());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"getChatMemberCount\"}", map(result));
    }

    @Test
    public void TestGetFile() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetFile());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"file_id\":\"FileId\",\"method\":\"getFile\"}", map(result));
    }

    @Test
    public void TestGetGameHighScores() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetGameHighScores());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"message_id\":67890,\"user_id\":98765,\"method\":\"getGameHighScores\"}", map(result));
    }

    @Test
    public void TestGetMe() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetMe());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"method\":\"getme\"}", map(result));
    }

    @Test
    public void TestGetUserProfilePhotos() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetUserProfilePhotos());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"user_id\":98765,\"offset\":3,\"limit\":10,\"method\":\"getuserprofilephotos\"}", map(result));
    }

    @Test
    public void TestGetWebhookInfo() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetWebhookInfo());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"method\":\"getwebhookinfo\"}", map(result));
    }

    @Test
    public void TestKickChatMember() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getBanChatMember());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"user_id\":98765,\"method\":\"banChatMember\"}", map(result));
    }

    @Test
    public void TestLeaveChat() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getLeaveChat());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"leaveChat\"}", map(result));
    }

    @Test
    public void TestSendChatAction() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendChatAction());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"action\":\"record_video\",\"method\":\"sendChatAction\"}", map(result));
    }

    @Test
    public void TestSendContact() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendContact());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"phone_number\":\"123456789\",\"first_name\":\"First Name\",\"last_name\":\"Last Name\",\"reply_to_message_id\":54,\"reply_markup\":{\"keyboard\":[[{\"text\":\"Button1\",\"request_contact\":true}]],\"resize_keyboard\":true,\"one_time_keyboard\":true,\"selective\":true},\"method\":\"sendContact\"}", map(result));
    }

    @Test
    public void TestSendGame() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendGame());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"game_short_name\":\"MyGame\",\"method\":\"sendGame\"}", map(result));
    }

    @Test
    public void TestSendLocation() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendLocation());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"latitude\":12.5,\"longitude\":21.5,\"reply_to_message_id\":53,\"method\":\"sendlocation\"}", map(result));
    }

    @Test
    public void TestSendVenue() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendVenue());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"latitude\":12.5,\"longitude\":21.5,\"title\":\"Venue Title\",\"address\":\"Address\",\"foursquare_id\":\"FourId\",\"reply_to_message_id\":53,\"method\":\"sendVenue\"}", map(result));
    }

    @Test
    public void TestSetGameScore() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSetGameScore());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"inline_message_id\":\"12345\",\"disable_edit_message\":true,\"user_id\":98765,\"score\":12,\"method\":\"setGameScore\"}", map(result));
    }

    @Test
    public void TestUnbanChatMember() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getUnbanChatMember());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"user_id\":98765,\"method\":\"unbanchatmember\"}", map(result));
    }

    @Test
    public void TestSendInvoice() throws Exception {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendInvoice());

        BotApiMethod<?> result = serverlessWebhook.updateReceived(webhookBot.getBotPath(), getUpdate());

        assertEquals("{\"chat_id\":\"12345\",\"title\":\"Random title\",\"description\":\"Random description\"," +
                "\"payload\":\"Random Payload\",\"provider_token\":\"Random provider token\",\"start_parameter\":\"STARTPARAM\"," +
                "\"currency\":\"EUR\",\"prices\":[{\"label\":\"LABEL\",\"amount\":1000}],\"max_tip_amount\":100," +
                "\"suggested_tip_amounts\":[10,50,75],\"method\":\"sendinvoice\"}", map(result));
    }

    private Update getUpdate() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue("{\"update_id\": 10}", Update.class);
        } catch (IOException e) {
            return null;
        }
    }

    private <T extends Serializable> String map(BotApiMethod<T> method) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(method);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize");
            return null;
        }
    }
}
