package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChatBoostSource {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testChatBoostSourcePremium() throws IOException {
        ChatBoost boostPremium = ChatBoost
                .builder()
                .boostId("testId")
                .addDate(2345)
                .expirationDate(6789)
                .source(ChatBoostSourcePremium
                        .builder()
                        .user(new User(12345L, "TestUser", false))
                        .build())
                .build();

        String resultPremium = mapper.writeValueAsString(boostPremium);
        assertEquals(boostPremium, mapper.readValue(resultPremium, ChatBoost.class));
    }

    @Test
    public void testChatBoostSourceGiveaway() throws IOException {
        ChatBoost boostGiveaway = ChatBoost
                .builder()
                .boostId("testId")
                .addDate(2345)
                .expirationDate(6789)
                .source(ChatBoostSourceGiveaway
                        .builder()
                        .giveawayMessageId(12345)
                        .user(new User(12345L, "TestUser", false))
                        .isUnclaimed(false)
                        .build())
                .build();

        String resultGiveaway = mapper.writeValueAsString(boostGiveaway);
        assertEquals(boostGiveaway, mapper.readValue(resultGiveaway, ChatBoost.class));
    }

    @Test
    public void testChatBoostSourceGiftCode() throws IOException {
        ChatBoost boostGift = ChatBoost
                .builder()
                .boostId("testId")
                .addDate(2345)
                .expirationDate(6789)
                .source(ChatBoostSourceGiftCode
                        .builder()
                        .user(new User(12345L, "TestUser", false))
                        .build())
                .build();

        String resultGift = mapper.writeValueAsString(boostGift);
        assertEquals(boostGift, mapper.readValue(resultGift, ChatBoost.class));
    }
}
