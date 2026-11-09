package org.telegram.telegrambots.meta.api.objects;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendSharedWidget;
import org.telegram.telegrambots.meta.api.methods.send.SendWidget;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageWidgetData;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditSharedWidgetData;
import org.telegram.telegrambots.meta.api.methods.widget.AnswerWidgetQuery;
import org.telegram.telegrambots.meta.api.objects.widget.*;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.*;

public class TestBotApi104Widgets {
    private final JsonMapper mapper = new JsonMapper();
    private static final String USER = "{\"id\":1,\"is_bot\":false,\"first_name\":\"A\"}";
    private static final String WALLET = "{\"address\":\"addr\",\"amount\":\"123456789012345678901\","
            + "\"public_key\":\"key\",\"state_init\":\"state\"}";

    @Test
    public void widgetAndQueryVariantsRoundTrip() throws Exception {
        String widget = "{\"id\":\"widget\",\"width\":100,\"height\":900,\"html\":\"<p>é</p>\","
                + "\"public_data\":\"public\",\"chat_data\":\"chat\"}";
        assertEquals(mapper.readTree(widget), mapper.valueToTree(mapper.readValue(widget, Widget.class)));

        String[] types = {
                "{\"type\":\"data\",\"wallet_card\":{\"address\":\"a\",\"amount\":\"9\",\"stickers\":[]}}",
                "{\"type\":\"sign_data\",\"wallet\":" + WALLET + ",\"sign_data\":\"{}\"}",
                "{\"type\":\"sent_transaction\",\"wallet\":" + WALLET + ",\"msg_hash\":\"hash\"}"
        };
        Class<?>[] classes = {WidgetQueryTypeData.class, WidgetQueryTypeSignData.class, WidgetQueryTypeSentTransaction.class};
        for (int i = 0; i < types.length; i++) {
            WidgetQueryType type = mapper.readValue(types[i], WidgetQueryType.class);
            assertInstanceOf(classes[i], type);
            assertEquals(mapper.readTree(types[i]), mapper.valueToTree(type));
            String query = "{\"id\":\"q\",\"from\":" + USER + ",\"chat_instance\":\"ci\","
                    + "\"inline_message_id\":\"inline\",\"chat_type\":\"sender\",\"type\":" + types[i] + ",\"data\":\"x\"}";
            WidgetQuery parsed = mapper.readValue(query, WidgetQuery.class);
            assertInstanceOf(classes[i], parsed.getType());
            assertEquals(mapper.readTree(query), mapper.readTree(mapper.writeValueAsString(parsed)));
            String shared = "{\"id\":\"q\",\"from\":" + USER + ",\"type\":" + types[i]
                    + ",\"data\":\"x\",\"widget_id\":\"w\"}";
            SharedWidgetQuery parsedShared = mapper.readValue(shared, SharedWidgetQuery.class);
            assertInstanceOf(classes[i], parsedShared.getType());
            assertEquals(mapper.readTree(shared), mapper.readTree(mapper.writeValueAsString(parsedShared)));
        }
        assertNull(mapper.readValue("{\"type\":\"future\"}", WidgetQueryType.class));
        assertEquals("123456789012345678901", mapper.readValue(WALLET, WidgetQueryWallet.class).getAmount());
    }

