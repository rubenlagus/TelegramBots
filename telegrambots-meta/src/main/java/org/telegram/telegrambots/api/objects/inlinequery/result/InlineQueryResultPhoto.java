package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(TYPE_FIELD)
    private final String type = "photo"; ///< Type of the result, must be “photo”
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(PHOTOURL_FIELD)
    private String photoUrl; ///< A valid URL of the photo. Photo size must not exceed 5MB
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Optional. MIME type of the photo, defaults to image/jpeg
    @JsonProperty(PHOTOWIDTH_FIELD)
    private Integer photoWidth; ///< Optional. Width of the photo
    @JsonProperty(PHOTOHEIGHT_FIELD)
    private Integer photoHeight; ///< Optional. Height of the photo
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the thumbnail for the photo
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the photo to be sent
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the photo
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

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
        return photoUrl;
    }

    public InlineQueryResultPhoto setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public InlineQueryResultPhoto setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public Integer getPhotoWidth() {
        return photoWidth;
    }

    public InlineQueryResultPhoto setPhotoWidth(Integer photoWidth) {
        this.photoWidth = photoWidth;
        return this;
    }

    public Integer getPhotoHeight() {
        return photoHeight;
    }

    public InlineQueryResultPhoto setPhotoHeight(Integer photoHeight) {
        this.photoHeight = photoHeight;
        return this;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public InlineQueryResultPhoto setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
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
        return inputMessageContent;
    }

    public InlineQueryResultPhoto setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultPhoto setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (photoUrl == null || photoUrl.isEmpty()) {
            throw new TelegramApiValidationException("PhotoUrl parameter can't be empty", this);
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
        return "InlineQueryResultPhoto{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", photoWidth=" + photoWidth +
                ", photoHeight=" + photoHeight +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
