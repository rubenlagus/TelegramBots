package org.telegram.telegrambots.meta.api.objects.menubutton.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonCommands;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonDefault;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * JSON serializer for MenuButton type
 */
public class MenuButtonDeserializer extends StdDeserializer<MenuButton>  {

    private final ObjectMapper objectMapper;

    public MenuButtonDeserializer() {
        this(null);
    }

    public MenuButtonDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MenuButton deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        switch (node.get("type").asText()) {
            case "default":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MenuButtonDefault>() {
                        });
            case "web_app":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MenuButtonWebApp>() {
                        });
            case "commands":
                return objectMapper.readValue(node.toString(),
                        new com.fasterxml.jackson.core.type.TypeReference<MenuButtonCommands>() {
                        });
        }

        return null;
    }
}
