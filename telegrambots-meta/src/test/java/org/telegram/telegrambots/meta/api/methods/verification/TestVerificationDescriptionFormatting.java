package org.telegram.telegrambots.meta.api.methods.verification;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestVerificationDescriptionFormatting {
    private final JsonMapper mapper = new JsonMapper();

    private MessageEntity entity(String type) {
        return MessageEntity.builder().type(type).offset(0).length(4).url("https://example.com").build();
    }

    @Test
    void userFormattingSerializationAndConstructors() throws Exception {
        VerifyUser legacy = new VerifyUser(123L, "description");
        assertEquals("description", legacy.getCustomDescription());
        assertNull(legacy.getCustomDescriptionParseMode());
        assertNull(legacy.getCustomDescriptionEntities());
        assertEquals(123L, new VerifyUser(123L).getUserId());
        assertEquals("HTML", new VerifyUser(123L, "link", "HTML", null).getCustomDescriptionParseMode());

        VerifyUser mode = VerifyUser.builder().userId(123L).customDescription("[link](https://example.com)")
                .customDescriptionParseMode("MarkdownV2").build();
        assertDoesNotThrow(mode::validate);
        JsonNode json = mapper.valueToTree(mode);
        assertEquals("verifyUser", mode.getMethod());
        assertEquals(123L, json.get("user_id").asLong());
        assertEquals("MarkdownV2", json.get("custom_description_parse_mode").asText());
        assertFalse(json.has("custom_description_entities"));
        assertEquals("MarkdownV2", mapper.readValue(json.toString(), VerifyUser.class).getCustomDescriptionParseMode());

        VerifyUser entities = VerifyUser.builder().userId(123L).customDescription("link")
                .customDescriptionEntities(Collections.singletonList(entity("text_link"))).build();
        assertDoesNotThrow(entities::validate);
        JsonNode entitiesJson = mapper.valueToTree(entities);
        assertEquals("text_link", entitiesJson.get("custom_description_entities").get(0).get("type").asText());
        assertEquals("https://example.com", entitiesJson.get("custom_description_entities").get(0).get("url").asText());
        assertEquals("text_link", mapper.readValue(entitiesJson.toString(), VerifyUser.class).getCustomDescriptionEntities().get(0).getType());
    }

    @Test
    void chatFormattingSerializationAndConstructors() throws Exception {
        VerifyChat legacy = new VerifyChat("@channel", "description");
        assertEquals("description", legacy.getCustomDescription());
        assertNull(legacy.getCustomDescriptionParseMode());
        assertNull(legacy.getCustomDescriptionEntities());
        assertEquals("@channel", new VerifyChat("@channel").getChatId());
        assertEquals("HTML", new VerifyChat("@channel", "link", "HTML", null).getCustomDescriptionParseMode());

        VerifyChat mode = VerifyChat.builder().chatId(123L).customDescription("<a href=\"https://example.com\">link</a>")
                .customDescriptionParseMode("HTML").build();
        assertDoesNotThrow(mode::validate);
        JsonNode json = mapper.valueToTree(mode);
        assertEquals("verifyChat", mode.getMethod());
        assertEquals("123", json.get("chat_id").asText());
        assertEquals("HTML", json.get("custom_description_parse_mode").asText());
        assertFalse(json.has("custom_description_entities"));
        assertEquals("HTML", mapper.readValue(json.toString(), VerifyChat.class).getCustomDescriptionParseMode());

        VerifyChat entities = VerifyChat.builder().chatId("@channel").customDescription("link")
                .customDescriptionEntities(Collections.singletonList(entity("text_link"))).build();
        assertDoesNotThrow(entities::validate);
        JsonNode entitiesJson = mapper.valueToTree(entities);
        assertEquals("text_link", entitiesJson.get("custom_description_entities").get(0).get("type").asText());
        assertEquals("text_link", mapper.readValue(entitiesJson.toString(), VerifyChat.class).getCustomDescriptionEntities().get(0).getType());
    }

    @Test
    void validationAndErrorResponses() {
        List<MessageEntity> links = Collections.singletonList(entity("text_link"));
        List<MessageEntity> invalid = Collections.singletonList(entity("bold"));
        List<MessageEntity> nullEntity = Arrays.asList((MessageEntity) null);
        VerifyUser user = new VerifyUser(123L, "link");
        VerifyChat chat = new VerifyChat("@channel", "link");

        user.setCustomDescriptionParseMode("HTML");
        chat.setCustomDescriptionParseMode("HTML");
        user.setCustomDescriptionEntities(links);
        chat.setCustomDescriptionEntities(links);
        assertThrows(TelegramApiValidationException.class, user::validate);
        assertThrows(TelegramApiValidationException.class, chat::validate);

        user.setCustomDescriptionParseMode(null);
        chat.setCustomDescriptionParseMode(null);
        for (List<MessageEntity> badEntities : Arrays.asList(invalid, nullEntity)) {
            user.setCustomDescriptionEntities(badEntities);
            chat.setCustomDescriptionEntities(badEntities);
            assertThrows(TelegramApiValidationException.class, user::validate);
            assertThrows(TelegramApiValidationException.class, chat::validate);
        }

        user.setCustomDescriptionEntities(null);
        chat.setCustomDescriptionEntities(null);
        user.setCustomDescription("");
        chat.setCustomDescription("");
        assertDoesNotThrow(user::validate);
        assertDoesNotThrow(chat::validate);
        user.setCustomDescription("x".repeat(71));
        chat.setCustomDescription("x".repeat(71));
        assertThrows(TelegramApiValidationException.class, user::validate);
        assertThrows(TelegramApiValidationException.class, chat::validate);
        user.setCustomDescriptionParseMode("HTML");
        chat.setCustomDescriptionParseMode("HTML");
        user.setCustomDescription("<a href=\"https://example.com/" + "x".repeat(70) + "\">link</a>");
        chat.setCustomDescription(user.getCustomDescription());
        assertDoesNotThrow(user::validate);
        assertDoesNotThrow(chat::validate);

        assertThrows(TelegramApiValidationException.class, () -> new VerifyUser(0L).validate());
        assertThrows(TelegramApiValidationException.class, () -> new VerifyChat("").validate());
        assertTrue(assertDoesNotThrow(() -> user.deserializeResponse("{\"ok\":true,\"result\":true}")));
        assertTrue(assertDoesNotThrow(() -> chat.deserializeResponse("{\"ok\":true,\"result\":true}")));
        String error = "{\"ok\":false,\"error_code\":400,\"description\":\"Bad Request\"}";
        assertEquals(400, assertThrows(TelegramApiRequestException.class, () -> user.deserializeResponse(error)).getErrorCode());
        assertEquals(400, assertThrows(TelegramApiRequestException.class, () -> chat.deserializeResponse(error)).getErrorCode());
    }
}