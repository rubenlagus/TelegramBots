package org.telegram.telegrambots.meta.api.objects.media.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.media.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class InputMediaDeserializer extends StdDeserializer<InputMedia> {
    private final ObjectMapper objectMapper;

    public InputMediaDeserializer() {
        this(null);
    }

    public InputMediaDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public InputMedia deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        switch (node.get("type").asText()) {
            case "photo":
            case "animation":
            case "video":
            case "audio":
            case "document":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InputMediaPhoto>() {
                        });
        }

        return null;
    }
}
