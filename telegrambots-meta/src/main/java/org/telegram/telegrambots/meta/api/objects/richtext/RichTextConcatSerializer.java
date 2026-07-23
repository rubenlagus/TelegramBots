package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Serializes {@link RichTextConcat} as a bare JSON array of its child nodes.
 */
public class RichTextConcatSerializer extends StdSerializer<RichTextConcat> {

    public RichTextConcatSerializer() {
        super(RichTextConcat.class);
    }

    /**
     * Called when the parent type has {@link com.fasterxml.jackson.annotation.JsonTypeInfo}.
     * RichTextConcat is a bare JSON array; it carries no type field, so we simply ignore
     * the type serializer and write the plain array.
     */
    @Override
    public void serializeWithType(RichTextConcat value, JsonGenerator gen,
                                  SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, provider);
    }

    @Override
    public void serialize(RichTextConcat value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartArray();
        for (RichText text : value.getTexts()) {
            provider.defaultSerializeValue(text, gen);
        }
        gen.writeEndArray();
    }
}