    @Test
    public void inlineWidgetContentsRoundTripViaInterface() throws Exception {
        String[] contents = {
                "{\"html\":\"<p>hello</p>\"}",
                "{\"widget_id\":\"w\"}",
                "{\"chat_data\":\"local\",\"caption\":\"caption\",\"html\":\"<p>é</p>\","
                        + "\"width\":100,\"height\":200,\"public_data\":\"public\",\"parse_mode\":\"HTML\","
                        + "\"show_caption_above_media\":true,\"allow_queries\":true,\"is_shared\":false}",
                "{\"chat_data\":\"local\",\"caption\":\"caption\",\"widget_id\":\"w\","
                        + "\"caption_entities\":[{\"type\":\"bold\",\"offset\":0,\"length\":1}],"
                        + "\"show_caption_above_media\":false}"
        };
        Class<?>[] classes = {InputWidgetMessageContent.class, InputSharedWidgetMessageContent.class,
                InputWidgetMessageContent.class, InputSharedWidgetMessageContent.class};
        for (int i = 0; i < contents.length; i++) {
            InputMessageContent content = mapper.readValue(contents[i], InputMessageContent.class);
            assertInstanceOf(classes[i], content);
            content.validate();
            String serialized = mapper.writeValueAsString(content);
            assertEquals(mapper.readTree(contents[i]), mapper.readTree(serialized));
            assertEquals(content, mapper.readValue(serialized, InputMessageContent.class));
        }
        assertThrows(Exception.class, () -> mapper.readValue("{\"chat_data\":\"local\"}", InputMessageContent.class));
    }

    @Test
    public void existingInlineContentsStillDeduceTheirTypes() throws Exception {
        String[] contents = {
                "{\"message_text\":\"hello\"}",
                "{\"latitude\":1.0,\"longitude\":2.0,\"live_period\":60}",
                "{\"latitude\":1.0,\"longitude\":2.0,\"title\":\"venue\",\"address\":\"address\"}",
                "{\"phone_number\":\"123\",\"first_name\":\"A\"}",
                "{\"title\":\"item\",\"description\":\"description\",\"payload\":\"payload\","
                        + "\"currency\":\"XTR\",\"prices\":[{\"label\":\"item\",\"amount\":1}]}",
                "{\"rich_message\":{\"html\":\"<p>hello</p>\"}}"
        };
        Class<?>[] classes = {InputTextMessageContent.class, InputLocationMessageContent.class,
                InputVenueMessageContent.class, InputContactMessageContent.class,
                InputInvoiceMessageContent.class, InputRichMessageContent.class};
        for (int i = 0; i < contents.length; i++) {
            InputMessageContent content = mapper.readValue(contents[i], InputMessageContent.class);
            assertInstanceOf(classes[i], content);
            assertEquals(content, mapper.readValue(mapper.writeValueAsString(content), InputMessageContent.class));
        }
    }

    @Test
    public void requestsSerializeAndDeserializeResponses() throws Exception {
        SendWidget send = SendWidget.builder().chatId(123L).html("<b>hi</b>").width(1).height(9999)
                .publicData("").chatData("local").allowQueries(true).isShared(true)
                .showCaptionAboveMedia(true).protectContent(false).build();
        send.validate();
        assertEquals("sendWidget", send.getMethod());
        JsonNode json = mapper.valueToTree(send);
        assertEquals("123", json.get("chat_id").asText());
        assertEquals("", json.get("public_data").asText());
        assertTrue(json.get("is_shared").asBoolean());
        assertFalse(json.get("protect_content").asBoolean());
        assertEquals(send.getHtml(), mapper.readValue(mapper.writeValueAsString(send), SendWidget.class).getHtml());

        SendSharedWidget shared = SendSharedWidget.builder().chatId("@group").widgetId("w").chatData("").build();
        shared.validate();
        assertEquals("sendSharedWidget", shared.getMethod());
        assertEquals("", mapper.valueToTree(shared).get("chat_data").asText());
        String response = "{\"ok\":true,\"result\":{\"message_id\":7,\"date\":1,\"chat\":{\"id\":1,\"type\":\"private\"}}}";
        assertEquals(7, send.deserializeResponse(response).getMessageId());
        assertEquals(7, shared.deserializeResponse(response).getMessageId());
        assertThrows(TelegramApiRequestException.class, () -> send.deserializeResponse("{\"ok\":false,\"error_code\":400,\"description\":\"bad\"}"));

        AnswerWidgetQuery answer = AnswerWidgetQuery.builder().widgetQueryId("q").data("").build();
        EditMessageWidgetData edit = EditMessageWidgetData.builder().chatId(123L).messageId(7).publicData("").build();
        EditSharedWidgetData editShared = EditSharedWidgetData.builder().widgetId("w").publicData("").cacheTime(5).build();
        answer.validate();
        edit.validate();
        editShared.validate();
        assertEquals("answerWidgetQuery", answer.getMethod());
        assertEquals("editMessageWidgetData", edit.getMethod());
        assertEquals("editSharedWidgetData", editShared.getMethod());
        assertEquals("", mapper.valueToTree(edit).get("public_data").asText());
        assertFalse(mapper.valueToTree(edit).has("chat_data"));
        assertTrue(answer.deserializeResponse("{\"ok\":true,\"result\":true}"));
        assertTrue(edit.deserializeResponse("{\"ok\":true,\"result\":true}"));
        assertTrue(editShared.deserializeResponse("{\"ok\":true,\"result\":true}"));
        assertThrows(TelegramApiRequestException.class, () -> editShared.deserializeResponse("{\"ok\":false,\"error_code\":400,\"description\":\"bad\"}"));
    }

