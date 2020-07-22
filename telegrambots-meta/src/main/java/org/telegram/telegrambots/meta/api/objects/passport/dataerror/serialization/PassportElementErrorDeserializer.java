package org.telegram.telegrambots.meta.api.objects.passport.dataerror.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.passport.dataerror.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class PassportElementErrorDeserializer extends StdDeserializer<PassportElementError> {
    private final ObjectMapper objectMapper;

    public PassportElementErrorDeserializer() {
        this(null);
    }

    private PassportElementErrorDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public PassportElementError deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        switch (node.get("source").asText()) {
            case "data":
            case "front_side":
            case "reverse_side":
            case "selfie":
            case "file":
            case "files":
            case "translation_file":
            case "translation_files":
            case "unspecified":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorDataField>(){});
        }


        return null;
    }
}
