package org.telegram.telegrambots.meta.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.updates.Close;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.methods.updates.LogOut;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
class TestDeserialization {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void TestListUpdates() throws Exception {
        String updateText = "{\"ok\":true,\"result\":[{\"update_id\":79995144,\n" +
                "\"message\":{\"message_id\":90,\"from\":{\"id\":1234567,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"en\"},\"chat\":{\"id\":1234567,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154223,\"text\":\"/start\",\"entities\":[{\"offset\":0,\"length\":6,\"type\":\"bot_command\"}]}},{\"update_id\":79995145,\n" +
                "\"message\":{\"message_id\":91,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154306,\"text\":\"/start\",\"entities\":[{\"offset\":0,\"length\":6,\"type\":\"bot_command\"}]}},{\"update_id\":79995146,\n" +
                "\"message\":{\"message_id\":92,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154313,\"text\":\"/test\",\"entities\":[{\"offset\":0,\"length\":5,\"type\":\"bot_command\"}]}},{\"update_id\":79995147,\n" +
                "\"message\":{\"message_id\":93,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154318,\"text\":\"@aaaa\"}},{\"update_id\":79995148,\n" +
                "\"message\":{\"message_id\":94,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154326,\"game\":{\"title\":\"Brick Stacker\",\"description\":\"Play the game to see how many bricks you can stack, and contribute to the tower!\",\"photo\":[{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":12663,\"width\":320,\"height\":180},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":35050,\"width\":640,\"height\":360}],\"animation\":{\"file_name\":\"video.mp4\",\"mime_type\":\"video/mp4\",\"duration\":6,\"width\":320,\"height\":180,\"thumb\":{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":6209,\"width\":320,\"height\":180},\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":411406}},\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"Play Brick Stacker!\",\"callback_game\":{}}]]},\"via_bot\":{\"id\":280713127,\"is_bot\":true,\"first_name\":\"Gamee\",\"username\":\"gamee\"}}},{\"update_id\":79995149,\n" +
                "\"message\":{\"message_id\":95,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154339,\"photo\":[{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":8291,\"width\":320,\"height\":180},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":25998,\"width\":800,\"height\":450},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":38407,\"width\":1200,\"height\":675}],\"via_bot\":{\"id\":114528005,\"is_bot\":true,\"first_name\":\"Yandex Image Search\",\"username\":\"pic\"}}},{\"update_id\":79995150,\n" +
                "\"message\":{\"message_id\":96,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154347,\"location\":{\"latitude\":34.76437,\"longitude\":0.001983}}},{\"update_id\":79995151,\n" +
                "\"message\":{\"message_id\":97,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154360,\"video_note\":{\"duration\":3,\"length\":240,\"thumb\":{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":6852,\"width\":240,\"height\":240},\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":100544}}},{\"update_id\":79995152,\n" +
                "\"message\":{\"message_id\":98,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154363,\"voice\":{\"duration\":1,\"mime_type\":\"audio/ogg\",\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":5198}}},{\"update_id\":79995153,\n" +
                "\"message\":{\"message_id\":99,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"it\"},\"chat\":{\"id\":12345678,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"type\":\"private\"},\"date\":1604154371,\"photo\":[{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":14395,\"width\":180,\"height\":320},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":52852,\"width\":450,\"height\":800},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":84493,\"width\":720,\"height\":1280}]}},{\"update_id\":79995154,\n" +
                "\"message\":{\"message_id\":6,\"from\":{\"id\":1234567,\"is_bot\":false,\"first_name\":\"MyFirstName\",\"username\":\"MyUsername\",\"language_code\":\"en\"},\"chat\":{\"id\":-1556359722345678,\"title\":\"test group\",\"type\":\"supergroup\"},\"date\":1604163105,\"new_chat_members\":[{\"id\":123455678,\"is_bot\":true,\"first_name\":\"Testing\",\"username\":\"TestingBot\"}]}}]}";

        ArrayList<Update> response = new GetUpdates().deserializeResponse(updateText);
        assertEquals(11, response.size());

        JsonNode realArray = mapper.readTree(updateText).get("result");

        assertUpdates(response, realArray);
    }

    private void assertUpdates(ArrayList<Update> response, JsonNode realArray) throws JsonProcessingException {
        Map<Integer, JsonNode> updateMap = new HashMap<>();
        for (int i = 0; i < realArray.size(); i++) {
            JsonNode update = realArray.get(i);
            updateMap.put(update.get("update_id").asInt(), update);
        }

        for (Update update : response) {
            Integer updateId = update.getUpdateId();
            JsonNode realUpdate = updateMap.get(updateId);
            JsonNode handledUpdate = mapper.readTree(mapper.writeValueAsString(update));
            assertEquals(realUpdate, handledUpdate);
        }
    }

    @Test
    void TestListUpdates2() throws Exception {
        String updateText = "{\"ok\":true,\"result\":[{\"update_id\":259894298,\n" +
                "\"message\":{\"message_id\":101,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\"},\"chat\":{\"id\":12345678,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"type\":\"private\"},\"date\":1604171814,\"text\":\"/start\",\"entities\":[{\"offset\":0,\"length\":6,\"type\":\"bot_command\"}]}},{\"update_id\":259894299,\n" +
                "\"message\":{\"message_id\":102,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"language_code\":\"en\"},\"chat\":{\"id\":12345678,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"type\":\"private\"},\"date\":1604188661,\"text\":\"/start\",\"entities\":[{\"offset\":0,\"length\":6,\"type\":\"bot_command\"}]}},{\"update_id\":259894300,\n" +
                "\"message\":{\"message_id\":103,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"language_code\":\"en\"},\"chat\":{\"id\":12345678,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"type\":\"private\"},\"date\":1604188669,\"photo\":[{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":18933,\"width\":180,\"height\":320},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":80310,\"width\":450,\"height\":800},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":114661,\"width\":720,\"height\":1280}]}},{\"update_id\":259894301,\n" +
                "\"message\":{\"message_id\":104,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"language_code\":\"en\"},\"chat\":{\"id\":12345678,\"first_name\":\"FistName\",\"last_name\":\"LastName\",\"username\":\"username\",\"type\":\"private\"},\"date\":1604188673,\"voice\":{\"duration\":1,\"mime_type\":\"audio/ogg\",\"file_id\":\"FILEID\",\"file_unique_id\":\"AgADFQADqRjwTA\",\"file_size\":8386}}},{\"update_id\":259894302,\n" +
                "\"message\":{\"message_id\":105,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"Username\"},\"chat\":{\"id\":12345678,\"first_name\":\"FirstName\",\"username\":\"Username\",\"type\":\"private\"},\"date\":1604226451,\"text\":\"/start\",\"entities\":[{\"offset\":0,\"length\":6,\"type\":\"bot_command\"}]}},{\"update_id\":259894303,\n" +
                "\"message\":{\"message_id\":106,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"Username\",\"language_code\":\"en\"},\"chat\":{\"id\":12345678,\"first_name\":\"FirstName\",\"username\":\"Username\",\"type\":\"private\"},\"date\":1604226480,\"document\":{\"file_name\":\"aaa.txt\",\"mime_type\":\"text/plain\",\"file_id\":\"FILEID\",\"file_unique_id\":\"AgADiQEAAjTe-VQ\",\"file_size\":2}}}]}";

        ArrayList<Update> response = new GetUpdates().deserializeResponse(updateText);
        assertEquals(6, response.size());

        JsonNode realArray = mapper.readTree(updateText).get("result");
        assertUpdates(response, realArray);
    }

    @Test
    void TestListUpdates3() throws Exception {
        String updateText = "{\"ok\":true,\"result\":[{\"update_id\":259894302,\n" +
                "\"message\":{\"message_id\":105,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"Username\"},\"chat\":{\"id\":12345678,\"first_name\":\"FirstName\",\"username\":\"Username\",\"type\":\"private\"},\"date\":1604226451,\"text\":\"/start\",\"entities\":[{\"offset\":0,\"length\":6,\"type\":\"bot_command\"}]}},{\"update_id\":259894303,\n" +
                "\"message\":{\"message_id\":106,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"Username\",\"language_code\":\"en\"},\"chat\":{\"id\":12345678,\"first_name\":\"FirstName\",\"username\":\"Username\",\"type\":\"private\"},\"date\":1604226480,\"document\":{\"file_name\":\"aaa.txt\",\"mime_type\":\"text/plain\",\"file_id\":\"FILEID\",\"file_unique_id\":\"AgADiQEAAjTe-VQ\",\"file_size\":2}}},{\"update_id\":259894304,\n" +
                "\"message\":{\"message_id\":107,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"group\",\"all_members_are_administrators\":true},\"date\":1604281682,\"new_chat_members\":[{\"id\":123455678,\"is_bot\":true,\"first_name\":\"Testing Telegram Bots\",\"username\":\"TestingRanBot\"}]}},{\"update_id\":259894305,\n" +
                "\"message\":{\"message_id\":108,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"group\",\"all_members_are_administrators\":true},\"date\":1604281713,\"left_chat_member\":{\"id\":123455678,\"is_bot\":true,\"first_name\":\"Testing Telegram Bots\",\"username\":\"TestingRanBot\"}}},{\"update_id\":259894306,\n" +
                "\"message\":{\"message_id\":109,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"group\",\"all_members_are_administrators\":true},\"date\":1604281720,\"new_chat_members\":[{\"id\":123455678,\"is_bot\":true,\"first_name\":\"Testing Telegram Bots\",\"username\":\"TestingRanBot\"}]}},{\"update_id\":259894307,\n" +
                "\"message\":{\"message_id\":110,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"group\",\"all_members_are_administrators\":false},\"date\":1604281763,\"migrate_to_chat_id\":-10011869112345}},{\"update_id\":259894308,\n" +
                "\"message\":{\"message_id\":1,\"from\":{\"id\":12345678,\"is_bot\":true,\"first_name\":\"Group\",\"username\":\"GroupAnonymousBot\"},\"sender_chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604281763,\"migrate_from_chat_id\":-10011869112345}},{\"update_id\":259894309,\n" +
                "\"message\":{\"message_id\":2,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604281985,\"sticker\":{\"width\":512,\"height\":512,\"emoji\":\"\\ud83e\\udd2c\",\"set_name\":\"ShadowKitty\",\"is_animated\":true,\"thumb\":{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":7546,\"width\":128,\"height\":128},\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":13187}}},{\"update_id\":259894310,\n" +
                "\"message\":{\"message_id\":3,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604281993,\"photo\":[{\"file_id\":\"FILEID\",\"file_unique_id\":\"AQADL3I6J10AAzbcAQAB\",\"file_size\":15207,\"width\":148,\"height\":320},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":64579,\"width\":369,\"height\":800},{\"file_id\":\"FILEID\",\"file_unique_id\":\"FILEID\",\"file_size\":93424,\"width\":591,\"height\":1280}]}},{\"update_id\":259894311,\n" +
                "\"message\":{\"message_id\":4,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282015,\"poll\":{\"id\":\"5834976086823272454\",\"question\":\"My test poll\",\"options\":[{\"text\":\"No option\",\"voter_count\":0},{\"text\":\"Yes option\",\"voter_count\":0}],\"total_voter_count\":0,\"is_closed\":false,\"is_anonymous\":true,\"type\":\"quiz\",\"allows_multiple_answers\":false}}},{\"update_id\":259894312,\n" +
                "\"message\":{\"message_id\":5,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282029,\"contact\":{\"phone_number\":\"618490765\",\"first_name\":\"FirstName\",\"last_name\":\"LASNAME\",\"vcard\":\"BEGIN:VCARD \\nVERSION:3.0\"}}},{\"update_id\":259894313,\n" +
                "\"message\":{\"message_id\":6,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282057,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822}}},{\"update_id\":259894314,\n" +
                "\"message\":{\"message_id\":7,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282070,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800}}},{\"update_id\":259894315,\n" +
                "\"edited_message\":{\"message_id\":7,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282070,\"edit_date\":1604282070,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894316,\n" +
                "\"edited_message\":{\"message_id\":7,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282070,\"edit_date\":1604282074,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800,\"heading\":102,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894317,\n" +
                "\"edited_message\":{\"message_id\":7,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282070,\"edit_date\":1604282077,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822}}},{\"update_id\":259894318,\n" +
                "\"message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800}}},{\"update_id\":259894319,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282102,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":111,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894320,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282106,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":105,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894321,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282110,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":83,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894322,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282114,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":95,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894323,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282118,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":99,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894324,\n" +
                "\"message\":{\"message_id\":9,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"last_name\":\"LastName\",\"username\":\"Username\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282124,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800}}},{\"update_id\":259894325,\n" +
                "\"message\":{\"message_id\":10,\"from\":{\"id\":12345678,\"is_bot\":true,\"first_name\":\"Group\",\"username\":\"GroupAnonymousBot\"},\"sender_chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282124,\"proximity_alert_triggered\":{\"traveler\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"last_name\":\"LastName\",\"username\":\"Username\"},\"watcher\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"distance\":0}}},{\"update_id\":259894326,\n" +
                "\"edited_message\":{\"message_id\":9,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"last_name\":\"LastName\",\"username\":\"Username\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282124,\"edit_date\":1604282124,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894327,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282126,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":104,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894328,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282128,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":100,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894329,\n" +
                "\"edited_message\":{\"message_id\":9,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"last_name\":\"LastName\",\"username\":\"Username\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282124,\"edit_date\":1604282128,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800,\"heading\":101,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894330,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282132,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":106,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894331,\n" +
                "\"edited_message\":{\"message_id\":9,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"last_name\":\"LastName\",\"username\":\"Username\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282124,\"edit_date\":1604282132,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":28800,\"heading\":106,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894332,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282136,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":102,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894333,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282140,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":106,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894334,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282144,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":99,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894335,\n" +
                "\"message\":{\"message_id\":11,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282147,\"text\":\"Hello\"}},{\"update_id\":259894336,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282148,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":98,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894337,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282152,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":111,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894338,\n" +
                "\"edited_message\":{\"message_id\":11,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282147,\"edit_date\":1604282153,\"text\":\"Hello, modified\"}},{\"update_id\":259894339,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282156,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":112,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894340,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282160,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":111,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894341,\n" +
                "\"message\":{\"message_id\":12,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282163,\"reply_to_message\":{\"message_id\":11,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282147,\"edit_date\":1604282153,\"text\":\"Hello, modified\"},\"text\":\"And the answer\"}},{\"update_id\":259894342,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282194,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":80,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894343,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282198,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":63,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894344,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282206,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":64,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894345,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282214,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":140,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894346,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282218,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":84,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894347,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282230,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":85,\"horizontal_accuracy\":65.000000}}},{\"update_id\":259894348,\n" +
                "\"edited_message\":{\"message_id\":8,\"from\":{\"id\":12345678,\"is_bot\":false,\"first_name\":\"FirstName\",\"username\":\"FirstName\",\"language_code\":\"en\"},\"chat\":{\"id\":-10011869112345,\"title\":\"My new test group\",\"type\":\"supergroup\"},\"date\":1604282102,\"edit_date\":1604282234,\"location\":{\"latitude\":0.004822,\"longitude\":-0.004822,\"live_period\":1800,\"heading\":85,\"horizontal_accuracy\":65.000000}}}]}";


        ArrayList<Update> response = new GetUpdates().deserializeResponse(updateText);
        assertEquals(47, response.size());

        JsonNode realArray = mapper.readTree(updateText).get("result");
        assertUpdates(response, realArray);
    }

    @Test
    void TestListUpdatesVoiceChat() throws Exception {
        String updateText = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"update_id\": 259939677,\n" +
                "            \"message\": {\n" +
                "                \"message_id\": 1,\n" +
                "                \"from\": {\n" +
                "                    \"id\": 1111111111111111,\n" +
                "                    \"is_bot\": false,\n" +
                "                    \"first_name\": \"TestUser\",\n" +
                "                    \"username\": \"TestUser\"\n" +
                "                },\n" +
                "                \"chat\": {\n" +
                "                    \"id\": -552721116,\n" +
                "                    \"title\": \"Random test\",\n" +
                "                    \"type\": \"group\",\n" +
                "                    \"all_members_are_administrators\": false\n" +
                "                },\n" +
                "                \"date\": 1615072605,\n" +
                "                \"group_chat_created\": true\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"update_id\": 259939678,\n" +
                "            \"message\": {\n" +
                "                \"message_id\": 2,\n" +
                "                \"from\": {\n" +
                "                    \"id\": 1111111111111111,\n" +
                "                    \"is_bot\": false,\n" +
                "                    \"first_name\": \"TestUser\",\n" +
                "                    \"username\": \"TestUser\"\n" +
                "                },\n" +
                "                \"chat\": {\n" +
                "                    \"id\": -552721116,\n" +
                "                    \"title\": \"Random test\",\n" +
                "                    \"type\": \"group\",\n" +
                "                    \"all_members_are_administrators\": false\n" +
                "                },\n" +
                "                \"date\": 1615072631,\n" +
                "                \"migrate_to_chat_id\": -1001308316775\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"update_id\": 259939679,\n" +
                "            \"message\": {\n" +
                "                \"message_id\": 2,\n" +
                "                \"from\": {\n" +
                "                    \"id\": 1111111111111111,\n" +
                "                    \"is_bot\": false,\n" +
                "                    \"first_name\": \"TestUser\",\n" +
                "                    \"username\": \"TestUser\"\n" +
                "                },\n" +
                "                \"chat\": {\n" +
                "                    \"id\": -1001308316775,\n" +
                "                    \"title\": \"Random test\",\n" +
                "                    \"type\": \"supergroup\"\n" +
                "                },\n" +
                "                \"date\": 1615072662,\n" +
                "                \"voice_chat_started\": {}\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"update_id\": 259939680,\n" +
                "            \"message\": {\n" +
                "                \"message_id\": 3,\n" +
                "                \"from\": {\n" +
                "                    \"id\": 1111111111111111,\n" +
                "                    \"is_bot\": false,\n" +
                "                    \"first_name\": \"TestUser\",\n" +
                "                    \"username\": \"TestUser\"\n" +
                "                },\n" +
                "                \"chat\": {\n" +
                "                    \"id\": -1001308316775,\n" +
                "                    \"title\": \"Random test\",\n" +
                "                    \"type\": \"supergroup\"\n" +
                "                },\n" +
                "                \"date\": 1615072671,\n" +
                "                \"voice_chat_participants_invited\": {\n" +
                "                    \"users\": [\n" +
                "                        {\n" +
                "                            \"id\": 222222222222222,\n" +
                "                            \"is_bot\": false,\n" +
                "                            \"first_name\": \"Test\",\n" +
                "                            \"last_name\": \"User\",\n" +
                "                            \"username\": \"TestUser\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"update_id\": 259939681,\n" +
                "            \"message\": {\n" +
                "                \"message_id\": 4,\n" +
                "                \"from\": {\n" +
                "                    \"id\": 1111111111111111,\n" +
                "                    \"is_bot\": false,\n" +
                "                    \"first_name\": \"TestUser\",\n" +
                "                    \"username\": \"TestUser\"\n" +
                "                },\n" +
                "                \"chat\": {\n" +
                "                    \"id\": -1001308316775,\n" +
                "                    \"title\": \"Random test\",\n" +
                "                    \"type\": \"supergroup\"\n" +
                "                },\n" +
                "                \"date\": 1615072919,\n" +
                "                \"voice_chat_ended\": {\n" +
                "                    \"duration\": 257\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        ArrayList<Update> response = new GetUpdates().deserializeResponse(updateText);
        assertEquals(5, response.size());

        JsonNode realArray = mapper.readTree(updateText).get("result");
        assertUpdates(response, realArray);
    }

    @Test
    void TestDeserializationCloseMethod() throws Exception {
        String updateText = "{\"ok\":true,\"result\": true}";

        Boolean response = new Close().deserializeResponse(updateText);

        assertTrue(response);
    }

    @Test
    void TestDeserializationChatMember() throws Exception {
        String updateText = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"user\": {\n" +
                "            \"id\": 1111111,\n" +
                "            \"is_bot\": true,\n" +
                "            \"first_name\": \"MyTesting\",\n" +
                "            \"username\": \"MyTestingUsername\"\n" +
                "        },\n" +
                "        \"status\": \"restricted\",\n" +
                "        \"until_date\": 0,\n" +
                "        \"can_send_messages\": false,\n" +
                "        \"can_send_media_messages\": false,\n" +
                "        \"can_send_polls\": false,\n" +
                "        \"can_send_other_messages\": false,\n" +
                "        \"can_add_web_page_previews\": false,\n" +
                "        \"can_change_info\": false,\n" +
                "        \"can_invite_users\": false,\n" +
                "        \"can_pin_messages\": false,\n" +
                "        \"is_member\": true\n" +
                "    }\n" +
                "}";

        ChatMember response = new GetChatMember().deserializeResponse(updateText);

        assertNotNull(response);
        assertNotNull(response.getUser());
        assertEquals(1111111, response.getUser().getId());
        assertTrue(response.getUser().getIsBot());
        assertEquals("MyTesting", response.getUser().getFirstName());
        assertEquals("MyTestingUsername", response.getUser().getUserName());
        assertEquals("restricted", response.getStatus());
        assertEquals(0, response.getUntilDate());
        assertFalse(response.getCanSendMessages());
        assertFalse(response.getCanSendMediaMessages());
        assertFalse(response.getCanSendPolls());
        assertFalse(response.getCanSendOtherMessages());
        assertFalse(response.getCanAddWebPagePreviews());
        assertFalse(response.getCanChangeInfo());
        assertFalse(response.getCanInviteUsers());
        assertFalse(response.getCanPinMessages());
        assertTrue(response.getIsMember());
    }

    @Test
    void TestDeserializationLogoutMethod() throws Exception {
        String updateText = "{\"ok\":true,\"result\": true}";

        Boolean response = new LogOut().deserializeResponse(updateText);

        assertTrue(response);
    }

    @Test
    void TestDeserializationGetMyCommandsMethod() throws Exception {
        String updateText = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"command\": \"enabled\",\n" +
                "            \"description\": \"Enabled Command\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"command\": \"disabled\",\n" +
                "            \"description\": \"Disabled Command\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        ArrayList<BotCommand> response = new GetMyCommands().deserializeResponse(updateText);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("enabled", response.get(0).getCommand());
        assertEquals("Enabled Command", response.get(0).getDescription());
        assertEquals("disabled", response.get(1).getCommand());
        assertEquals("Disabled Command", response.get(1).getDescription());
    }

    @Test
    void TestUpdateDeserialization() throws Exception {
        Update update = mapper.readValue(TelegramBotsHelper.GetUpdate(), Update.class);
        assertUpdate(update);
    }

    @Test
    void TestUpdateDeserializationWithInlineKeyboard() throws Exception {
        Update update = mapper.readValue(TelegramBotsHelper.GetUpdateWithMessageInCallbackQuery(), Update.class);
        assertNotNull(update);
        assertNotNull(update.getCallbackQuery());
        assertNotNull(update.getCallbackQuery().getMessage());
        assertNotNull(update.getCallbackQuery().getMessage().getReplyMarkup());
    }

    @Test
    void TestResponseWithoutErrorDeserialization() throws IOException {
        ApiResponse<ArrayList<Update>> result = mapper.readValue(TelegramBotsHelper.GetResponseWithoutError(), new TypeReference<ApiResponse<ArrayList<Update>>>(){});
        assertNotNull(result);
        assertTrue(result.getOk());
        assertEquals(1, result.getResult().size());
        assertUpdate(result.getResult().get(0));
    }

    @Test
    void TestResponseWithErrorDeserialization() throws IOException {
        ApiResponse<ArrayList<Update>> result = mapper.readValue(TelegramBotsHelper.GetResponseWithError(), new TypeReference<ApiResponse<ArrayList<Update>>>(){});
        assertNotNull(result);
        assertFalse(result.getOk());
        assertEquals(Integer.valueOf(400), result.getErrorCode());
        assertEquals("Error descriptions", result.getErrorDescription());
        assertNotNull(result.getParameters());
        assertEquals(Long.valueOf(12345), result.getParameters().getMigrateToChatId());
        assertEquals(Integer.valueOf(12), result.getParameters().getRetryAfter());
    }

    private void assertUpdate(Update update) {
        assertNotNull(update);
        assertEquals((Integer) 10000, update.getUpdateId());
        assertEditedMessage(update.getEditedMessage());
        assertCallbackQuery(update.getCallbackQuery());
        assertInlineQuery(update.getInlineQuery());
        assertChosenInlineQuery(update.getChosenInlineQuery());
        assertMessage(update.getMessage());
    }

    private void assertMessage(Message message) {
        assertNotNull(message);
        assertEquals(Integer.valueOf(1441645532), message.getDate());
        assertEquals(Integer.valueOf(1365), message.getMessageId());
        assertEquals(Integer.valueOf(1441645550), message.getForwardDate());
        assertEquals("Bold and italics", message.getText());
        assertPrivateChat(message.getChat());
        assertFromUser(message.getFrom());
        assertForwardFrom(message.getForwardFrom());
        assertReplyToMessage(message.getReplyToMessage());
        assertEntities(message.getEntities());
        assertAudio(message.getAudio());
        assertVoice(message.getVoice());
        assertDocument(message.getDocument());
    }

    private void assertDocument(Document document) {
        assertNotNull(document);
        assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", document.getFileId());
        assertEquals("Testfile.pdf", document.getFileName());
        assertEquals("application/pdf", document.getMimeType());
        assertEquals(Integer.valueOf(536392), document.getFileSize());
    }

    private void assertVoice(Voice voice) {
        assertNotNull(voice);
        assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", voice.getFileId());
        assertEquals(Integer.valueOf(5), voice.getDuration());
        assertEquals("audio/ogg", voice.getMimeType());
        assertEquals(Integer.valueOf(23000), voice.getFileSize());
    }

    private void assertAudio(Audio audio) {
        assertNotNull(audio);
        assertEquals("AwADBAADbXXXXXXXXXXXGBdhD2l6_XX", audio.getFileId());
        assertEquals(Integer.valueOf(243), audio.getDuration());
        assertEquals("audio/mpeg", audio.getMimeType());
        assertEquals(Integer.valueOf(3897500), audio.getFileSize());
        assertEquals("Testmusicfile", audio.getTitle());
    }

    private void assertEntities(List<MessageEntity> entities) {
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(EntityType.ITALIC, entities.get(0).getType());
        assertEquals(Integer.valueOf(9), entities.get(0).getOffset());
        assertEquals(Integer.valueOf(7), entities.get(0).getLength());
        assertEquals("italics", entities.get(0).getText());
        assertEquals(EntityType.BOLD, entities.get(1).getType());
        assertEquals(Integer.valueOf(0), entities.get(1).getOffset());
        assertEquals(Integer.valueOf(4), entities.get(1).getLength());
        assertEquals("Bold", entities.get(1).getText());
    }

    private void assertReplyToMessage(Message replyToMessage) {
        assertNotNull(replyToMessage);
        assertEquals(Integer.valueOf(1441645000), replyToMessage.getDate());
        assertEquals(Integer.valueOf(1334), replyToMessage.getMessageId());
        assertEquals("Original", replyToMessage.getText());
        assertNotNull(replyToMessage.getChat());
        assertEquals("ReplyLastname", replyToMessage.getChat().getLastName());
        assertEquals("ReplyFirstname", replyToMessage.getChat().getFirstName());
        assertEquals("Testusername", replyToMessage.getChat().getUserName());
        assertEquals(Long.valueOf(1111112), replyToMessage.getChat().getId());
    }

    private void assertForwardFrom(User forwardFrom) {
        assertNotNull(forwardFrom);
        assertEquals("ForwardLastname", forwardFrom.getLastName());
        assertEquals("ForwardFirstname", forwardFrom.getFirstName());
        assertEquals(Long.valueOf(222222), forwardFrom.getId());
    }

    private void assertPrivateChat(Chat chat) {
        assertNotNull(chat);
        assertEquals(Long.valueOf(1111111), chat.getId());
        assertTrue(chat.isUserChat());
        assertEquals("Test Lastname", chat.getLastName());
        assertEquals("Test Firstname", chat.getFirstName());
        assertEquals("Testusername", chat.getUserName());
    }

    private void assertChosenInlineQuery(ChosenInlineQuery chosenInlineQuery) {
        assertNotNull(chosenInlineQuery);
        assertEquals("12", chosenInlineQuery.getResultId());
        assertEquals("inline query", chosenInlineQuery.getQuery());
        assertEquals("1234csdbsk4839", chosenInlineQuery.getInlineMessageId());
        assertFromUser(chosenInlineQuery.getFrom());
    }

    private void assertInlineQuery(InlineQuery inlineQuery) {
        assertNotNull(inlineQuery);
        assertEquals("134567890097", inlineQuery.getId());
        assertEquals("inline query", inlineQuery.getQuery());
        assertEquals("offset", inlineQuery.getOffset());
        assertFromUser(inlineQuery.getFrom());
        assertNotNull(inlineQuery.getLocation());
        assertEquals(0.234242534, inlineQuery.getLocation().getLatitude());
        assertEquals(0.234242534, inlineQuery.getLocation().getLongitude());
    }

    private void assertCallbackQuery(CallbackQuery callbackQuery) {
        assertNotNull(callbackQuery);
        assertEquals("4382bfdwdsb323b2d9", callbackQuery.getId());
        assertEquals("Data from button callback", callbackQuery.getData());
        assertEquals("1234csdbsk4839", callbackQuery.getInlineMessageId());
        assertFromUser(callbackQuery.getFrom());
    }

    private void assertEditedMessage(Message message) {
        assertEquals((Integer) 1441645532, message.getDate());
        assertEquals((Integer) 1441646600, message.getEditDate());
        assertEquals((Integer) 1365, message.getMessageId());
        assertEquals("Edited text", message.getText());
        assertChannelChat(message.getChat());
        assertFromUser(message.getFrom());
    }

    private void assertFromUser(User from) {
        assertNotNull(from);
        assertEquals((Long) 1111111L, from.getId());
        assertEquals("Test Lastname", from.getLastName());
        assertEquals("Test Firstname", from.getFirstName());
        assertEquals("Testusername", from.getUserName());
    }

    private void assertChannelChat(Chat chat) {
        assertNotNull(chat);
        assertEquals(Long.valueOf(-10000000000L), chat.getId());
        assertTrue(chat.isChannelChat());
        assertEquals("Test channel", chat.getTitle());
    }
}