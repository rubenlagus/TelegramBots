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

import java.util.List;

/** @version 10.4 A TON wallet card displayed on a user's profile. */
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
public class TonWalletCard implements BotApiObject {
    @JsonProperty("address")
    @NonNull
    private String address;
    /** Balance in the smallest cryptocurrency units, as a decimal number. */
    @JsonProperty("amount")
    @NonNull
    private String amount;
    @JsonProperty("stickers")
    @NonNull
    private List<TonWalletCardSticker> stickers;
    @JsonProperty("background_nft_address")
    private String backgroundNftAddress;
}