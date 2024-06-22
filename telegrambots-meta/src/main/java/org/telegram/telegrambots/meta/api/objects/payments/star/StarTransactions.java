package org.telegram.telegrambots.meta.api.objects.payments.star;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Contains a list of Telegram Star transactions.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarTransactions implements BotApiObject {
    private static final String TRANSACTIONS_FIELD = "transactions";

    /**
     * The list of transactions
     */
    @JsonProperty(TRANSACTIONS_FIELD)
    @NonNull
    @Singular
    private List<StarTransaction> transactions;
}
