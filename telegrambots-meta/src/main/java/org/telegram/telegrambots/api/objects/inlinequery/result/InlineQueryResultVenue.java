package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a venue. By default, the venue will be sent by the user. Alternatively, you can
 * use input_message_content to send a message with the specified content instead of the venue.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultVenue implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUARE_ID_FIELD = "foursquare_id";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";


    private final String type = "venue"; ///< Type of the result, must be "venue"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String title; ///< Optional. Location title

    private Float latitude; ///< Venue latitude in degrees

    private Float longitude; ///< Venue longitude in degrees

    private String address; ///< Address of the venue

    private String foursquare_id; ///< Optional. Foursquare identifier of the venue if known

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent

    private String thumb_url; ///< Optional. URL of the thumbnail (jpeg only) for the file

    private Integer thumb_width; ///< Optional. Thumbnail width

    private Integer thumb_height; ///< Optional. Thumbnail height

    public InlineQueryResultVenue() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultVenue setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultVenue setTitle(String title) {
        this.title = title;
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public InlineQueryResultVenue setLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getLongitude() {
        return longitude;
    }

    public InlineQueryResultVenue setLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public InlineQueryResultVenue setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getFoursquareId() {
        return foursquare_id;
    }

    public InlineQueryResultVenue setFoursquareId(String foursquareId) {
        this.foursquare_id = foursquareId;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultVenue setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultVenue setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultVenue setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumb_width;
    }

    public InlineQueryResultVenue setThumbWidth(Integer thumbWidth) {
        this.thumb_width = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumb_height;
    }

    public InlineQueryResultVenue setThumbHeight(Integer thumbHeight) {
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
        if (address == null || address.isEmpty()) {
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
        return "InlineQueryResultVenue{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mimeType='" + latitude + '\'' +
                ", documentUrl='" + longitude + '\'' +
                ", thumbHeight=" + thumb_height +
                ", thumbWidth=" + thumb_width +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", foursquareId='" + foursquare_id + '\'' +
                ", address='" + address + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
