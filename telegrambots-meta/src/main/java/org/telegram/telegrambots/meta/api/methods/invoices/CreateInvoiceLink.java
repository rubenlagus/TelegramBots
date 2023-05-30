package org.telegram.telegrambots.meta.api.methods.invoices;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 6.1
 * Use this method to create a link for an invoice.
 * Retruns the created invoice link as String on success
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CreateInvoiceLink extends BotApiMethod<String> {
    public static final String PATH = "createInvoiceLink";

    public static final String TITLE_FIELD = "title";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String PAYLOAD_FIELD = "payload";
    public static final String PROVIDER_TOKEN_FIELD = "provider_token";
    public static final String CURRENCY_FIELD = "currency";
    public static final String PRICES_FIELD = "prices";
    public static final String MAXTIPAMOUNT_FIELD = "max_tip_amount";
    public static final String SUGGESTEDTIPAMOUNTS_FIELD = "suggested_tip_amounts";
    public static final String PROVIDER_DATA_FIELD = "provider_data";
    public static final String PHOTO_URL_FIELD = "photo_url";
    public static final String PHOTO_SIZE_FIELD = "photo_size";
    public static final String PHOTO_WIDTH_FIELD = "photo_width";
    public static final String PHOTO_HEIGHT_FIELD = "photo_height";
    public static final String NEED_NAME_FIELD = "need_name";
    public static final String NEED_PHONE_NUMBER_FIELD = "need_phone_number";
    public static final String NEED_EMAIL_FIELD = "need_email";
    public static final String NEED_SHIPPING_ADDRESS_FIELD = "need_shipping_address";
    public static final String SEND_PHONE_NUMBER_TO_PROVIDER_FIELD = "send_phone_number_to_provider";
    public static final String SEND_EMAIL_TO_PROVIDER_FIELD = "send_email_to_provider";
    public static final String IS_FLEXIBLE_FIELD = "is_flexible";

    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Product name, 1-32 characters
    @JsonProperty(DESCRIPTION_FIELD)
    @NonNull
    private String description; ///< 	Product description, 1-255 characters
    @JsonProperty(PAYLOAD_FIELD)
    @NonNull
    private String payload; ///< Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
    @JsonProperty(PROVIDER_TOKEN_FIELD)
    @NonNull
    private String providerToken; ///< Payment provider token, obtained via BotFather
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency; ///< Three-letter ISO 4217 currency code, see more on currencies
    @JsonProperty(PRICES_FIELD)
    @NonNull
    @Singular
    private List<LabeledPrice> prices; ///< Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
    /**
     * Optional
     * URL of the product photo for the invoice.
     * Can be a photo of the goods or a marketing image for a service.
     */
    @JsonProperty(PHOTO_URL_FIELD)
    private String photoUrl;
    @JsonProperty(PHOTO_SIZE_FIELD)
    private Integer photoSize; ///< Optional	Photo size in bytes
    @JsonProperty(PHOTO_WIDTH_FIELD)
    private Integer photoWidth; ///< Optional	Photo width
    @JsonProperty(PHOTO_HEIGHT_FIELD)
    private Integer photoHeight; ///< Optional	Photo height
    @JsonProperty(NEED_NAME_FIELD)
    private Boolean needName; ///< Optional	Pass True, if you require the user's full name to complete the order
    @JsonProperty(NEED_PHONE_NUMBER_FIELD)
    private Boolean needPhoneNumber; ///< Optional	Pass True, if you require the user's phone number to complete the order
    @JsonProperty(NEED_EMAIL_FIELD)
    private Boolean needEmail; ///< Optional	Pass True, if you require the user's email address to complete the order
    @JsonProperty(NEED_SHIPPING_ADDRESS_FIELD)
    private Boolean needShippingAddress; ///< Optional	Pass True, if you require the user's shipping address to complete the order
    @JsonProperty(IS_FLEXIBLE_FIELD)
    private Boolean isFlexible; ///< Optional	Pass True, if the final price depends on the shipping method
    @JsonProperty(SEND_PHONE_NUMBER_TO_PROVIDER_FIELD)
    private Boolean sendPhoneNumberToProvider;  ///< Optional	Pass True, if the user's phone number should be sent to the provider
    @JsonProperty(SEND_EMAIL_TO_PROVIDER_FIELD)
    private Boolean sendEmailToProvider;    ///< Optional	Pass True, if the user's email address should be sent to the provider
    /**
     * Optional
     * JSON-serialized data about the invoice, which will be shared with the payment provider.
     *
     * @apiNote A detailed description of required fields should be provided by the payment provider.
     */
    @JsonProperty(PROVIDER_DATA_FIELD)
    private String providerData;
    /**
     * The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double).
     * For example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145.
     * Defaults to 0
     */
    @JsonProperty(MAXTIPAMOUNT_FIELD)
    private Integer maxTipAmount;
    /**
     * Optional	A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not float/double).
     * At most 4 suggested tip amounts can be specified.
     * The suggested tip amounts must be positive, passed in a strictly increased order and must not exceed max_tip_amount.
     */
    @JsonProperty(SUGGESTEDTIPAMOUNTS_FIELD)
    @Singular
    private List<Integer> suggestedTipAmounts;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (StringUtils.isEmpty(title) || title.length() > 32) {
            throw new TelegramApiValidationException("Title parameter can't be empty or longer than 32 chars", this);
        }
        if (StringUtils.isEmpty(description) || description.length() > 255) {
            throw new TelegramApiValidationException("Description parameter can't be empty or longer than 255 chars", this);
        }
        if (StringUtils.isEmpty(payload)) {
            throw new TelegramApiValidationException("Payload parameter can't be empty", this);
        }
        if (StringUtils.isEmpty(providerToken)) {
            throw new TelegramApiValidationException("ProviderToken parameter can't be empty", this);
        }
        if (StringUtils.isEmpty(currency)) {
            throw new TelegramApiValidationException("Currency parameter can't be empty", this);
        }
        if (prices.isEmpty()) {
            throw new TelegramApiValidationException("Prices parameter can't be empty", this);
        } else {
            for (LabeledPrice price : prices) {
                price.validate();
            }
        }
        if (suggestedTipAmounts != null && !suggestedTipAmounts.isEmpty() && suggestedTipAmounts.size() > 4) {
            throw new TelegramApiValidationException("No more that 4 suggested tips allowed", this);
        }
    }

    @Override
    public String deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, String.class);
    }
}
