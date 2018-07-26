package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 */
public class InputFileSerializer extends JsonSerializer<InputFile> {
    @Override
    public void serialize(InputFile value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getAttachName());
    }
}
