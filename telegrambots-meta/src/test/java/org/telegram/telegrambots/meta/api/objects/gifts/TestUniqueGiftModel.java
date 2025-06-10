package org.telegram.telegrambots.meta.api.objects.gifts;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author Ruben Bermudez
 * @version 9.0
 */
public class TestUniqueGiftModel {

    @Test
    public void testUniqueGiftModelConstructor() {
        Sticker sticker = mock(Sticker.class);
        
        UniqueGiftModel uniqueGiftModel = UniqueGiftModel.builder()
                .name("Test Gift Model")
                .sticker(sticker)
                .rarityPerMille(100)
                .build();

        assertEquals("Test Gift Model", uniqueGiftModel.getName());
        assertEquals(sticker, uniqueGiftModel.getSticker());
        assertEquals(Integer.valueOf(100), uniqueGiftModel.getRarityPerMille());
    }

    @Test
    public void testUniqueGiftModelSettersAndGetters() {
        Sticker sticker1 = mock(Sticker.class);
        Sticker sticker2 = mock(Sticker.class);
        
        UniqueGiftModel uniqueGiftModel = UniqueGiftModel.builder()
                .name("Initial Name")
                .sticker(sticker1)
                .rarityPerMille(100)
                .build();

        assertEquals("Initial Name", uniqueGiftModel.getName());
        assertEquals(sticker1, uniqueGiftModel.getSticker());
        assertEquals(Integer.valueOf(100), uniqueGiftModel.getRarityPerMille());

        // Test setters
        uniqueGiftModel.setName("Updated Name");
        uniqueGiftModel.setSticker(sticker2);
        uniqueGiftModel.setRarityPerMille(200);

        assertEquals("Updated Name", uniqueGiftModel.getName());
        assertEquals(sticker2, uniqueGiftModel.getSticker());
        assertEquals(Integer.valueOf(200), uniqueGiftModel.getRarityPerMille());
    }
}
