package org.telegram.telegrambots.meta.api.objects.commands.scope.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChatMember;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 */
public class BotCommandScopeDeserializer extends StdDeserializer<BotCommandScope> {
    private final ObjectMapper objectMapper;

    public BotCommandScopeDeserializer() {
        this(null);
    }

    private BotCommandScopeDeserializer(Class<?> vc) {
        super(vc);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public BotCommandScope deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String type = node.has("Type") ? node.get("type").asText() : "";
        switch (type) {
            case "default":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeDefault>(){});
            case "all_private_chats":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeAllPrivateChats>(){});
            case "all_group_chats":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeAllGroupChats>(){});
            case "all_chat_administrators":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeAllChatAdministrators>(){});
            case "chat":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeChat>(){});
            case "chat_administrators":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeChatAdministrators>(){});
            case "chat_member":
                return objectMapper.readValue(node.toString(),
                        new TypeReference<BotCommandScopeChatMember>(){});
            default:
                return null;
        }
    }
}
