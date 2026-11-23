package org.telegram.telegrambots.meta.api.objects.widget;

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

/** A TON wallet used in a widget query. @version 10.4 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WidgetQueryWallet implements BotApiObject {
    @JsonProperty("address")
    @NonNull
    private String address;
    @JsonProperty("amount")
    @NonNull
    private String amount;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("state_init")
    private String stateInit;
}