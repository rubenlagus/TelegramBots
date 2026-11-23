package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.serialization;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputContactMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputInvoiceMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputLocationMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputRichMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputSharedWidgetMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputVenueMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputWidgetMessageContent;

/** Resolves inline message content by the marker fields in its payload. */
public class InputMessageContentDeserializer extends StdDeserializer<InputMessageContent> {
    public InputMessageContentDeserializer() {
        super(InputMessageContent.class);
    }

    @Override
    public InputMessageContent deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = context.readTree(parser);

        if (node.has("message_text")) {
            return context.readTreeAsValue(node, InputTextMessageContent.class);
        }
        if (node.has("address")) {
            return context.readTreeAsValue(node, InputVenueMessageContent.class);
        }
        if (node.has("latitude")) {
            return context.readTreeAsValue(node, InputLocationMessageContent.class);
        }
        if (node.has("phone_number")) {
            return context.readTreeAsValue(node, InputContactMessageContent.class);
        }
        if (node.has("provider_token") || node.has("prices")) {
            return context.readTreeAsValue(node, InputInvoiceMessageContent.class);
        }
        if (node.has("rich_message")) {
            return context.readTreeAsValue(node, InputRichMessageContent.class);
        }
        if (node.has("widget_id")) {
            return context.readTreeAsValue(node, InputSharedWidgetMessageContent.class);
        }
        if (node.has("html")) {
            return context.readTreeAsValue(node, InputWidgetMessageContent.class);
        }
        throw new IllegalArgumentException("Unrecognized inline message content");
    }
}