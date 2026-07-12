package org.telegram.telegrambots.meta.api.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestLink {

    @Test
    public void testLinkBuilder() {
        Link link = Link.builder()
                .url("https://example.com")
                .build();

        assertNotNull(link);
        assertEquals("https://example.com", link.getUrl());
    }

    @Test
    public void testLinkSetUrl() {
        Link link = Link.builder()
                .url("https://example.com")
                .build();
        link.setUrl("https://other.com");

        assertEquals("https://other.com", link.getUrl());
    }

    @Test
    public void testLinkEquality() {
        Link link1 = Link.builder().url("https://example.com").build();
        Link link2 = Link.builder().url("https://example.com").build();
        Link link3 = Link.builder().url("https://other.com").build();

        assertEquals(link1, link2);
        assertEquals(link1.hashCode(), link2.hashCode());
    }
}
