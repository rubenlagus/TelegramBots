package org.telegram.telegrambots.meta.api.objects.reactions.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionTypeCustomEmoji;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionTypeEmoji;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class ReactionDeserializer extends StdDeserializer<ReactionType> {
    private final ObjectMapper objectMapper;

    public ReactionDeserializer() {
        this(null);
    }

    private ReactionDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ReactionType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.has("type")) {
            String type = node.get("type").asText();

            if (ReactionType.EMOJI_TYPE.equals(type)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<ReactionTypeEmoji>(){});
            }
            if (ReactionType.CUSTOM_EMOJI_TYPE.equals(type)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<ReactionTypeCustomEmoji>(){});
            }
        }
        return null;
    }
}
