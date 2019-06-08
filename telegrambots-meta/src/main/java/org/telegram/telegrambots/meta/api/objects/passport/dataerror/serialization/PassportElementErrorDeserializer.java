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
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorDataField>(){});
            case "front_side":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorFrontSide>(){});
            case "reverse_side":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorReverseSide>(){});
            case "selfie":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorSelfie>(){});
            case "file":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorFile>(){});
            case "files":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorFiles>(){});
            case "translation_file":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorTranslationFile>(){});
            case "translation_files":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorTranslationFiles>(){});
            case "unspecified":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<PassportElementErrorUnspecified>(){});
        }


        return null;
    }
}
