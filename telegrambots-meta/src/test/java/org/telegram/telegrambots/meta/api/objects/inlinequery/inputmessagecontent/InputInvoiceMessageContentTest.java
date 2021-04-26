package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.TestUtils;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
public class InputInvoiceMessageContentTest {

    @Test
    public void validObjectMustNotThrow() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        assertDoesNotThrow(sendInvoice::validate);
    }

    @Test
    public void titleCantBeEmpty() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setTitle("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Title parameter must be between 1 and 32 characters", thrown.getMessage());
    }

    @Test
    public void titleCantBeLongerThan32() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setTitle(TestUtils.getTextOfSize(33));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Title parameter must be between 1 and 32 characters", thrown.getMessage());
    }

    @Test
    public void descriptionCantBeEmpty() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setDescription("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Description parameter must be between 1 and 255 characters", thrown.getMessage());
    }

    @Test
    public void descriptionCantBeLongerThan255() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setDescription(TestUtils.getTextOfSize(256));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Description parameter must be between 1 and 255 characters", thrown.getMessage());
    }

    @Test
    public void payloadCantBeEmpty() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setPayload("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Payload parameter must be between 1 and 128 characters", thrown.getMessage());
    }

    @Test
    public void payloadCantBeLongerThan128() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setPayload(TestUtils.getTextOfSize(129));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Payload parameter must be between 1 and 128 characters", thrown.getMessage());
    }

    @Test
    public void providerTokenCantBeEmpty() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setProviderToken("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("ProviderToken parameter must not be empty", thrown.getMessage());
    }

    @Test
    public void currencyCantBeEmpty() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setCurrency("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Currency parameter must not be empty", thrown.getMessage());
    }

    @Test
    public void pricesMustBeValidated() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setPrices(Collections.singletonList(LabeledPrice.builder().label("").amount(1).build()));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Label parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void pricesCantBeEmpty() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setPrices(new ArrayList<>());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Prices parameter must not be empty", thrown.getMessage());
        sendInvoice.setPrices(Collections.singletonList(LabeledPrice.builder().label("").amount(1).build()));
        thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Label parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void suggestedTipAmountsMustNotHaveMoreThan4Elements() {
        InputInvoiceMessageContent sendInvoice = createSendInvoiceObject();
        sendInvoice.setSuggestedTipAmounts(Arrays.asList(1,2,3,4,5));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Only up to 4 suggested tip amounts are allowed", thrown.getMessage());
    }

    private InputInvoiceMessageContent createSendInvoiceObject() {
        return InputInvoiceMessageContent.builder()
                .title("Title")
                .description("Description")
                .payload("Payload")
                .providerToken("ProviderToken")
                .currency("Currency")
                .price(LabeledPrice.builder().label("Label").amount(1).build())
                .suggestedTipAmount(1)
                .suggestedTipAmount(2)
                .suggestedTipAmount(3)
                .suggestedTipAmount(4)
                .build();
    }
}
