package org.telegram.telegrambots.meta.api.methods.send;

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
import lombok.experimental.Tolerate;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send an invoice. On success, the sent Message is returned.
 *
 * @deprecated Use {@link org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice}
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Deprecated
public class SendInvoice extends BotApiMethodMessage {
    public static final String PATH = "sendinvoice";

    private static final String CHATID_FIELD = "chat_id";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String PAYLOAD_FIELD = "payload";
    private static final String PROVIDER_TOKEN_FIELD = "provider_token";
    private static final String START_PARAMETER_FIELD = "start_parameter";
    private static final String CURRENCY_FIELD = "currency";
    private static final String PRICES_FIELD = "prices";
    private static final String PHOTO_URL_FIELD = "photo_url";
    private static final String PHOTO_SIZE_FIELD = "photo_size";
    private static final String PHOTO_WIDTH_FIELD = "photo_width";
    private static final String PHOTO_HEIGHT_FIELD = "photo_height";
    private static final String NEED_NAME_FIELD = "need_name";
    private static final String NEED_PHONE_NUMBER_FIELD = "need_phone_number";
    private static final String NEED_EMAIL_FIELD = "need_email";
    private static final String NEED_SHIPPING_ADDRESS_FIELD = "need_shipping_address";
    private static final String SEND_PHONE_NUMBER_TO_PROVIDER_FIELD = "send_phone_number_to_provider";
    private static final String SEND_EMAIL_TO_PROVIDER_FIELD = "send_email_to_provider";
    private static final String IS_FLEXIBLE_FIELD = "is_flexible";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String REPLY_TO_MESSAGE_ID_FIELD = "reply_to_message_id";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String PROVIDER_DATA_FIELD = "provider_data";
    private static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    private static final String MAXTIPAMOUNT_FIELD = "max_tip_amount";
    private static final String SUGGESTEDTIPAMOUNTS_FIELD = "suggested_tip_amounts";
    private static final String PROTECTCONTENT_FIELD = "protect_content";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Product name
    @JsonProperty(DESCRIPTION_FIELD)
    @NonNull
    private String description; ///< Product description
    @JsonProperty(PAYLOAD_FIELD)
    @NonNull
    private String payload; ///< Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
    @JsonProperty(PROVIDER_TOKEN_FIELD)
    @NonNull
    private String providerToken; ///< Payments provider token, obtained via Botfather
    /**
     * Optional
     * Unique deep-linking parameter. If left empty, forwarded copies of the sent message will have a Pay button,
     * allowing multiple users to pay directly from the forwarded message, using the same invoice.
     * If non-empty, forwarded copies of the sent message will have a URL button with a deep link to the bot (instead of a Pay button),
     * with the value used as the start parameter
     */
    @JsonProperty(START_PARAMETER_FIELD)
    @NonNull
    private String startParameter;
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency; ///< 3-letter ISO 4217 currency code
    @JsonProperty(PRICES_FIELD)
    @NonNull
    @Singular
    private List<LabeledPrice> prices; ///< Price breakdown, a list of components (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
    /**
     * Optional. URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service.
     * People like it better when they see what they are paying for
     */
    @JsonProperty(PHOTO_URL_FIELD)
    private String photoUrl;
    @JsonProperty(PHOTO_SIZE_FIELD)
    private Integer photoSize; ///< Optional. Photo size
    @JsonProperty(PHOTO_WIDTH_FIELD)
    private Integer photoWidth; ///< Optional. Photo width
    @JsonProperty(PHOTO_HEIGHT_FIELD)
    private Integer photoHeight; ///< Optional. Photo height
    @JsonProperty(NEED_NAME_FIELD)
    private Boolean needName; ///< Optional. Pass True, if you require the user's full name to complete the order
    @JsonProperty(NEED_PHONE_NUMBER_FIELD)
    private Boolean needPhoneNumber; ///< Optional. Pass True, if you require the user's phone number to complete the order
    @JsonProperty(NEED_EMAIL_FIELD)
    private Boolean needEmail; ///< Optional. Pass True, if you require the user's email to complete the order
    @JsonProperty(NEED_SHIPPING_ADDRESS_FIELD)
    private Boolean needShippingAddress; ///< Optional. Pass True, if you require the user's shipping address to complete the order
    @JsonProperty(IS_FLEXIBLE_FIELD)
    private Boolean isFlexible; ///< Optional. Pass True, if the final price depends on the shipping method
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(REPLY_TO_MESSAGE_ID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message

    @JsonProperty(SEND_PHONE_NUMBER_TO_PROVIDER_FIELD)
    private Boolean sendPhoneNumberToProvider;      ///< Optional. Pass True, if user's phone number should be sent to provider
    @JsonProperty(SEND_EMAIL_TO_PROVIDER_FIELD)
    private Boolean sendEmailToProvider;        ///< Optional. Pass True, if user's email address should be sent to provider
    /**
     * Optional. A JSON-serialized object for an inline keyboard.
     *
     * @apiNote If empty, one 'Buy title' button will be shown. If not empty, the first button must be a Pay button.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    /**
     * Optional JSON-encoded data about the invoice, which will be shared with the payment provider.
     *
     * @apiNote A detailed description of required fields should be provided by the payment provider.
     */
    @JsonProperty(PROVIDER_DATA_FIELD)
    private String providerData;
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    /**
     * The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double).
     * For example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145.
     * Defaults to 0
     */
    @JsonProperty(MAXTIPAMOUNT_FIELD)
    private Integer maxTipAmount;
    /**
     * A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not float/double).
     * At most 4 suggested tip amounts can be specified.
     * The suggested tip amounts must be positive, passed in a strictly increased order and must not exceed max_tip_amount.
     */
    @JsonProperty(SUGGESTEDTIPAMOUNTS_FIELD)
    @Singular
    private List<Integer> suggestedTipAmounts;
    @JsonProperty(PROTECTCONTENT_FIELD)
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (StringUtils.isEmpty(chatId)) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (StringUtils.isEmpty(title)) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (StringUtils.isEmpty(description)) {
            throw new TelegramApiValidationException("Description parameter can't be empty", this);
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
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static class SendInvoiceBuilder {

        @Tolerate
        public SendInvoiceBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
