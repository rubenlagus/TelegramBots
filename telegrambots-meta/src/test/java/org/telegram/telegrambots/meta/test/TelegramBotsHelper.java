package org.telegram.telegrambots.meta.test;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public final class TelegramBotsHelper {
    private TelegramBotsHelper() {
    }

    public static String GetUpdate() {
        return "{\"update_id\": 10000,\"message\": {\"date\": 1441645532,\"chat\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"type\": \"private\",\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"message_id\": 1365,\"from\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"forward_from\": {\"last_name\": \"ForwardLastname\",\"id\": 222222,\"first_name\": \"ForwardFirstname\"},\"forward_date\": 1441645550,\"reply_to_message\": {\"date\": 1441645000,\"chat\": {\"last_name\": \"ReplyLastname\",\"type\": \"private\",\"id\": 1111112,\"first_name\": \"ReplyFirstname\",\"username\": \"Testusername\"},\"message_id\": 1334,\"text\": \"Original\"},\"text\": \"Bold and italics\",\"entities\": [{\"type\": \"italic\",\"offset\": 9,\"length\": 7},{\"type\": \"bold\",\"offset\": 0,\"length\": 4}],\"audio\": {\"file_id\": \"AwADBAADbXXXXXXXXXXXGBdhD2l6_XX\",\"duration\": 243,\"mime_type\": \"audio/mpeg\",\"file_size\": 3897500,\"title\": \"Testmusicfile\"},\"voice\": {\"file_id\": \"AwADBAADbXXXXXXXXXXXGBdhD2l6_XX\",\"duration\": 5,\"mime_type\": \"audio/ogg\",\"file_size\": 23000},\"document\": {\"file_id\": \"AwADBAADbXXXXXXXXXXXGBdhD2l6_XX\",\"file_name\": \"Testfile.pdf\",\"mime_type\": \"application/pdf\",\"file_size\": 536392}},\"edited_message\": {\"date\": 1441645532,\"chat\": {\"id\": -10000000000,\"type\": \"channel\",\"title\": \"Test channel\"},\"message_id\": 1365,\"from\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"text\": \"Edited text\",\"edit_date\": 1441646600},\"inline_query\": {\"id\": \"134567890097\",\"from\": {\"last_name\": \"Test Lastname\",\"type\": \"private\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"query\": \"inline query\",\"offset\": \"offset\",\"location\": {\"longitude\": 0.234242534,\"latitude\": 0.234242534}},\"chosen_inline_result\": {\"result_id\": \"12\",\"from\": {\"last_name\": \"Test Lastname\",\"type\": \"private\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"query\": \"inline query\",\"inline_message_id\": \"1234csdbsk4839\"},\"callback_query\": {\"id\": \"4382bfdwdsb323b2d9\",\"from\": {\"last_name\": \"Test Lastname\",\"type\": \"private\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"data\": \"Data from button callback\",\"inline_message_id\": \"1234csdbsk4839\"}\n}";
    }

    public static String GetUpdateWithMessageInCallbackQuery() {
        return  "      {\n" +
                "         \"update_id\":10000,\n" +
                "         \"callback_query\":{\n" +
                "            \"id\":\"4382bfdwdsb323b2d9\",\n" +
                "            \"from\":{\n" +
                "               \"id\":1,\n" +
                "               \"is_bot\":false,\n" +
                "               \"first_name\":\"Name\",\n" +
                "               \"last_name\":\"Surname\",\n" +
                "               \"username\":\"Username\",\n" +
                "               \"language_code\":\"en\"\n" +
                "            },\n" +
                "            \"message\":{\n" +
                "               \"message_id\":3572,\n" +
                "               \"from\":{\n" +
                "                  \"id\":1,\n" +
                "                  \"is_bot\":true,\n" +
                "                  \"first_name\":\"Name\",\n" +
                "                  \"username\":\"Surname\"\n" +
                "               },\n" +
                "               \"chat\":{\n" +
                "                  \"id\":2,\n" +
                "                  \"first_name\":\"Name\",\n" +
                "                  \"last_name\":\"Surname\",\n" +
                "                  \"username\":\"Username\",\n" +
                "                  \"type\":\"private\"\n" +
                "               },\n" +
                "               \"date\":1559995844,\n" +
                "               \"text\":\"Font size: 16\\nFont: Arial-Black\\nText Color: 000000\\nBackground: FFFFFF\",\n" +
                "               \"entities\":[\n" +
                "                  {\n" +
                "                     \"offset\":0,\n" +
                "                     \"length\":11,\n" +
                "                     \"type\":\"bold\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":11,\n" +
                "                     \"length\":2,\n" +
                "                     \"type\":\"code\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":14,\n" +
                "                     \"length\":6,\n" +
                "                     \"type\":\"bold\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":20,\n" +
                "                     \"length\":11,\n" +
                "                     \"type\":\"code\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":32,\n" +
                "                     \"length\":12,\n" +
                "                     \"type\":\"bold\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":44,\n" +
                "                     \"length\":6,\n" +
                "                     \"type\":\"code\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":51,\n" +
                "                     \"length\":12,\n" +
                "                     \"type\":\"bold\"\n" +
                "                  },\n" +
                "                  {\n" +
                "                     \"offset\":63,\n" +
                "                     \"length\":6,\n" +
                "                     \"type\":\"code\"\n" +
                "                  }\n" +
                "               ],\n" +
                "               \"reply_markup\":{\n" +
                "                  \"inline_keyboard\":[\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"-=Font Size=-\",\n" +
                "                           \"callback_data\":\"*\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"-\",\n" +
                "                           \"callback_data\":\"IntValue;fontSize;Font Size;-1\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"text\":\"16\",\n" +
                "                           \"callback_data\":\"*\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"text\":\"+\",\n" +
                "                           \"callback_data\":\"IntValue;fontSize;Font Size;+1\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"-=Font Name=-\",\n" +
                "                           \"callback_data\":\"*\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Cochin\",\n" +
                "                           \"callback_data\":\"StringValue;font;Font Name;Cochin\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"text\":\"Zapfino\",\n" +
                "                           \"callback_data\":\"StringValue;font;Font Name;Zapfino\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                           \"text\":\"Herculanum\",\n" +
                "                           \"callback_data\":\"StringValue;font;Font Name;Herculanum\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"-=Font Color=-\",\n" +
                "                           \"callback_data\":\"*\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Pumpkin\",\n" +
                "                           \"callback_data\":\"StringValue;fgColor;Font Color;Pumpkin\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Emerald\",\n" +
                "                           \"callback_data\":\"StringValue;fgColor;Font Color;Emerald\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Midnight blue\",\n" +
                "                           \"callback_data\":\"StringValue;fgColor;Font Color;Midnight blue\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Orange\",\n" +
                "                           \"callback_data\":\"StringValue;fgColor;Font Color;Orange\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Amethyst\",\n" +
                "                           \"callback_data\":\"StringValue;fgColor;Font Color;Amethyst\"\n" +
                "                        }\n" +
                "                     ],\n" +
                "                     [\n" +
                "                        {\n" +
                "                           \"text\":\"Pomegranate\",\n" +
                "                           \"callback_data\":\"StringValue;fgColor;Font Color;Pomegranate\"\n" +
                "                        }\n" +
                "                     ]\n" +
                "                  ]\n" +
                "               }\n" +
                "            },\n" +
                "            \"chat_instance\":\"12345678901234567890\",\n" +
                "            \"data\":\"*\"\n" +
                "         }\n" +
                "      }";
    }

    public static String GetResponseWithoutError() {
        return "{\"ok\": true,\"result\": [{\"update_id\": 10000,\"message\": {\"date\": 1441645532,\"chat\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"type\": \"private\",\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"message_id\": 1365,\"from\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"forward_from\": {\"last_name\": \"ForwardLastname\",\"id\": 222222,\"first_name\": \"ForwardFirstname\"},\"forward_date\": 1441645550,\"reply_to_message\": {\"date\": 1441645000,\"chat\": {\"last_name\": \"ReplyLastname\",\"type\": \"private\",\"id\": 1111112,\"first_name\": \"ReplyFirstname\",\"username\": \"Testusername\"},\"message_id\": 1334,\"text\": \"Original\"},\"text\": \"Bold and italics\",\"entities\": [{\"type\": \"italic\",\"offset\": 9,\"length\": 7},{\"type\": \"bold\",\"offset\": 0,\"length\": 4}],\"audio\": {\"file_id\": \"AwADBAADbXXXXXXXXXXXGBdhD2l6_XX\",\"duration\": 243,\"mime_type\": \"audio/mpeg\",\"file_size\": 3897500,\"title\": \"Testmusicfile\"},\"voice\": {\"file_id\": \"AwADBAADbXXXXXXXXXXXGBdhD2l6_XX\",\"duration\": 5,\"mime_type\": \"audio/ogg\",\"file_size\": 23000},\"document\": {\"file_id\": \"AwADBAADbXXXXXXXXXXXGBdhD2l6_XX\",\"file_name\": \"Testfile.pdf\",\"mime_type\": \"application/pdf\",\"file_size\": 536392}},\"edited_message\": {\"date\": 1441645532,\"chat\": {\"id\": -10000000000,\"type\": \"channel\",\"title\": \"Test channel\"},\"message_id\": 1365,\"from\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"text\": \"Edited text\",\"edit_date\": 1441646600},\"inline_query\": {\"id\": \"134567890097\",\"from\": {\"last_name\": \"Test Lastname\",\"type\": \"private\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"query\": \"inline query\",\"offset\": \"offset\",\"location\": {\"longitude\": 0.234242534,\"latitude\": 0.234242534}},\"chosen_inline_result\": {\"result_id\": \"12\",\"from\": {\"last_name\": \"Test Lastname\",\"type\": \"private\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"query\": \"inline query\",\"inline_message_id\": \"1234csdbsk4839\"},\"callback_query\": {\"id\": \"4382bfdwdsb323b2d9\",\"from\": {\"last_name\": \"Test Lastname\",\"type\": \"private\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"data\": \"Data from button callback\",\"inline_message_id\": \"1234csdbsk4839\"}}]\n}";
    }

    public static String GetResponseWithError() {
        return "{\"ok\": false,\"error_code\": 400,\"description\": \"Error descriptions\",\"parameters\": {\"migrate_to_chat_id\": 12345,\"retry_after\": 12}}";
    }

    public static String getResponseWithError409() {
        return "{\"ok\": false,\"error_code\": 409,\"description\": \"Conflict: terminated by other getUpdates request; make sure that only one bot instance is running\"}";
    }

    public static String GetSetGameScoreBooleanResponse() {
        return "{\"ok\": true,\"result\": true}";
    }

    public static String GetSetGameScoreMessageResponse() {
        return "{\"ok\": true,\"result\": {\"date\": 1441645532,\"chat\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"type\": \"private\",\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"message_id\": 1365,\"from\": {\"last_name\": \"Test Lastname\",\"id\": 1111111,\"first_name\": \"Test Firstname\",\"username\": \"Testusername\"},\"text\": \"Original\"}}";
    }
}
