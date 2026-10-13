package org.telegram.telegrambots.meta.api.objects.richtext;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsontype.TypeSerializer;
import tools.jackson.databind.ser.std.StdSerializer;

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
                                  SerializationContext ctxt, TypeSerializer typeSer) {
        serialize(value, gen, ctxt);
    }

    @Override
    public void serialize(RichTextConcat value, JsonGenerator gen, SerializationContext ctxt) {
        gen.writeStartArray();
        for (RichText text : value.getTexts()) {
            if (text == null) {
                ctxt.defaultSerializeNullValue(gen);
            } else {
                ctxt.findValueSerializer(text.getClass()).serialize(text, gen, ctxt);
            }
        }
        gen.writeEndArray();
    }
}
