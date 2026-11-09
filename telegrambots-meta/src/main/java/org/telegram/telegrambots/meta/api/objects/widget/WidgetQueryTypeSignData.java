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

/** A query containing data signed by a TON wallet. @version 10.4 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WidgetQueryTypeSignData implements WidgetQueryType {
    public static final String SIGN_DATA_TYPE = "sign_data";
    @JsonProperty("type")
    private final String type = SIGN_DATA_TYPE;
    @JsonProperty("wallet")
    @NonNull
    private WidgetQueryWallet wallet;
    @JsonProperty("sign_data")
    @NonNull
    private String signData;
}