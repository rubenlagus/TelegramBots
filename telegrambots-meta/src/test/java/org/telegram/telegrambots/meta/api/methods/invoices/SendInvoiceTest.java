package org.telegram.telegrambots.meta.api.methods.invoices;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 5.2
 */
public class SendInvoiceTest {
    @Test
    public void validObjectMustNotThrow() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        assertDoesNotThrow(sendInvoice::validate);
        sendInvoice.setSuggestedTipAmounts(new ArrayList<>());
        assertDoesNotThrow(sendInvoice::validate);
    }

    @Test
    public void chatIdCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setChatId("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("ChatId parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void titleCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setTitle("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Title parameter can't be empty or longer than 32 chars", thrown.getMessage());
    }

    @Test
    public void titleCantBeOver32Chars() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setTitle("1234567890123456789012345678901234");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Title parameter can't be empty or longer than 32 chars", thrown.getMessage());
    }

    @Test
    public void descriptionCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setDescription("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Description parameter can't be empty or longer than 255 chars", thrown.getMessage());
    }

    @Test
    public void descriptionCantBeOver255() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setDescription("1234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345" +
                "1234567890123456789012345678901234567890123456789012345678901234567890");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Description parameter can't be empty or longer than 255 chars", thrown.getMessage());
    }

    @Test
    public void payloadCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setPayload("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Payload parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void providerTokenCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setProviderToken("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("ProviderToken parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void currencyCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setCurrency("");
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Currency parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void pricesMustBeValidated() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setPrices(Collections.singletonList(LabeledPrice.builder().label("").amount(1).build()));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Label parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void pricesCantBeEmpty() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setPrices(new ArrayList<>());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Prices parameter can't be empty", thrown.getMessage());
        sendInvoice.setPrices(Collections.singletonList(LabeledPrice.builder().label("").amount(1).build()));
        thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Label parameter can't be empty", thrown.getMessage());
    }

    @Test
    public void suggestedTipAmountsMustNotHaveMoreThan4Elements() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setSuggestedTipAmounts(Arrays.asList(1,2,3,4,5));
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("No more that 4 suggested tips allowed", thrown.getMessage());
    }

    @Test
    public void replyMarkupMustBeValidated() {
        SendInvoice sendInvoice = createSendInvoiceObject();
        sendInvoice.setReplyMarkup(InlineKeyboardMarkup.builder().keyboardRow(Collections.singletonList(InlineKeyboardButton.builder().text("").build())).build());
        Throwable thrown = assertThrows(TelegramApiValidationException.class, sendInvoice::validate);
        assertEquals("Text parameter can't be empty", thrown.getMessage());
    }

    private SendInvoice createSendInvoiceObject() {
        return SendInvoice.builder()
                .chatId(123456L)
                .title("Title")
                .description("Description")
                .payload("Payload")
                .providerToken("ProviderToken")
                .startParameter("StartParameter")
                .currency("Currency")
                .price(LabeledPrice.builder().label("Label").amount(1).build())
                .suggestedTipAmount(1)
                .suggestedTipAmount(2)
                .suggestedTipAmount(3)
                .suggestedTipAmount(4)
                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(Collections.singletonList(InlineKeyboardButton.builder().text("Hello").build())).build())
                .build();
    }
}
