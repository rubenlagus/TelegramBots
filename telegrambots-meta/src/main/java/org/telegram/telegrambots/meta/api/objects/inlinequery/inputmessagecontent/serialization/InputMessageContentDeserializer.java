package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class InputMessageContentDeserializer extends StdDeserializer<InputMessageContent> {
    private final ObjectMapper objectMapper;

    public InputMessageContentDeserializer() {
        this(null);
    }

    private InputMessageContentDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public InputMessageContent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (node.has("message_text")) {
            return objectMapper.readValue(node.toString(),
                    new com.fasterxml.jackson.core.type.TypeReference<InputTextMessageContent>(){});
        }

        // Order here is important since Venue and Location has both latitude
        if (node.has("address")) {
            return objectMapper.readValue(node.toString(),
                    new com.fasterxml.jackson.core.type.TypeReference<InputVenueMessageContent>(){});
        }


        if (node.has("latitude")) {
            return objectMapper.readValue(node.toString(),
                    new com.fasterxml.jackson.core.type.TypeReference<InputLocationMessageContent>(){});
        }

        if (node.has("phone_number")) {
            return objectMapper.readValue(node.toString(),
                    new com.fasterxml.jackson.core.type.TypeReference<InputContactMessageContent>(){});
        }

        if (node.has("provider_token")) {
            return objectMapper.readValue(node.toString(),
                    new com.fasterxml.jackson.core.type.TypeReference<InputInvoiceMessageContent>(){});
        }

        return null;
    }
}
