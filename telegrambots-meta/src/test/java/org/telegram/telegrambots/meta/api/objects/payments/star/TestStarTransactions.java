package org.telegram.telegrambots.meta.api.objects.payments.star;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerFragment;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerOther;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerUser;
import org.telegram.telegrambots.meta.api.objects.payments.withdrawalstate.RevenueWithdrawalStateFailed;
import org.telegram.telegrambots.meta.api.objects.payments.withdrawalstate.RevenueWithdrawalStatePending;
import org.telegram.telegrambots.meta.api.objects.payments.withdrawalstate.RevenueWithdrawalStateSucceeded;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestStarTransactions {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testTransactionPartner() {
        StarTransactions starTransactions = StarTransactions
                .builder()
                .transaction(StarTransaction
                        .builder()
                        .id("TestId1")
                        .date(12345)
                        .amount(100)
                        .source(TransactionPartnerFragment
                                .builder()
                                .withdrawalState(RevenueWithdrawalStateSucceeded
                                        .builder()
                                        .date(98765)
                                        .url("url")
                                        .build())
                                .build())
                        .receiver(TransactionPartnerFragment
                                .builder()
                                .withdrawalState(RevenueWithdrawalStateFailed
                                        .builder()
                                        .build())
                                .build())
                        .build())
                .transaction(StarTransaction
                        .builder()
                        .id("TestId2")
                        .date(12345)
                        .amount(100)
                        .source(TransactionPartnerFragment
                                .builder()
                                .withdrawalState(RevenueWithdrawalStatePending
                                        .builder()
                                        .build())
                                .build())
                        .receiver(TransactionPartnerOther
                                .builder()
                                .build())
                        .build())
                .transaction(StarTransaction
                        .builder()
                        .id("TestId3")
                        .date(12345)
                        .amount(100)
                        .source(TransactionPartnerUser
                                .builder()
                                .user(User
                                        .builder()
                                        .id(1L)
                                        .firstName("FirstName")
                                        .isBot(false)
                                        .build())
                                .build())
                        .receiver(TransactionPartnerUser
                                .builder()
                                .user(User
                                        .builder()
                                        .id(1L)
                                        .firstName("FirstName")
                                        .isBot(false)
                                        .build())
                                .build())
                        .build())
                .build();

        try {
            String result = mapper.writeValueAsString(starTransactions);
            assertEquals(starTransactions, mapper.readValue(result, StarTransactions.class));
        } catch (Exception e) {
            fail(e);
        }
    }
}
