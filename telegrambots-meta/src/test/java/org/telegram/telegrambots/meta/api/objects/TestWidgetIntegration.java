package org.telegram.telegrambots.meta.api.objects;

import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.updates.AllowedUpdates;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputSharedWidgetMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputWidgetMessageContent;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetQueryTypeData;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetQueryTypeSentTransaction;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetQueryTypeSignData;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.*;

class TestWidgetIntegration {
    private final JsonMapper mapper = new JsonMapper();
    private static final String FROM = "{\"id\":123,\"first_name\":\"A\",\"is_bot\":false}";

    @Test
    void queryVariantsAndUpdateFields() throws Exception {
        String[] types = {"{\"type\":\"data\"}",
                "{\"type\":\"sign_data\",\"wallet\":{\"address\":\"w\",\"amount\":\"5\"},\"sign_data\":\"{}\"}",
                "{\"type\":\"sent_transaction\",\"wallet\":{\"address\":\"w\",\"amount\":\"5\"},\"msg_hash\":\"hash\"}"};
        Class<?>[] classes = {WidgetQueryTypeData.class, WidgetQueryTypeSignData.class, WidgetQueryTypeSentTransaction.class};
        for (int i = 0; i < types.length; i++) {
            String json = "{\"update_id\":1,\"widget_query\":{\"id\":\"q\",\"from\":" + FROM
                    + ",\"chat_instance\":\"i\",\"type\":" + types[i] + ",\"data\":\"x\"}}";
            Update update = mapper.readValue(json, Update.class);
            assertTrue(update.hasWidgetQuery());
            assertInstanceOf(classes[i], update.getWidgetQuery().getType());
            assertInstanceOf(classes[i], mapper.readValue(mapper.writeValueAsString(update), Update.class).getWidgetQuery().getType());
        }
        Update shared = mapper.readValue("{\"update_id\":1,\"shared_widget_query\":{\"id\":\"q\",\"from\":" + FROM
                + ",\"type\":{\"type\":\"data\"},\"data\":\"x\",\"widget_id\":\"w\"}}", Update.class);
        assertTrue(shared.hasSharedWidgetQuery());
        assertEquals("widget_query", AllowedUpdates.WIDGETQUERY);
        assertEquals("shared_widget_query", AllowedUpdates.SHAREDWIDGETQUERY);
    }

    @Test
    void widgetInMessageAndExternalReply() throws Exception {
        Message message = mapper.readValue("{\"message_id\":1,\"date\":1,\"chat\":{\"id\":1,\"type\":\"private\"},"
                + "\"widget\":{\"html\":\"<p>hello</p>\"},\"external_reply\":{\"widget\":{\"html\":\"<p>other</p>\"}}}", Message.class);
        assertTrue(message.hasWidget());
        assertEquals("<p>other</p>", message.getExternalReplyInfo().getWidget().getHtml());
        assertTrue(mapper.readValue(mapper.writeValueAsString(message), Message.class).hasWidget());
    }

    @Test
    void inlineContentsDispatchAndValidate() throws Exception {
        InputMessageContent widget = mapper.readValue("{\"html\":\"<p>test</p>\",\"public_data\":\"a\"}", InputMessageContent.class);
        assertInstanceOf(InputWidgetMessageContent.class, widget);
        widget.validate();
        assertInstanceOf(InputWidgetMessageContent.class, mapper.readValue(mapper.writeValueAsString(widget), InputMessageContent.class));
        InputMessageContent shared = mapper.readValue("{\"widget_id\":\"w\",\"chat_data\":\"a\"}", InputMessageContent.class);
        assertInstanceOf(InputSharedWidgetMessageContent.class, shared);
        shared.validate();
        assertInstanceOf(InputSharedWidgetMessageContent.class, mapper.readValue(mapper.writeValueAsString(shared), InputMessageContent.class));
        assertThrows(TelegramApiValidationException.class, () -> new InputWidgetMessageContent("").validate());
        assertThrows(TelegramApiValidationException.class, () -> new InputSharedWidgetMessageContent("").validate());
        assertThrows(TelegramApiValidationException.class, () -> InputWidgetMessageContent.builder().html("é".repeat(16385)).build().validate());
        InputWidgetMessageContent.builder().html("ok").publicData("é".repeat(512)).build().validate();
        assertThrows(TelegramApiValidationException.class, () -> InputWidgetMessageContent.builder().html("ok").publicData("é".repeat(513)).build().validate());
        assertThrows(TelegramApiValidationException.class, () -> InputSharedWidgetMessageContent.builder().widgetId("w").chatData("é".repeat(513)).build().validate());
    }
}