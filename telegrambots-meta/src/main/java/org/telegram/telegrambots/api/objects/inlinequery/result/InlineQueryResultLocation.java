package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a location on a map. By default, the location will be sent by the user.
 * Alternatively, you can use input_message_content to send a message with the specified content
 * instead of the location.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultLocation implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";


    private final String type = "location"; ///< Type of the result, must be "location"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String title; ///< Optional. Location title

    private Float latitude; ///< Location latitude in degrees

    private Float longitude; ///< Location longitude in degrees

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent

    private String thumb_url; ///< Optional. URL of the thumbnail (jpeg only) for the file

    private Integer thumb_width; ///< Optional. Thumbnail width

    private Integer thumb_height; ///< Optional. Thumbnail height

    public InlineQueryResultLocation() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultLocation setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultLocation setTitle(String title) {
        this.title = title;
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public InlineQueryResultLocation setLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getLongitude() {
        return longitude;
    }

    public InlineQueryResultLocation setLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultLocation setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultLocation setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultLocation setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumb_width;
    }

    public InlineQueryResultLocation setThumbWidth(Integer thumbWidth) {
        this.thumb_width = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumb_height;
    }

    public InlineQueryResultLocation setThumbHeight(Integer thumbHeight) {
        this.thumb_height = thumbHeight;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude parameter can't be empty", this);
        }
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude parameter can't be empty", this);
        }
        if (input_message_content != null) {
            input_message_content.validate();
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultLocation{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mimeType='" + latitude + '\'' +
                ", documentUrl='" + longitude + '\'' +
                ", thumbHeight=" + thumb_height +
                ", thumbWidth=" + thumb_width +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
