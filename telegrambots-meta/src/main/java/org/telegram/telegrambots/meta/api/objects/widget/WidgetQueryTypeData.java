package org.telegram.telegrambots.meta.api.objects.widget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.ton.TonWalletCard;

/** A query containing data, optionally with a wallet card. @version 10.4 */
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
public class WidgetQueryTypeData implements WidgetQueryType {
    public static final String DATA_TYPE = "data";
    @JsonProperty("type")
    private final String type = DATA_TYPE;
    @JsonProperty("wallet_card")
    private TonWalletCard walletCard;
}