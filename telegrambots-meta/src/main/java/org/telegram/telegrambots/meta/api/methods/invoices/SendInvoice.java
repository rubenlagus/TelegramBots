package org.telegram.telegrambots.meta.api.methods.invoices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
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
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendInvoice extends BotApiMethodMessage {
    public static final String PATH = "sendinvoice";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
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
    private static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    private static final String MAX_TIP_AMOUNT_FIELD = "max_tip_amount";
    private static final String SUGGESTED_TIP_AMOUNTS_FIELD = "suggested_tip_amounts";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    private static final String MESSAGE_EFFECT_ID_FIELD = "message_effect_id";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    /**
     * Product name
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;
    /**
     * Product description
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
     * Payment provider token, obtained via @BotFather.
     * @apiNote Pass an empty string for payments in Telegram Stars.
     */
    @JsonProperty(PROVIDER_TOKEN_FIELD)
    private String providerToken;
    /**
     * Optional
     * Unique deep-linking parameter. If left empty, forwarded copies of the sent message will have a Pay button,
     * allowing multiple users to pay directly from the forwarded message, using the same invoice.
     * If non-empty, forwarded copies of the sent message will have a URL button with a deep link to the bot (instead of a Pay button),
     * with the value used as the start parameter
     */
    @JsonProperty(START_PARAMETER_FIELD)
    private String startParameter;
    /**
     * Three-letter ISO 4217 currency code, see more on currencies.
     * @apiNote Pass “XTR” for payments in Telegram Stars.
     */
    @JsonProperty(CURRENCY_FIELD)
    @NonNull
    private String currency; ///< 3-letter ISO 4217 currency code
    /**
     * Price breakdown, a JSON-serialized list of components
     * (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.).
     * @apiNote Must contain exactly one item for payments in Telegram Stars.
     */
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
    /**
     * Optional.
     * Photo size
     */
    @JsonProperty(PHOTO_SIZE_FIELD)
    private Integer photoSize;
    /**
     * Optional.
     * Photo width
     */
    @JsonProperty(PHOTO_WIDTH_FIELD)
    private Integer photoWidth;
    /**
     * Optional.
     * Photo height
     */
    @JsonProperty(PHOTO_HEIGHT_FIELD)
    private Integer photoHeight;
    /**
     * Optional
     * Pass True if you require the user's full name to complete the order.
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(NEED_NAME_FIELD)
    private Boolean needName;
    /**
     * Optional
     * Pass True if you require the user's phone number to complete the order.
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(NEED_PHONE_NUMBER_FIELD)
    private Boolean needPhoneNumber;
    /**
     * Optional
     * Pass True if you require the user's email address to complete the order.
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(NEED_EMAIL_FIELD)
    private Boolean needEmail;
    /**
     * Optional
     * Pass True if you require the user's shipping address to complete the order.
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(NEED_SHIPPING_ADDRESS_FIELD)
    private Boolean needShippingAddress;
    /**
     * Optional
     * Pass True if the final price depends on the shipping method.
     *
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(IS_FLEXIBLE_FIELD)
    private Boolean isFlexible;
    /**
     * Optional.
     * Sends the message silently. Users will receive a notification with no sound.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;
    /**
     * Optional.
     * If the message is a reply, ID of the original message
     */
    @JsonProperty(REPLY_TO_MESSAGE_ID_FIELD)
    private Integer replyToMessageId;
    /**
     * Optional
     * Pass True if the user's phone number should be sent to the provider.
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(SEND_PHONE_NUMBER_TO_PROVIDER_FIELD)
    private Boolean sendPhoneNumberToProvider;
    /**
     * Optional
     * Pass True if the user's email address should be sent to the provider.
     * @apiNote Ignored for payments in Telegram Stars.
     */
    @JsonProperty(SEND_EMAIL_TO_PROVIDER_FIELD)
    private Boolean sendEmailToProvider;
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
    /**
     * Optional
     * Pass True, if the message should be sent even if the specified replied-to message is not found
     */
    @JsonProperty(ALLOW_SENDING_WITHOUT_REPLY_FIELD)
    private Boolean allowSendingWithoutReply;
    /**
     * Optional
     * The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double).
     * For example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145. See the exp parameter in currencies.json,
     * it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
     * Defaults to 0.
     * @apiNote Not supported for payments in Telegram Stars.
     */
    @JsonProperty(MAX_TIP_AMOUNT_FIELD)
    private Integer maxTipAmount;
    /**
     * A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not float/double).
     * At most 4 suggested tip amounts can be specified.
     * The suggested tip amounts must be positive, passed in a strictly increased order and must not exceed max_tip_amount.
     */
    @JsonProperty(SUGGESTED_TIP_AMOUNTS_FIELD)
    @Singular
    private List<Integer> suggestedTipAmounts;
    /**
     *  Optional.
     *  Protects the contents of sent messages from forwarding and saving
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;
    /**
     * Optional
     * Unique identifier of the message effect to be added to the message
     */
    @JsonProperty(MESSAGE_EFFECT_ID_FIELD)
    private String messageEffectId;

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
        if (StringUtils.isEmpty(title) || title.length() > 32) {
            throw new TelegramApiValidationException("Title parameter can't be empty or longer than 32 chars", this);
        }
        if (StringUtils.isEmpty(description) || description.length() > 255) {
            throw new TelegramApiValidationException("Description parameter can't be empty or longer than 255 chars", this);
        }
        if (StringUtils.isEmpty(payload)) {
            throw new TelegramApiValidationException("Payload parameter can't be empty", this);
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

    public static abstract class SendInvoiceBuilder<C extends SendInvoice, B extends SendInvoiceBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendInvoiceBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
