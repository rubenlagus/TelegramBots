package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A text with a bank card number.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextBankCardNumber implements RichText {
    public static final String TYPE = "bank_card_number";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";
    private static final String BANK_CARD_NUMBER_FIELD = "bank_card_number";

    /**
     * Type of the rich text, always "bank_card_number"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The text
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;

    /**
     * The bank card number
     */
    @JsonProperty(BANK_CARD_NUMBER_FIELD)
    @NonNull
    private String bankCardNumber;
}
