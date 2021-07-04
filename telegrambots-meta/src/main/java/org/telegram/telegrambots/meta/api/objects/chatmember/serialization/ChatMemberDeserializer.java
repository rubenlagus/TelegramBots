package org.telegram.telegrambots.meta.api.objects.chatmember.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberOwner;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 */
public class ChatMemberDeserializer extends StdDeserializer<ChatMember> {
    private final ObjectMapper objectMapper;

    public ChatMemberDeserializer() {
        this(null);
    }

    private ChatMemberDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ChatMember deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String type = node.has("status") ? node.get("status").asText() : "";
        switch (type) {
            case "administrator":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<ChatMemberAdministrator>(){});
            case "kicked":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<ChatMemberBanned>(){});
            case "left":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<ChatMemberLeft>(){});
            case "member":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<ChatMemberMember>(){});
            case "creator":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<ChatMemberOwner>(){});
            case "restricted":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<ChatMemberRestricted>(){});
            default:
                return null;
        }
    }
}
