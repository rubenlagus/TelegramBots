package org.telegram.telegrambots.api.objects.inlinequery.result;



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


    private final String type = "contact"; ///< Type of the result, must be "contact"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String phone_number; ///< Contact's phone number

    private String first_name; ///< Contact's first name

    private String last_name; ///< Contact's last name

    private InlineKeyboardMarkup reply_narkup; ///< Optional. Inline keyboard attached to the message

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent

    private String thumb_url; ///< Optional. URL of the thumbnail (jpeg only) for the file

    private Integer thumb_width; ///< Optional. Thumbnail width

    private Integer thumb_height; ///< Optional. Thumbnail height

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
        return phone_number;
    }

    public InlineQueryResultContact setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return first_name;
    }

    public InlineQueryResultContact setFirstName(String firstName) {
        this.first_name = firstName;
        return this;
    }

    public String getLastName() {
        return last_name;
    }

    public InlineQueryResultContact setLastName(String lastName) {
        this.last_name = lastName;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_narkup;
    }

    public InlineQueryResultContact setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_narkup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultContact setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultContact setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumb_width;
    }

    public InlineQueryResultContact setThumbWidth(Integer thumbWidth) {
        this.thumb_width = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumb_height;
    }

    public InlineQueryResultContact setThumbHeight(Integer thumbHeight) {
        this.thumb_height = thumbHeight;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (phone_number == null || phone_number.isEmpty()) {
            throw new TelegramApiValidationException("PhoneNumber parameter can't be empty", this);
        }
        if (first_name == null || first_name.isEmpty()) {
            throw new TelegramApiValidationException("FirstName parameter can't be empty", this);
        }
        if (input_message_content != null) {
            input_message_content.validate();
        }
        if (reply_narkup != null) {
            reply_narkup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultContact{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", phoneNumber='" + phone_number + '\'' +
                ", firstName='" + first_name + '\'' +
                ", thumbHeight=" + thumb_height +
                ", thumbWidth=" + thumb_width +
                ", thumbUrl='" + thumb_url + '\'' +
                ", lastName='" + last_name + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_narkup + '\'' +
                '}';
    }
}
