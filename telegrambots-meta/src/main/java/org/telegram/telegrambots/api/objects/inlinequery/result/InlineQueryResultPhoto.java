package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a photo. By default, this photo will be sent by the user with
 * optional caption. Alternatively, you can use input_message_content to send a message with the
 * specified content instead of the photo.
 * @date 01 of January of 2016
 */
public class InlineQueryResultPhoto implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String PHOTOURL_FIELD = "photo_url";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String PHOTOWIDTH_FIELD = "photo_width";
    private static final String PHOTOHEIGHT_FIELD = "photo_height";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";


    private final String type = "photo"; ///< Type of the result, must be “photo”

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String photo_url; ///< A valid URL of the photo. Photo size must not exceed 5MB

    private String mime_type; ///< Optional. MIME type of the photo, defaults to image/jpeg

    private Integer photo_width; ///< Optional. Width of the photo

    private Integer photo_height; ///< Optional. Height of the photo

    private String thumb_url; ///< Optional. URL of the thumbnail for the photo

    private String title; ///< Optional. Title for the result

    private String description; ///< Optional. Short description of the result

    private String caption; ///< Optional. Caption of the photo to be sent

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the photo

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultPhoto() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultPhoto setId(String id) {
        this.id = id;
        return this;
    }

    public String getPhotoUrl() {
        return photo_url;
    }

    public InlineQueryResultPhoto setPhotoUrl(String photoUrl) {
        this.photo_url = photoUrl;
        return this;
    }

    public String getMimeType() {
        return mime_type;
    }

    public InlineQueryResultPhoto setMimeType(String mimeType) {
        this.mime_type = mimeType;
        return this;
    }

    public Integer getPhotoWidth() {
        return photo_width;
    }

    public InlineQueryResultPhoto setPhotoWidth(Integer photoWidth) {
        this.photo_width = photoWidth;
        return this;
    }

    public Integer getPhotoHeight() {
        return photo_height;
    }

    public InlineQueryResultPhoto setPhotoHeight(Integer photoHeight) {
        this.photo_height = photoHeight;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultPhoto setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultPhoto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultPhoto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultPhoto setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultPhoto setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultPhoto setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (photo_url == null || photo_url.isEmpty()) {
            throw new TelegramApiValidationException("PhotoUrl parameter can't be empty", this);
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
        return "InlineQueryResultPhoto{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", photoUrl='" + photo_url + '\'' +
                ", mimeType='" + mime_type + '\'' +
                ", photoWidth=" + photo_width +
                ", photoHeight=" + photo_height +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
