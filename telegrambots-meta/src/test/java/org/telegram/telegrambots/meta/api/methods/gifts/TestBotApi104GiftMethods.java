package org.telegram.telegrambots.meta.api.methods.gifts;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGift;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBotApi104GiftMethods {
    private final JsonMapper mapper = new JsonMapper();
    private static final String ERROR = "{\"ok\":false,\"error_code\":400,\"description\":\"Bad Request\"}";

    @Test
    void getUniqueGiftRequestAndValidation() throws Exception {
        GetUniqueGift method = GetUniqueGift.builder().name("PlushPepe-123").build();
        assertEquals("getUniqueGift", method.getMethod());
        assertDoesNotThrow(method::validate);
        JsonNode json = mapper.valueToTree(method);
        assertEquals("PlushPepe-123", json.get("name").asText());
        assertEquals("PlushPepe-123", mapper.readValue(json.toString(), GetUniqueGift.class).getName());

        assertThrows(NullPointerException.class, () -> GetUniqueGift.builder().build());
        assertThrows(TelegramApiValidationException.class, () -> new GetUniqueGift("").validate());
    }

    @Test
    void getUniqueGiftResponseAndErrors() throws Exception {
        GetUniqueGift method = new GetUniqueGift("PlushPepe-123");
        String sticker = "{\"file_id\":\"file\",\"file_unique_id\":\"unique\",\"type\":\"regular\","
                + "\"width\":512,\"height\":512,\"is_animated\":false,\"is_video\":false}";
        String response = "{\"ok\":true,\"result\":{\"base_name\":\"Plush Pepe\","
                + "\"name\":\"PlushPepe-123\",\"number\":123,\"gift_id\":\"gift-1\",\"is_burned\":true,"
                + "\"model\":{\"name\":\"Model\",\"sticker\":" + sticker + ",\"rarity_per_mille\":20},"
                + "\"symbol\":{\"name\":\"Symbol\",\"sticker\":" + sticker + ",\"rarity_per_mille\":10},"
                + "\"backdrop\":{\"name\":\"Blue\",\"rarity_per_mille\":30,\"colors\":{"
                + "\"center_color\":1,\"edge_color\":2,\"symbol_color\":3,\"text_color\":4}}}}";
        UniqueGift gift = method.deserializeResponse(response);
        assertEquals("PlushPepe-123", gift.getName());
        assertEquals("Plush Pepe", gift.getBaseName());
        assertEquals(123, gift.getNumber());
        assertEquals("gift-1", gift.getGiftId());
        assertTrue(gift.getIsBurned());
        assertEquals("Model", gift.getModel().getName());
        assertEquals("unique", gift.getSymbol().getSticker().getFileUniqueId());
        assertEquals(1, gift.getBackdrop().getColors().getCenterColor());

        TelegramApiRequestException error = assertThrows(TelegramApiRequestException.class, () -> method.deserializeResponse(ERROR));
        assertEquals(400, error.getErrorCode());
        assertEquals("Bad Request", error.getApiResponse());
        assertThrows(TelegramApiRequestException.class, () -> method.deserializeResponse("not json"));
    }

    @Test
    void deleteGiftRequestValidationAndResponses() throws Exception {
        DeleteBusinessAccountGift method = DeleteBusinessAccountGift.builder()
                .businessConnectionId("connection").ownedGiftId("owned-gift").build();
        assertEquals("deleteBusinessAccountGift", method.getMethod());
        assertDoesNotThrow(method::validate);
        JsonNode json = mapper.valueToTree(method);
        assertEquals("connection", json.get("business_connection_id").asText());
        assertEquals("owned-gift", json.get("owned_gift_id").asText());
        DeleteBusinessAccountGift roundTrip = mapper.readValue(json.toString(), DeleteBusinessAccountGift.class);
        assertEquals("connection", roundTrip.getBusinessConnectionId());
        assertEquals("owned-gift", roundTrip.getOwnedGiftId());

        assertThrows(NullPointerException.class, () -> DeleteBusinessAccountGift.builder().ownedGiftId("gift").build());
        assertThrows(NullPointerException.class, () -> DeleteBusinessAccountGift.builder().businessConnectionId("connection").build());
        assertThrows(TelegramApiValidationException.class, () -> new DeleteBusinessAccountGift("", "gift").validate());
        assertThrows(TelegramApiValidationException.class, () -> new DeleteBusinessAccountGift("connection", "").validate());

        assertTrue(method.deserializeResponse("{\"ok\":true,\"result\":true}"));
        TelegramApiRequestException error = assertThrows(TelegramApiRequestException.class, () -> method.deserializeResponse(ERROR));
        assertEquals(400, error.getErrorCode());
        assertEquals("Bad Request", error.getApiResponse());
        assertThrows(TelegramApiRequestException.class, () -> method.deserializeResponse("not json"));
    }
}