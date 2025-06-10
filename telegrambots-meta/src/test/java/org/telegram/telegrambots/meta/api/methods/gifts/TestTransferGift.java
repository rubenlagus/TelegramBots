package org.telegram.telegrambots.meta.api.methods.gifts;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestTransferGift {

    @Test
    public void testTransferGiftGetPath() {
        TransferGift transferGift = TransferGift.builder()
                .businessConnectionId("test-connection-id")
                .ownedGiftId("test-gift-id")
                .newOwnerChatId(123456789L)
                .build();

        assertEquals("transferGift", transferGift.getMethod());
    }

    @Test
    public void testTransferGiftValidation() {
        TransferGift transferGift = TransferGift.builder()
                .businessConnectionId("test-connection-id")
                .ownedGiftId("test-gift-id")
                .newOwnerChatId(123456789L)
                .build();

        // Should not throw an exception
        try {
            transferGift.validate();
        } catch (TelegramApiValidationException e) {
            fail();
        }

        // Test with empty business connection ID
        TransferGift emptyBusinessConnectionId = TransferGift.builder()
                .businessConnectionId("")
                .ownedGiftId("test-gift-id")
                .newOwnerChatId(123456789L)
                .build();

        assertThrows(TelegramApiValidationException.class, emptyBusinessConnectionId::validate);

        // Test with empty owned gift ID
        TransferGift emptyOwnedGiftId = TransferGift.builder()
                .businessConnectionId("test-connection-id")
                .ownedGiftId("")
                .newOwnerChatId(123456789L)
                .build();

        assertThrows(TelegramApiValidationException.class, emptyOwnedGiftId::validate);
    }

    @Test
    public void testTransferGiftWithStarCount() {
        TransferGift transferGift = TransferGift.builder()
                .businessConnectionId("test-connection-id")
                .ownedGiftId("test-gift-id")
                .newOwnerChatId(123456789L)
                .starCount(100)
                .build();

        assertEquals("test-connection-id", transferGift.getBusinessConnectionId());
        assertEquals("test-gift-id", transferGift.getOwnedGiftId());
        assertEquals(Long.valueOf(123456789L), transferGift.getNewOwnerChatId());
        assertEquals(Integer.valueOf(100), transferGift.getStarCount());
    }
}
