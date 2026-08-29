package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Deserializes a Telegram Bot API {@link RichText} value, which can arrive as:
 * <ul>
 *   <li>A JSON string → {@link RichTextPlain}</li>
 *   <li>A JSON array  → {@link RichTextConcat}</li>
 *   <li>A JSON object with a {@code "type"} field → one of the typed subtypes</li>
 *   <li>An object with an unknown type → {@code null} (safe fallback)</li>
 * </ul>
 */
public class RichTextDeserializer extends StdDeserializer<RichText> {

    private static final Map<String, Class<? extends RichText>> TYPE_MAP = Map.ofEntries(
            Map.entry(RichTextBold.TYPE, RichTextBold.class),
            Map.entry(RichTextItalic.TYPE, RichTextItalic.class),
            Map.entry(RichTextUnderline.TYPE, RichTextUnderline.class),
            Map.entry(RichTextStrikethrough.TYPE, RichTextStrikethrough.class),
            Map.entry(RichTextSpoiler.TYPE, RichTextSpoiler.class),
            Map.entry(RichTextDateTime.TYPE, RichTextDateTime.class),
            Map.entry(RichTextTextMention.TYPE, RichTextTextMention.class),
            Map.entry(RichTextSubscript.TYPE, RichTextSubscript.class),
            Map.entry(RichTextSuperscript.TYPE, RichTextSuperscript.class),
            Map.entry(RichTextMarked.TYPE, RichTextMarked.class),
            Map.entry(RichTextCode.TYPE, RichTextCode.class),
            Map.entry(RichTextCustomEmoji.TYPE, RichTextCustomEmoji.class),
            Map.entry(RichTextMathematicalExpression.TYPE, RichTextMathematicalExpression.class),
            Map.entry(RichTextUrl.TYPE, RichTextUrl.class),
            Map.entry(RichTextEmailAddress.TYPE, RichTextEmailAddress.class),
            Map.entry(RichTextPhoneNumber.TYPE, RichTextPhoneNumber.class),
            Map.entry(RichTextBankCardNumber.TYPE, RichTextBankCardNumber.class),
            Map.entry(RichTextMention.TYPE, RichTextMention.class),
            Map.entry(RichTextHashtag.TYPE, RichTextHashtag.class),
            Map.entry(RichTextCashtag.TYPE, RichTextCashtag.class),
            Map.entry(RichTextBotCommand.TYPE, RichTextBotCommand.class),
            Map.entry(RichTextAnchor.TYPE, RichTextAnchor.class),
            Map.entry(RichTextAnchorLink.TYPE, RichTextAnchorLink.class),
            Map.entry(RichTextReference.TYPE, RichTextReference.class),
            Map.entry(RichTextReferenceLink.TYPE, RichTextReferenceLink.class),
            Map.entry(RichTextPlain.TYPE, RichTextPlain.class),
            Map.entry(RichTextButton.TYPE, RichTextButton.class)
    );

    public RichTextDeserializer() {
        super(RichText.class);
    }

    /**
     * When {@link com.fasterxml.jackson.annotation.JsonTypeInfo} is present on the interface,
     * Jackson wraps us in a {@code TypeWrappedDeserializer} and calls this method instead of
     * {@link #deserialize}. We bypass the type-info machinery entirely — our {@link #deserialize}
     * already handles every wire form (string, array, typed object).
     */
    @Override
    public RichText deserializeWithType(JsonParser p, DeserializationContext ctx,
                                        TypeDeserializer typeDeserializer) throws IOException {
        return deserialize(p, ctx);
    }

    @Override
    public RichText deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonToken token = p.currentToken();

        if (token == JsonToken.VALUE_STRING) {
            return RichTextPlain.builder().text(p.getText()).build();
        }

        if (token == JsonToken.START_ARRAY) {
            List<RichText> items = new ArrayList<>();
            while (p.nextToken() != JsonToken.END_ARRAY) {
                items.add(deserialize(p, ctx));
            }
            return RichTextConcat.builder().texts(items).build();
        }

        // JSON object: read the whole node, look up the concrete subtype by "type" field.
        JsonNode node = p.readValueAsTree();
        String type = node.path("type").asText(null);
        Class<? extends RichText> cls = type != null ? TYPE_MAP.get(type) : null;
        if (cls == null) {
            // Unknown or missing type — safe fallback, do not crash the whole update.
            return null;
        }
        // Deserialize as the concrete class directly, avoiding recursion back into this deserializer.
        return ctx.readTreeAsValue(node, cls);
    }
}
