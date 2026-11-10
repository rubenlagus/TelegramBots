package org.telegram.telegrambots.meta.api.objects.ton;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/** @version 10.4 A service message about a transfer with a TON wallet. */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TonWalletTransfer implements BotApiObject {
    /** May be empty if the transfer hasn't been sent yet. */
    @JsonProperty("transaction_id")
    @NonNull
    private String transactionId;
    @JsonProperty("peer_address")
    @NonNull
    private String peerAddress;
    /** Amount in the smallest units, negative for outgoing transfers. */
    @JsonProperty("amount")
    @NonNull
    private String amount;
    /** Optional; omitted if encrypted. */
    @JsonProperty("comment")
    private String comment;
}