package org.telegram.telegrambots.meta.api.objects.boost.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSource;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostSourcePremium;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class ChatBoostSourceDeserializer extends StdDeserializer<ChatBoostSource> {
    private final ObjectMapper objectMapper;

    public ChatBoostSourceDeserializer() {
        this(null);
    }

    private ChatBoostSourceDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ChatBoostSource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.has("source")) {
            String source = node.get("source").asText();

            if (ChatBoostSource.PREMIUM_TYPE.equals(source)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<ChatBoostSourcePremium>(){});
            }
            if (ChatBoostSource.GIFT_CODE_TYPE.equals(source)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<ChatBoostSourcePremium>(){});
            }
            if (ChatBoostSource.GIVEAWAY_TYPE.equals(source)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<ChatBoostSourcePremium>(){});
            }

        }
        return null;
    }
}
