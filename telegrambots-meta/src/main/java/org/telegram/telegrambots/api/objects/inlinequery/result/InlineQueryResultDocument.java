package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a file. By default, this file will be sent by the user with an
 * optional caption. Alternatively, you can use input_message_content to send a message with the
 * specified content instead of the file.
 * @note Currently, only .PDF and .ZIP files can be sent using this method.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 01 of January of 2016
 */
public class InlineQueryResultDocument implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DOCUMENTURL_FIELD = "document_url";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";


    private final String type = "document"; ///< Type of the result, must be "document"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String title; ///< Optional. Title for the result

    private String document_url; ///< A valid URL for the file

    private String mime_type; ///< Mime type of the content of the file, either “application/pdf” or “application/zip”

    private String description; ///< Optional. Short description of the result

    private String caption; ///< Optional. Caption of the document to be sent, 0-200 characters

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the file

    private String thumb_url; ///< Optional. URL of the thumbnail (jpeg only) for the file

    private Integer thumb_width; ///< Optional. Thumbnail width

    private Integer thumb_height; ///< Optional. Thumbnail height

    public InlineQueryResultDocument() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultDocument setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDocumentUrl() {
        return document_url;
    }

    public InlineQueryResultDocument setDocumentUrl(String documentUrl) {
        this.document_url = documentUrl;
        return this;
    }

    public String getMimeType() {
        return mime_type;
    }

    public InlineQueryResultDocument setMimeType(String mimeType) {
        this.mime_type = mimeType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultDocument setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultDocument setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultDocument setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultDocument setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultDocument setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumb_width;
    }

    public InlineQueryResultDocument setThumbWidth(Integer thumbWidth) {
        this.thumb_width = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumb_height;
    }

    public InlineQueryResultDocument setThumbHeight(Integer thumbHeight) {
        this.thumb_height = thumbHeight;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (mime_type == null || mime_type.isEmpty()) {
            throw new TelegramApiValidationException("Mimetype parameter can't be empty", this);
        }
        if (document_url == null || document_url.isEmpty()) {
            throw new TelegramApiValidationException("DocumentUrl parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
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
        return "InlineQueryResultDocument{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mimeType='" + mime_type + '\'' +
                ", documentUrl='" + document_url + '\'' +
                ", thumbHeight=" + thumb_height +
                ", thumbWidth=" + thumb_width +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
