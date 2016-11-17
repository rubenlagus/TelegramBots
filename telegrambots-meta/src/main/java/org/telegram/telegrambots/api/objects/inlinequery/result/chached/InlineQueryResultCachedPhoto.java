package org.telegram.telegrambots.api.objects.inlinequery.result.chached;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a photo stored on the Telegram servers. By default, this photo will
 * be sent by the user with an optional caption. Alternatively, you can use input_message_content to
 * send a message with the specified content instead of the photo.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedPhoto implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String PHOTOFILEID_FIELD = "photo_file_id";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "photo"; ///< Type of the result, must be “photo”
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(PHOTOFILEID_FIELD)
    private String photoFileId; ///< A valid file identifier of the photo
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

    public InlineQueryResultCachedPhoto() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedPhoto setId(String id) {
        this.id = id;
        return this;
    }

    public String getPhotoFileId() {
        return photoFileId;
    }

    public InlineQueryResultCachedPhoto setPhotoFileId(String photoFileId) {
        this.photoFileId = photoFileId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedPhoto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultCachedPhoto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedPhoto setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultCachedPhoto setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedPhoto setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (photoFileId == null || photoFileId.isEmpty()) {
            throw new TelegramApiValidationException("PhotoFileId parameter can't be empty", this);
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
        return "InlineQueryResultCachedPhoto{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", photoFileId='" + photoFileId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