    @Test
    public void dataUtf8BoundariesAndRequestValidation() throws Exception {
        SendWidget send = SendWidget.builder().chatId("1").html("a").publicData("é".repeat(512)).build();
        send.validate();
        send.setPublicData("é".repeat(512) + "a");
        assertThrows(TelegramApiValidationException.class, send::validate);
        Widget context = Widget.builder().html("x").build();
        WidgetValidations.validateOptionalData(null, "data", context);
        WidgetValidations.validateOptionalData("", "data", context);
        WidgetValidations.validateOptionalData("😀".repeat(256), "data", context);
        assertThrows(TelegramApiValidationException.class,
                () -> WidgetValidations.validateOptionalData("😀".repeat(256) + "a", "data", context));
        assertThrows(TelegramApiValidationException.class, () -> SendSharedWidget.builder().chatId("1").widgetId("").build().validate());
        assertThrows(TelegramApiValidationException.class, () -> SendSharedWidget.builder().chatId("1").widgetId("w").chatData("x".repeat(1025)).build().validate());
        MessageEntity entity = MessageEntity.builder().type("bold").offset(0).length(1).build();
        assertThrows(TelegramApiValidationException.class, () -> SendWidget.builder().chatId("1").html("a")
                .parseMode("HTML").captionEntities(List.of(entity)).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> SendSharedWidget.builder().chatId("1").widgetId("w")
                .parseMode("HTML").captionEntities(List.of(entity)).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> AnswerWidgetQuery.builder().widgetQueryId("q").data("é".repeat(513)).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> AnswerWidgetQuery.builder().widgetQueryId("").build().validate());
        assertThrows(TelegramApiValidationException.class, () -> EditSharedWidgetData.builder().widgetId("w").publicData("x").cacheTime(4).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> EditSharedWidgetData.builder().widgetId("w").publicData("x").cacheTime(3601).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> EditSharedWidgetData.builder().widgetId("w").publicData("x".repeat(1025)).build().validate());
        assertDoesNotThrow(() -> EditSharedWidgetData.builder().widgetId("w").publicData("").cacheTime(3600).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> EditMessageWidgetData.builder().build().validate());
        assertThrows(TelegramApiValidationException.class, () -> EditMessageWidgetData.builder().chatId("1").inlineMessageId("i").build().validate());
        assertThrows(TelegramApiValidationException.class, () -> EditMessageWidgetData.builder().inlineMessageId("i").chatData("x".repeat(1025)).build().validate());
        assertDoesNotThrow(() -> EditMessageWidgetData.builder().inlineMessageId("i").chatData("").build().validate());
    }

