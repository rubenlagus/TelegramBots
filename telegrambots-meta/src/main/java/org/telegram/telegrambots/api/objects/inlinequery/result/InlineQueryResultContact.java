package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a contact with a phone number. By default, this contact will be sent by the
 * user. Alternatively, you can use input_message_content to send a message with the specified
 * content instead of the contact.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
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

    @JsonProperty(TYPE_FIELD)
    private final String type = "contact"; ///< Type of the result, must be "contact"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(PHONE_NUMBER_FIELD)
    private String phoneNumber; ///< Contact's phone number
    @JsonProperty(FIRST_NAME_FIELD)
    private String firstName; ///< Contact's first name
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName; ///< Contact's last name
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

    public InlineQueryResultContact() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultContact setId(String id) {
        this.id = id;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InlineQueryResultContact setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public InlineQueryResultContact setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InlineQueryResultContact setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultContact setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultContact setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public InlineQueryResultContact setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public InlineQueryResultContact setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public InlineQueryResultContact setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
        return this;
    }

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

    @Override
    public String toString() {
        return "InlineQueryResultContact{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", thumbHeight=" + thumbHeight +
                ", thumbWidth=" + thumbWidth +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", lastName='" + lastName + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
