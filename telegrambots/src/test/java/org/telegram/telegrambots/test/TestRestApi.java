package org.telegram.telegrambots.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.*;
import org.telegram.telegrambots.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.methods.groupadministration.*;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.test.Fakes.FakeWebhook;
import org.telegram.telegrambots.updatesreceivers.RestApi;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class TestRestApi extends JerseyTest {

    private FakeWebhook webhookBot = new FakeWebhook();
    private RestApi restApi;

    @Override
    protected Application configure() {
        restApi = new RestApi();
        return new ResourceConfig().register(restApi).register(JacksonFeature.class);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        restApi.registerCallback(webhookBot);
        super.setUp();
    }

    @Test
    public void TestSendMessage() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendMessage());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                .request(MediaType.APPLICATION_JSON)
                .post(entity, SendMessage.class);
        assertEquals("{\"chat_id\":\"@test\",\"text\":\"Hithere\",\"parse_mode\":\"html\",\"reply_to_message_id\":12,\"reply_markup\":{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard\",\"force_reply\":true},\"method\":\"sendmessage\"}", map(result));
    }

    @Test
    public void TestAnswerCallbackQuery() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getAnswerCallbackQuery());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, AnswerCallbackQuery.class);

        assertEquals("{\"callback_query_id\":\"id\",\"text\":\"text\",\"show_alert\":true,\"method\":\"answercallbackquery\"}", map(result));
    }

    @Test
    public void TestAnswerInlineQuery() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getAnswerInlineQuery());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, AnswerInlineQuery.class);

        assertEquals("{\"personal\":true,\"inline_query_id\":\"id\",\"results\":[{\"@class\":\"org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle\",\"type\":\"article\",\"id\":\"0\",\"title\":\"Title\",\"input_message_content\":{\"@class\":\"org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent\",\"message_text\":\"Text\",\"parse_mode\":\"Markdown\"},\"reply_markup\":{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup\",\"inline_keyboard\":[[{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton\",\"text\":\"Button1\",\"callback_data\":\"Callback\"}]]},\"url\":\"Url\",\"hide_url\":false,\"description\":\"Description\",\"thumb_url\":\"ThumbUrl\",\"thumb_width\":10,\"thumb_height\":20},{\"@class\":\"org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultPhoto\",\"type\":\"photo\",\"id\":\"1\",\"photo_url\":\"PhotoUrl\",\"mime_type\":\"image/jpg\",\"photo_width\":10,\"photo_height\":20,\"thumb_url\":\"ThumbUrl\",\"title\":\"Title\",\"description\":\"Description\",\"caption\":\"Caption\",\"input_message_content\":{\"@class\":\"org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent\",\"message_text\":\"Text\",\"parse_mode\":\"Markdown\"},\"reply_markup\":{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup\",\"inline_keyboard\":[[{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton\",\"text\":\"Button1\",\"callback_data\":\"Callback\"}]]}}],\"cache_time\":100,\"is_personal\":true,\"next_offset\":\"3\",\"switch_pm_text\":\"pmText\",\"switch_pm_parameter\":\"PmParameter\",\"method\":\"answerInlineQuery\"}", map(result));
    }

    @Test
    public void TestEditMessageCaption() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getEditMessageCaption());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, EditMessageCaption.class);

        assertEquals("{\"chat_id\":\"ChatId\",\"message_id\":1,\"caption\":\"Caption\"," +
                "\"reply_markup\":{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard" +
                ".InlineKeyboardMarkup\",\"inline_keyboard\":[[{\"@class\":\"org.telegram." +
                "telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton\"," +
                "\"text\":\"Button1\",\"callback_data\":\"Callback\"}]]},\"method\":" +
                "\"editmessagecaption\"}", map(result));
    }

    @Test
    public void TestEditMessageReplyMarkup() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getEditMessageReplyMarkup());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, EditMessageReplyMarkup.class);

        assertEquals("{\"inline_message_id\":\"12345\",\"reply_markup\":{\"@class\":\"org" +
                ".telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup\"," +
                "\"inline_keyboard\":[[{\"@class\":\"org.telegram.telegrambots.api.objects." +
                "replykeyboard.buttons.InlineKeyboardButton\",\"text\":\"Button1\"," +
                "\"callback_data\":\"Callback\"}]]},\"method\":\"editmessagereplymarkup\"}",
                map(result));
    }

    @Test
    public void TestEditMessageText() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getEditMessageText());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, EditMessageText.class);

        assertEquals("{\"chat_id\":\"ChatId\",\"message_id\":1,\"text\":\"Text\"," +
                "\"parse_mode\":\"Markdown\",\"reply_markup\":{\"@class\":\"org.telegram." +
                "telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup\",\"" +
                "inline_keyboard\":[[{\"@class\":\"org.telegram.telegrambots.api.objects." +
                "replykeyboard.buttons.InlineKeyboardButton\",\"text\":\"Button1\",\"callback_data\"" +
                ":\"Callback\"}]]},\"method\":\"editmessagetext\"}", map(result));
    }

    @Test
    public void TestForwardMessage() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getForwardMessage());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, ForwardMessage.class);

        assertEquals("{\"chat_id\":\"To\",\"from_chat_id\":\"From\",\"message_id\":15," +
                "\"disable_notification\":true,\"method\":\"forwardmessage\"}", map(result));
    }

    @Test
    public void TestGetChat() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetChat());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetChat.class);

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"getChat\"}", map(result));
    }

    @Test
    public void TestGetChatAdministrators() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getChatAdministrators());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetChatAdministrators.class);

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"getChatAdministrators\"}", map(result));
    }

    @Test
    public void TestGetChatMember() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getChatMember());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetChatMember.class);

        assertEquals("{\"chat_id\":\"12345\",\"user_id\":98765,\"method\":\"getChatMember\"}", map(result));
    }

    @Test
    public void TestGetChatMemberCount() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getChatMemberCount());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetChatMemberCount.class);

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"getChatMembersCount\"}", map(result));
    }

    @Test
    public void TestGetFile() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetFile());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetFile.class);

        assertEquals("{\"file_id\":\"FileId\",\"method\":\"getFile\"}", map(result));
    }

    @Test
    public void TestGetGameHighScores() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetGameHighScores());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetGameHighScores.class);

        assertEquals("{\"chat_id\":\"12345\",\"message_id\":67890,\"user_id\":98765,\"method\":\"getGameHighScores\"}", map(result));
    }

    @Test
    public void TestGetMe() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetMe());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetMe.class);

        assertEquals("{\"method\":\"getme\"}", map(result));
    }

    @Test
    public void TestGetUserProfilePhotos() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetUserProfilePhotos());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetUserProfilePhotos.class);

        assertEquals("{\"user_id\":98765,\"offset\":3,\"limit\":10,\"method\":\"getuserprofilephotos\"}", map(result));
    }

    @Test
    public void TestGetWebhookInfo() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getGetWebhookInfo());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, GetWebhookInfo.class);

        assertEquals("{\"method\":\"getwebhookinfo\"}", map(result));
    }

    @Test
    public void TestKickChatMember() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getKickChatMember());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, KickChatMember.class);

        assertEquals("{\"chat_id\":\"12345\",\"user_id\":98765,\"method\":\"kickchatmember\"}", map(result));
    }

    @Test
    public void TestLeaveChat() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getLeaveChat());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, LeaveChat.class);

        assertEquals("{\"chat_id\":\"12345\",\"method\":\"leaveChat\"}", map(result));
    }

    @Test
    public void TestSendChatAction() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendChatAction());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SendChatAction.class);

        assertEquals("{\"chat_id\":\"12345\",\"action\":\"record_video\",\"method\":\"sendChatAction\"}", map(result));
    }

    @Test
    public void TestSendContact() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendContact());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SendContact.class);

        assertEquals("{\"chat_id\":\"12345\",\"phone_number\":\"123456789\",\"first_name\":\"First Name\",\"last_name\":\"Last Name\",\"reply_to_message_id\":54,\"reply_markup\":{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup\",\"keyboard\":[[{\"@class\":\"org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton\",\"text\":\"Button1\",\"request_contact\":true}]],\"resize_keyboard\":true,\"one_time_keyboard\":true,\"selective\":true},\"method\":\"sendContact\"}", map(result));
    }

    @Test
    public void TestSendGame() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendGame());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SendGame.class);

        assertEquals("{\"chat_id\":\"12345\",\"game_short_name\":\"MyGame\",\"method\":\"sendGame\"}", map(result));
    }

    @Test
    public void TestSendLocation() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendLocation());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SendLocation.class);

        assertEquals("{\"chat_id\":\"12345\",\"latitude\":12.5,\"longitude\":21.5,\"reply_to_message_id\":53,\"method\":\"sendlocation\"}", map(result));
    }

    @Test
    public void TestSendVenue() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendVenue());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SendVenue.class);

        assertEquals("{\"chat_id\":\"12345\",\"latitude\":12.5,\"longitude\":21.5,\"title\":\"Venue Title\",\"address\":\"Address\",\"foursquare_id\":\"FourId\",\"reply_to_message_id\":53,\"method\":\"sendVenue\"}", map(result));
    }

    @Test
    public void TestSetGameScore() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSetGameScore());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SetGameScore.class);

        assertEquals("{\"inline_message_id\":\"12345\",\"disable_edit_message\":true,\"user_id\":98765,\"score\":12,\"method\":\"setGameScore\"}", map(result));
    }

    @Test
    public void TestUnbanChatMember() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getUnbanChatMember());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, UnbanChatMember.class);

        assertEquals("{\"chat_id\":\"12345\",\"user_id\":98765,\"method\":\"unbanchatmember\"}", map(result));
    }

    @Test
    public void TestSendInvoice() {
        webhookBot.setReturnValue(BotApiMethodHelperFactory.getSendInvoice());

        Entity<Update> entity = Entity.json(getUpdate());
        BotApiMethod result =
                target("callback/testbot")
                        .request(MediaType.APPLICATION_JSON)
                        .post(entity, SendInvoice.class);

        assertEquals("{\"chat_id\":12345,\"title\":\"Random title\",\"description\":\"Random description\"" +
                ",\"payload\":\"Random Payload\",\"provider_token\":\"Random provider token\",\"start_parameter\":" +
                "\"STARTPARAM\",\"currency\":\"EUR\",\"prices\":[{\"@class\":" +
                "\"org.telegram.telegrambots.api.objects.payments.LabeledPrice\",\"label\":\"LABEL\"," +
                "\"amount\":1000}],\"method\":\"sendinvoice\"}", map(result));
    }

    private Update getUpdate() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue("{\"update_id\": 10}", Update.class);
        } catch (IOException e) {
            return null;
        }
    }

    private String map(BotApiMethod method) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(method);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize");
            return null;
        }
    }
}
