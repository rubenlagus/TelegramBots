package org.telegram.telegrambots.meta.api.objects.payments.paidmedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartner;

/**
 * @author Ruben Bermudez
 * @version 7.6
 *
 * The paid media isn't available before the payment.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class PaidMediaPreview implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";

    /**
     * Type of the paid media, always “preview”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "preview";
    /**
     * Optional.
     * Media width as defined by the sender
     */
    @JsonProperty(WIDTH_FIELD)
    private Integer width;
    /**
     * Optional.
     * Media height as defined by the sender
     */
    @JsonProperty(HEIGHT_FIELD)
    private Integer height;
    /**
     * Optional.
     * Duration of the media in seconds as defined by the sender
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
}
