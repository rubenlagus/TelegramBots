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
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.payments.star.StarAmount;

/**
 * Describes a service message about a successful payment for a suggested post.
 * @author Generated using AI assistance
 * @version 9.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestedPostPaid implements BotApiObject {
    private static final String SUGGESTED_POST_MESSAGE_FIELD = "suggested_post_message";
    private static final String CURRENCY_FIELD = "currency";
    private static final String AMOUNT_FIELD = "amount";
    private static final String STAR_AMOUNT_FIELD = "star_amount";

    /**
     * Optional.
     * Message containing the suggested post. Note that the Message object in this field will not contain 
     * the reply_to_message field even if it itself is a reply.
     */
    @JsonProperty(SUGGESTED_POST_MESSAGE_FIELD)
    private Message suggestedPostMessage;

    /**
     * Currency in which the payment was made. Currently, one of "XTR" for Telegram Stars 
     * or "TON" for toncoins
     */
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency;

    /**
     * Optional.
     * The amount of the currency that was received by the channel in nanotoncoins; for payments in toncoins only
     */
    @JsonProperty(AMOUNT_FIELD)
    private Integer amount;

    /**
     * Optional.
     * The amount of Telegram Stars that was received by the channel; for payments in Telegram Stars only
     */
    @JsonProperty(STAR_AMOUNT_FIELD)
    private StarAmount starAmount;
}
