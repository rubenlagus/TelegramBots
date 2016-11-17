package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(TYPE_FIELD)
    private final String type = "document"; ///< Type of the result, must be "document"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(DOCUMENTURL_FIELD)
    private String documentUrl; ///< A valid URL for the file
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Mime type of the content of the file, either “application/pdf” or “application/zip”
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the document to be sent, 0-200 characters
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the file
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the thumbnail (jpeg only) for the file
    @JsonProperty(THUMBWIDTH_FIELD)
    private Integer thumbWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBHEIGHT_FIELD)
    private Integer thumbHeight; ///< Optional. Thumbnail height

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
        return documentUrl;
    }

    public InlineQueryResultDocument setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public InlineQueryResultDocument setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
        return replyMarkup;
    }

    public InlineQueryResultDocument setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultDocument setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public InlineQueryResultDocument setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public InlineQueryResultDocument setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public InlineQueryResultDocument setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (mimeType == null || mimeType.isEmpty()) {
            throw new TelegramApiValidationException("Mimetype parameter can't be empty", this);
        }
        if (documentUrl == null || documentUrl.isEmpty()) {
            throw new TelegramApiValidationException("DocumentUrl parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
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
        return "InlineQueryResultDocument{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", documentUrl='" + documentUrl + '\'' +
                ", thumbHeight=" + thumbHeight +
                ", thumbWidth=" + thumbWidth +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
