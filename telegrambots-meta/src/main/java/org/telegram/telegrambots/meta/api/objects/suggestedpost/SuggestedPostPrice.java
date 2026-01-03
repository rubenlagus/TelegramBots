package org.telegram.telegrambots.meta.api.objects.suggestedpost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * Describes price of a suggested post.
 * @author Generated using AI assistance
 * @version 9.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestedPostPrice implements BotApiObject {
    private static final String CURRENCY_FIELD = "currency";
    private static final String AMOUNT_FIELD = "amount";

    /**
     * Currency in which the post will be paid. Currently, must be one of "XTR" for Telegram Stars or "TON" for toncoins
     */
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency;

    /**
     * The amount of the currency that will be paid for the post in the smallest units of the currency, 
     * i.e. Telegram Stars or nanotoncoins. Currently, price in Telegram Stars must be between 5 and 100000, 
     * and price in nanotoncoins must be between 10000000 and 10000000000000.
     */
    @JsonProperty(AMOUNT_FIELD)
    @NonNull
    private Integer amount;
}
