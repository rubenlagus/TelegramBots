package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

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
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a contact with a phone number. By default, this contact will be sent by the
 * user. Alternatively, you can use input_message_content to send a message with the specified
 * content instead of the contact.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineQueryResultContact implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String PHONE_NUMBER_FIELD = "phone_number";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";
    private static final String VCARD_FIELD = "vcard";

    @JsonProperty(TYPE_FIELD)
    private final String type = "contact"; ///< Type of the result, must be "contact"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(PHONE_NUMBER_FIELD)
    @NonNull
    private String phoneNumber; ///< Contact's phone number
    @JsonProperty(FIRST_NAME_FIELD)
    @NonNull
    private String firstName; ///< Contact's first name
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName; ///< Optional. Contact's last name
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the thumbnail (jpeg only) for the file
    @JsonProperty(THUMBWIDTH_FIELD)
    private Integer thumbWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBHEIGHT_FIELD)
    private Integer thumbHeight; ///< Optional. Thumbnail height
    @JsonProperty(VCARD_FIELD)
    private String vCard; ///< Optional. Additional data about the contact in the form of a vCard, 0-2048 bytes

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new TelegramApiValidationException("PhoneNumber parameter can't be empty", this);
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new TelegramApiValidationException("FirstName parameter can't be empty", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
