package org.telegram.telegrambots.meta.api.objects.widget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/** A query notifying the bot of a transaction sent from a TON wallet. @version 10.4 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WidgetQueryTypeSentTransaction implements WidgetQueryType {
    public static final String SENT_TRANSACTION_TYPE = "sent_transaction";
    @JsonProperty("type")
    private final String type = SENT_TRANSACTION_TYPE;
    @JsonProperty("wallet")
    @NonNull
    private WidgetQueryWallet wallet;
    @JsonProperty("msg_hash")
    @NonNull
    private String msgHash;
}