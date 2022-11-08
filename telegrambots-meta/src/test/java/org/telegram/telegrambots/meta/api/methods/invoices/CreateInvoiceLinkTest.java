package org.telegram.telegrambots.meta.api.methods.invoices;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Ruben Bermudez
 * @version 5.2
 */
public class CreateInvoiceLinkTest {
    @Test
    public void validObjectMustNotThrow() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        assertDoesNotThrow(createInvoiceLink::validate);
        createInvoiceLink.setSuggestedTipAmounts(new ArrayList<>());
        assertDoesNotThrow(createInvoiceLink::validate);
    }

    @Test
    public void titleCantBeEmpty() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setTitle("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Title parameter can't be empty or longer than 32 chars", thrown.getMessage());
    }

    @Test
    public void titleCantBeOver32Chars() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setTitle("1234567890123456789012345678901234");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Title parameter can't be empty or longer than 32 chars", thrown.getMessage());
    }

    @Test
    public void descriptionCantBeEmpty() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setDescription("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Description parameter can't be empty or longer than 255 chars", thrown.getMessage());
    }

    @Test
    public void descriptionCantBeOver255() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setDescription("1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345" +
                "1234567890123456789012345678901234567890123456789012345678901234567890");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Description parameter can't be empty or longer than 255 chars", thrown.getMessage());
    }

    @Test
    public void payloadCantBeEmpty() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setPayload("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Payload parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void providerTokenCantBeEmpty() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setProviderToken("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("ProviderToken parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void currencyCantBeEmpty() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setCurrency("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Currency parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void pricesMustBeValidated() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setPrices(Collections.singletonList(LabeledPrice.builder().label("").amount(1).build()));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Label parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void pricesCantBeEmpty() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setPrices(new ArrayList<>());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Prices parameter can't be empty", thrown.getMessage());
        createInvoiceLink.setPrices(Collections.singletonList(LabeledPrice.builder().label("").amount(1).build()));
        thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("Label parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void suggestedTipAmountsMustNotHaveMoreThan4Elements() {
        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();
        createInvoiceLink.setSuggestedTipAmounts(Arrays.asList(1,2,3,4,5));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, createInvoiceLink::validate);
        assertEquals("No more that 4 suggested tips allowed", thrown.getMessage());
    }

    @Test
    public void testCreateInvoiceLinkDeserializeValidResponse(){
        String responseText = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": \"https://t.me/testlink\" \n" +
                "}";

        CreateInvoiceLink createInvoiceLink = createSendInvoiceObject();

        try {
            String link = createInvoiceLink.deserializeResponse(responseText);
            assertEquals("https://t.me/testlink",link);
        } catch (TelegramApiRequestException e) {
            fail(e.getMessage());
        }
    }

    private CreateInvoiceLink createSendInvoiceObject() {
        return CreateInvoiceLink.builder()
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