    @Test
    public void widgetCaptionLimitsUseParsedTextLength() throws Exception {
        SendWidget maxWidgetCaption = SendWidget.builder().chatId("1").html("<b>h</b>")
                .caption("a".repeat(1024)).build();
        maxWidgetCaption.validate();
        assertThrows(TelegramApiValidationException.class, () -> SendWidget.builder().chatId("1").html("h")
                .caption("a".repeat(1025)).build().validate());

        SendSharedWidget maxSharedCaption = SendSharedWidget.builder().chatId("1").widgetId("w")
                .caption("a".repeat(1024)).build();
        maxSharedCaption.validate();
        assertThrows(TelegramApiValidationException.class, () -> SendSharedWidget.builder().chatId("1").widgetId("w")
                .caption("a".repeat(1025)).build().validate());

        assertDoesNotThrow(() -> SendWidget.builder().chatId("1").html("h").parseMode("HTML")
                .caption("<b>" + "a".repeat(1024) + "</b>").build().validate());
        InputWidgetMessageContent inlineWidget = InputWidgetMessageContent.builder().html("h")
                .caption("a".repeat(1024)).build();
        inlineWidget.validate();
        assertThrows(TelegramApiValidationException.class, () -> InputWidgetMessageContent.builder().html("h")
                .caption("a".repeat(1025)).build().validate());
        InputSharedWidgetMessageContent inlineShared = InputSharedWidgetMessageContent.builder().widgetId("w")
                .caption("a".repeat(1024)).build();
        inlineShared.validate();
        assertThrows(TelegramApiValidationException.class, () -> InputSharedWidgetMessageContent.builder().widgetId("w")
                .caption("a".repeat(1025)).build().validate());
    }

    @Test
    public void htmlUtf8AndZlibBoundaries() throws Exception {
        Widget context = Widget.builder().html("x").build();
        assertThrows(TelegramApiValidationException.class, () -> WidgetValidations.validateHtml(null, context));
        assertThrows(TelegramApiValidationException.class, () -> WidgetValidations.validateHtml("", context));
        WidgetValidations.validateHtml("é".repeat(16384), context);
        assertThrows(TelegramApiValidationException.class, () -> WidgetValidations.validateHtml("é".repeat(16384) + "a", context));
        String atLimit = findCompressedSize(4096);
        String aboveLimit = findCompressedSize(4097);
        assertEquals(4096, zlibSize(atLimit));
        assertEquals(4097, zlibSize(aboveLimit));
        // A zlib stream at the limit is larger with the gzip header and trailer.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(bytes) {{ def.setLevel(Deflater.BEST_COMPRESSION); }}) {
            gzip.write(atLimit.getBytes(StandardCharsets.UTF_8));
        }
        assertTrue(bytes.size() > 4096);
        WidgetValidations.validateHtml(atLimit, context);
        assertThrows(TelegramApiValidationException.class, () -> WidgetValidations.validateHtml(aboveLimit, context));
        assertDoesNotThrow(() -> SendWidget.builder().chatId("1").html(atLimit).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> SendWidget.builder().chatId("1").html(aboveLimit).build().validate());
    }

    private static String findCompressedSize(int target) {
        for (int seed = 0; seed < 16; seed++) {
            Random random = new Random(seed);
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < 6000; i++) content.append((char) ('!' + random.nextInt(90)));
            for (int length = 4500; length <= 6000; length++) {
                String candidate = content.substring(0, length);
                int size = zlibSize(candidate);
                if (size == target) return candidate;
                if (size > target + 20) break;
            }
        }
        throw new AssertionError("No fixture found with zlib size " + target);
    }

    private static int zlibSize(String text) {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        try {
            deflater.setInput(text.getBytes(StandardCharsets.UTF_8));
            deflater.finish();
            byte[] buffer = new byte[8192];
            int size = 0;
            while (!deflater.finished()) size += deflater.deflate(buffer);
            return size;
        } finally {
            deflater.end();
        }
    }
}