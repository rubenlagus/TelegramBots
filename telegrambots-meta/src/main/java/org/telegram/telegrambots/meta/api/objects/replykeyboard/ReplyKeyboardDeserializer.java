package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;

/**
 * Resolves a {@link ReplyKeyboard} by the marker key present in the payload.
 *
 * <p>The Bot API sends no type discriminator for reply markup, and {@code Id.DEDUCTION} cannot be
 * used: since Bot API 10.3 both {@link ForceReplyKeyboard} and {@link ReplyKeyboardMarkup} carry
 * {@code force_reply}, which makes the former's property set a subset of the latter's and the
 * deduction ambiguous. Marker keys are checked most-specific first.
 *
 * @author Ruben Bermudez
 * @version 10.3
 */
public class ReplyKeyboardDeserializer extends StdDeserializer<ReplyKeyboard> {
    public ReplyKeyboardDeserializer() {
        super(ReplyKeyboard.class);
    }

    @Override
    public ReplyKeyboard deserializeWithType(JsonParser p, DeserializationContext ctx,
                                             TypeDeserializer typeDeserializer) throws IOException {
        return deserialize(p, ctx);
    }

    @Override
    public ReplyKeyboard deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonNode node = ctx.readTree(p);

        if (node.has("inline_keyboard")) {
            return ctx.readTreeAsValue(node, InlineKeyboardMarkup.class);
        }
        if (node.has("keyboard")) {
            return ctx.readTreeAsValue(node, ReplyKeyboardMarkup.class);
        }
        if (node.has("remove_keyboard")) {
            return ctx.readTreeAsValue(node, ReplyKeyboardRemove.class);
        }
        if (node.has("force_reply")) {
            return ctx.readTreeAsValue(node, ForceReplyKeyboard.class);
        }
        return null;
    }
}
