package org.telegram.telegrambots.meta.api.objects.gifts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestUniqueGiftInfoText {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testDeserializeTextEntitiesAndIsPrivate() throws IOException {
        String json = "{\"gift\":{\"base_name\":\"b\",\"name\":\"n\",\"number\":1,\"gift_id\":\"g\"},\"origin\":\"transfer\",\"text\":\"Happy birthday\","
                + "\"entities\":[{\"type\":\"bold\",\"offset\":0,\"length\":5}],\"is_private\":true}";

        UniqueGiftInfo info = mapper.readValue(json, UniqueGiftInfo.class);

        assertEquals("Happy birthday", info.getText());
        assertEquals(1, info.getEntities().size());
        assertEquals("bold", info.getEntities().get(0).getType());
        assertTrue(info.getIsPrivate());
    }

    @Test
    public void testNewFieldsAreOptional() throws IOException {
        String json = "{\"gift\":{\"base_name\":\"b\",\"name\":\"n\",\"number\":1,\"gift_id\":\"g\"},\"origin\":\"transfer\"}";

        UniqueGiftInfo info = mapper.readValue(json, UniqueGiftInfo.class);

        assertNull(info.getText());
        assertNull(info.getEntities());
        assertNull(info.getIsPrivate());
    }
}
