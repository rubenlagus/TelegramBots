package org.telegram.telegrambots.meta.api.objects.message;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.core.util.JsonParserSequence;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.util.TokenBuffer;

/**
 * Resolves a {@link MaybeInaccessibleMessage} by its {@code date} field: {@code 0} means an
 * {@link InaccessibleMessage}; any other value (or a missing/null field) means a regular
 * {@link Message}.
 *
 * <p>A custom deserializer is used instead of {@code JsonTypeInfo} on the interface because, with
 * {@code As.EXISTING_PROPERTY}, every distinct {@code date} value (that is, every message) becomes
 * a new type id, and Jackson caches one deserializer per type id forever.
 *
 * <p>Properties preceding {@code date} are buffered only until the field is found; the remaining
 * input is streamed directly into the selected subtype. The two subtype deserializers are resolved
 * once and reused, keeping the cost of a message independent of its {@code date} value.
 *
 * @author Ruben Bermudez
 * @version 10.4
 */
public class MaybeInaccessibleMessageDeserializer extends StdDeserializer<MaybeInaccessibleMessage> {

    private volatile ValueDeserializer<Object> messageDeserializer;
    private volatile ValueDeserializer<Object> inaccessibleMessageDeserializer;

    public MaybeInaccessibleMessageDeserializer() {
        super(MaybeInaccessibleMessage.class);
    }

    @Override
    public MaybeInaccessibleMessage deserialize(JsonParser p, DeserializationContext ctx) {
        JsonToken token = p.currentToken();
        if (token == JsonToken.VALUE_NULL) {
            return null;
        }
        if (token == JsonToken.START_OBJECT) {
            token = p.nextToken();
        }

        TokenBuffer buffer = null;
        while (token == JsonToken.PROPERTY_NAME) {
            String name = p.currentName();
            p.nextToken();

            if (MaybeInaccessibleMessage.DATE_FIELD.equals(name)) {
                Class<? extends MaybeInaccessibleMessage> target =
                        "0".equals(p.getValueAsString()) ? InaccessibleMessage.class : Message.class;
                if (buffer == null) {
                    buffer = ctx.bufferForInputBuffering(p);
                }
                buffer.writeName(name);
                buffer.copyCurrentStructure(p);
                p.clearCurrentToken();
                p = JsonParserSequence.createFlattened(false, buffer.asParser(ctx, p), p);
                if (p.currentToken() != JsonToken.END_OBJECT) {
                    p.nextToken();
                }
                return deserializeAs(p, ctx, target);
            }

            if (buffer == null) {
                buffer = ctx.bufferForInputBuffering(p);
            }
            buffer.writeName(name);
            buffer.copyCurrentStructure(p);
            token = p.nextToken();
        }

        if (buffer != null) {
            buffer.writeEndObject();
            p = buffer.asParser(ctx, p);
            p.nextToken();
        }
        if (p.currentToken() == JsonToken.END_OBJECT) {
            return new Message();
        }
        return deserializeAs(p, ctx, Message.class);
    }

    private MaybeInaccessibleMessage deserializeAs(JsonParser p, DeserializationContext ctx,
                                                   Class<? extends MaybeInaccessibleMessage> target) {
        ValueDeserializer<Object> deserializer = target == InaccessibleMessage.class
                ? inaccessibleMessageDeserializer
                : messageDeserializer;
        if (deserializer == null) {
            deserializer = ctx.findContextualValueDeserializer(ctx.constructType(target), null);
            if (target == InaccessibleMessage.class) {
                inaccessibleMessageDeserializer = deserializer;
            } else {
                messageDeserializer = deserializer;
            }
        }
        return (MaybeInaccessibleMessage) deserializer.deserialize(p, ctx);
    }
}
