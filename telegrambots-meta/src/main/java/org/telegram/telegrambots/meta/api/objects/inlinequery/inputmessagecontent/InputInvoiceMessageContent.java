package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * Represents the content of a text message to be sent as the result of an inline query.
 * @author Ruben Bermudez
 * @version 1.0
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class InputInvoiceMessageContent implements InputMessageContent {

    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String PAYLOAD_FIELD = "payload";
    private static final String PROVIDERTOKEN_FIELD = "provider_token";
    private static final String CURRENCY_FIELD = "currency";
    private static final String PRICES_FIELD = "prices";
    private static final String MAXTIPAMOUNT_FIELD = "max_tip_amount";
    private static final String SUGGESTEDTIPAMOUNTS_FIELD = "suggested_tip_amounts";
    private static final String PROVIDERDATA_FIELD = "provider_data";
    private static final String PHOTOURL_FIELD = "photo_url";
    private static final String PHOTOSIZE_FIELD = "photo_size";
    private static final String PHOTOWIDTH_FIELD = "photo_width";
    private static final String PHOTOHEIGHT_FIELD = "photo_height";
    private static final String NEEDNAME_FIELD = "need_name";
    private static final String NEEDPHONENUMBER_FIELD = "need_phone_number";
    private static final String NEEDEMAIL_FIELD = "need_email";
    private static final String NEEDSHIPPINGADDRESS_FIELD = "need_shipping_address";
    private static final String SENDPHONENUMBERTOPROVIDER_FIELD = "send_phone_number_to_provider";
    private static final String SENDEMAILTOPROVIDER_FIELD = "send_email_to_provider";
    private static final String ISFLEXIBLE_FIELD = "is_flexible";

    /**
     * Product name, 1-32 characters
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;
    /**
     * Product description, 1-255 characters
     */
    @JsonProperty(DESCRIPTION_FIELD)
    @NonNull
    private String description;
    /**
     * Bot-defined invoice payload, 1-128 bytes.
     * This will not be displayed to the user, use for your internal processes.
     */
    @JsonProperty(PAYLOAD_FIELD)
    @NonNull
    private String payload;
    /**
     * Payment provider token, obtained via Botfather
     */
    @JsonProperty(PROVIDERTOKEN_FIELD)
    @NonNull
    private String providerToken;
    /**
     * Three-letter ISO 4217 currency code, see more on currencies
     */
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency;
    /**
     * Price breakdown, a JSON-serialized list of components
     * (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
     */
    @JsonProperty(PRICES_FIELD)
    @NonNull
    @Singular
    private List<LabeledPrice> prices;
    /**
     * Optional.
     * The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double).
     * For example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145.
     * Defaults to 0
     */
    @JsonProperty(MAXTIPAMOUNT_FIELD)
    private Integer maxTipAmount;
    /**
     * Optional.
     * A JSON-serialized array of suggested amounts of tip in the smallest units of the currency (integer, not float/double).
     * At most 4 suggested tip amounts can be specified.
     * The suggested tip amounts must be positive, passed in a strictly increased order and must not exceed max_tip_amount.
     */
    @JsonProperty(SUGGESTEDTIPAMOUNTS_FIELD)
    @Singular
    private List<Integer> suggestedTipAmounts;
    /**
     * Optional.
     * A JSON-serialized object for data about the invoice, which will be shared with the payment provider.
     * A detailed description of the required fields should be provided by the payment provider.
     */
    @JsonProperty(PROVIDERDATA_FIELD)
    private String providerData;
    /**
     * Optional.
     * URL of the product photo for the invoice.
     * Can be a photo of the goods or a marketing image for a service.
     * People like it better when they see what they are paying for.
     */
    @JsonProperty(PHOTOURL_FIELD)
    private String photoUrl;
    /**
     * Optional.
     * Photo size
     */
    @JsonProperty(PHOTOSIZE_FIELD)
    private Integer photoSize;
    /**
     * Optional.
     * Photo width
     */
    @JsonProperty(PHOTOWIDTH_FIELD)
    private Integer photoWidth;
    /**
     * Optional.
     * Photo height
     */
    @JsonProperty(PHOTOHEIGHT_FIELD)
    private Integer photoHeight;
    /**
     * Optional.
     * Pass True, if you require the user's full name to complete the order
     */
    @JsonProperty(NEEDNAME_FIELD)
    private Boolean needName;
    /**
     * Optional.
     * Pass True, if you require the user's phone number to complete the order
     */
    @JsonProperty(NEEDPHONENUMBER_FIELD)
    private Boolean needPhoneNumber;
    /**
     * Optional.
     * Pass True, if you require the user's email address to complete the order
     */
    @JsonProperty(NEEDEMAIL_FIELD)
    private Boolean needEmail;
    /**
     * Optional.
     * Pass True, if you require the user's shipping address to complete the order
     */
    @JsonProperty(NEEDSHIPPINGADDRESS_FIELD)
    private Boolean needShippingAddress;
    /**
     * Optional.
     * Pass True, if user's phone number should be sent to provider
     */
    @JsonProperty(SENDPHONENUMBERTOPROVIDER_FIELD)
    private Boolean sendPhoneNumberToProvider;
    /**
     * Optional.
     * Pass True, if user's email address should be sent to provider
     */
    @JsonProperty(SENDEMAILTOPROVIDER_FIELD)
    private Boolean sendEmailToProvider;
    /**
     * Optional.
     * Pass True, if the final price depends on the shipping method
     */
    @JsonProperty(ISFLEXIBLE_FIELD)
    private Boolean isFlexible;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (StringUtils.isEmpty(title) || title.length() > 32) {
            throw new TelegramApiValidationException("Title parameter must be between 1 and 32 characters", this);
        }
        if (StringUtils.isEmpty(description) || description.length() > 32) {
            throw new TelegramApiValidationException("Description parameter must be between 1 and 255 characters", this);
        }
        if (StringUtils.isEmpty(payload) || payload.length() > 32) {
            throw new TelegramApiValidationException("Payload parameter must be between 1 and 128 characters", this);
        }
        if (StringUtils.isEmpty(providerToken)) {
            throw new TelegramApiValidationException("ProviderToken parameter must not be empty", this);
        }
        if (StringUtils.isEmpty(currency)) {
            throw new TelegramApiValidationException("Currency parameter must not be empty", this);
        }
        if (prices == null || prices.isEmpty()) {
            throw new TelegramApiValidationException("Prices parameter must not be empty", this);
        }
        for (LabeledPrice price : prices) {
            price.validate();
        }
        if (suggestedTipAmounts != null && !suggestedTipAmounts.isEmpty() && suggestedTipAmounts.size() > 4) {
            throw new TelegramApiValidationException("Only up to 4 suggested tip amounts are allowed", this);
        }
    }
}
