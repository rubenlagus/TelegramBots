package org.telegram.telegrambots.meta.api.objects.payments.transactionpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

/**
 * @author Ruben Bermudez
 * @version 8.3
 *
 * Describes a transaction with a chat.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionPartnerChat implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String CHAT_FIELD = "chat";
    private static final String GIFT_FIELD = "gift";

    /**
     * Type of the transaction partner, always “chat”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "chat";
    /**
     * Information about the chat
     */
    @JsonProperty(CHAT_FIELD)
    @NonNull
    private Chat chat;
    /**
     * Optional.
     * The gift sent to the chat by the bot
     */
    @JsonProperty(GIFT_FIELD)
    private String gift;
}
