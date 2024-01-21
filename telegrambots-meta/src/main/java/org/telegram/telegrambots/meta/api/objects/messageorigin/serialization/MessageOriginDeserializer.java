package org.telegram.telegrambots.meta.api.objects.messageorigin.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOrigin;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOriginChannel;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOriginChat;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOriginHiddenUser;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOriginUser;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class MessageOriginDeserializer extends StdDeserializer<MessageOrigin> {
    private final ObjectMapper objectMapper;

    public MessageOriginDeserializer() {
        this(null);
    }

    private MessageOriginDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MessageOrigin deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.has("type")) {
            String type = node.get("type").asText();

            if (MessageOrigin.USER_TYPE.equals(type)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MessageOriginUser>(){});
            }
            if (MessageOrigin.HIDDEN_USER_TYPE.equals(type)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MessageOriginHiddenUser>(){});
            }
            if (MessageOrigin.CHAT_TYPE.equals(type)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MessageOriginChat>(){});
            }
            if (MessageOrigin.CHANNEL_TYPE.equals(type)) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MessageOriginChannel>(){});
            }
        }
        return null;
    }
}
