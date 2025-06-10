package org.telegram.telegrambots.meta.api.methods.gifts;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestGiftPremiumSubscription {

    @Test
    public void testGiftPremiumSubscriptionGetPath() {
        GiftPremiumSubscription giftPremiumSubscription = GiftPremiumSubscription.builder()
                .userId(123456789L)
                .monthCount(3)
                .starCount(1000)
                .build();

        assertEquals("giftPremiumSubscription", giftPremiumSubscription.getMethod());
    }

    @Test
    public void testGiftPremiumSubscriptionValidation() {
        GiftPremiumSubscription giftPremiumSubscription = GiftPremiumSubscription.builder()
                .userId(123456789L)
                .monthCount(3)
                .starCount(1000)
                .build();

        // Should not throw an exception
        try {
            giftPremiumSubscription.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with long text
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 129; i++) {
            longText.append("a");
        }

        GiftPremiumSubscription withLongText = GiftPremiumSubscription.builder()
                .userId(123456789L)
                .monthCount(3)
                .starCount(1000)
                .text(longText.toString())
                .build();

        assertThrows(TelegramApiValidationException.class, withLongText::validate);

        // Test with empty text
        GiftPremiumSubscription withEmptyText = GiftPremiumSubscription.builder()
                .userId(123456789L)
                .monthCount(3)
                .starCount(1000)
                .text("")
                .build();

        assertThrows(TelegramApiValidationException.class, withEmptyText::validate);
    }

    @Test
    public void testGiftPremiumSubscriptionWithOptionalParams() {
        List<MessageEntity> entities = new ArrayList<>();
        
        GiftPremiumSubscription giftPremiumSubscription = GiftPremiumSubscription.builder()
                .userId(123456789L)
                .monthCount(3)
                .starCount(1000)
                .text("Gift message")
                .textParseMode("Markdown")
                .textEntities(entities)
                .build();

        assertEquals(123456789L, giftPremiumSubscription.getUserId());
        assertEquals(Integer.valueOf(3), giftPremiumSubscription.getMonthCount());
        assertEquals(Integer.valueOf(1000), giftPremiumSubscription.getStarCount());
        assertEquals("Gift message", giftPremiumSubscription.getText());
        assertEquals("Markdown", giftPremiumSubscription.getTextParseMode());
        assertEquals(entities, giftPremiumSubscription.getTextEntities());
    }
}
