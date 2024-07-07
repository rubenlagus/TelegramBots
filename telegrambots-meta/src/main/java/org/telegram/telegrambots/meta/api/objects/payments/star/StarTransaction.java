package org.telegram.telegrambots.meta.api.objects.payments.star;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartner;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Describes a Telegram Star transaction.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarTransaction implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String AMOUNT_FIELD = "amount";
    private static final String DATE_FIELD = "date";
    private static final String SOURCE_FIELD = "source";
    private static final String RECEIVER_FIELD = "receiver";

    /**
     * Unique identifier of the transaction.
     * Coincides with the identifier of the original transaction for refund transactions.
     * Coincides with SuccessfulPayment.telegram_payment_charge_id for successful incoming payments from users.
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id;
    /**
     * Number of Telegram Stars transferred by the transaction
     */
    @JsonProperty(AMOUNT_FIELD)
    @NonNull
    private Integer amount;
    /**
     * Date the transaction was created in Unix time
     */
    @JsonProperty(DATE_FIELD)
    @NonNull
    private Integer date;
    /**
     * Optional.
     * Source of an incoming transaction (e.g., a user purchasing goods or services, Fragment refunding a failed withdrawal).
     * Only for incoming transactions
     */
    @JsonProperty(SOURCE_FIELD)
    private TransactionPartner source;
    /**
     * Optional.
     * Receiver of an outgoing transaction (e.g., a user for a purchase refund, Fragment for a withdrawal).
     * Only for outgoing transactions
     */
    @JsonProperty(RECEIVER_FIELD)
    private TransactionPartner receiver;
}
