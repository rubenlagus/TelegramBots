package org.telegram.telegrambots.meta.api.objects.richtext;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.jsontype.TypeSerializer;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Serializes {@link RichTextPlain} as a bare JSON string.
 * <p>
 * The Bot API has no {@code "plain"} rich text type: plain text is carried on the wire as a JSON
 * string. {@link RichTextPlain} only exists so that plain text has a Java representation within the
 * {@link RichText} hierarchy, and must therefore never emit a type field.
 */
public class RichTextPlainSerializer extends StdSerializer<RichTextPlain> {

    public RichTextPlainSerializer() {
        super(RichTextPlain.class);
    }

    /**
     * Called when the parent type has {@link com.fasterxml.jackson.annotation.JsonTypeInfo}.
     * RichTextPlain is a bare JSON string; it carries no type field, so we simply ignore
     * the type serializer and write the string.
     */
    @Override
    public void serializeWithType(RichTextPlain value, JsonGenerator gen,
                                  SerializationContext ctxt, TypeSerializer typeSer) {
        serialize(value, gen, ctxt);
    }

    @Override
    public void serialize(RichTextPlain value, JsonGenerator gen, SerializationContext ctxt) {
        gen.writeString(value.getText());
    }
}
