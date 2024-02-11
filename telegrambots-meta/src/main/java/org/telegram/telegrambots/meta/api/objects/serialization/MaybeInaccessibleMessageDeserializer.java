package org.telegram.telegrambots.meta.api.objects.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.InaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class MaybeInaccessibleMessageDeserializer extends StdDeserializer<MaybeInaccessibleMessage> {
    private final ObjectMapper objectMapper;

    public MaybeInaccessibleMessageDeserializer() {
        this(null);
    }

    private MaybeInaccessibleMessageDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MaybeInaccessibleMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.has(MaybeInaccessibleMessage.DATE_FIELD)) {
            int date = node.get(MaybeInaccessibleMessage.DATE_FIELD).asInt(0);
            if (date == 0) {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<InaccessibleMessage>(){});
            } else {
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<Message>(){});
            }
        }
        return null;
    }
}
