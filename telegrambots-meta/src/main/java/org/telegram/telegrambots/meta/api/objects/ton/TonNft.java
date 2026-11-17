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
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGift;

import java.util.List;

/** @version 10.4 An NFT in the TON blockchain. */
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
public class TonNft implements BotApiObject {
    @JsonProperty("address")
    @NonNull
    private String address;
    @JsonProperty("collection_address")
    private String collectionAddress;
    @JsonProperty("index")
    @NonNull
    private String index;
    @JsonProperty("name")
    @NonNull
    private String name;
    @JsonProperty("description")
    @NonNull
    private String description;
    @JsonProperty("attributes")
    private List<TonNftAttribute> attributes;
    @JsonProperty("gift")
    private UniqueGift gift;
}