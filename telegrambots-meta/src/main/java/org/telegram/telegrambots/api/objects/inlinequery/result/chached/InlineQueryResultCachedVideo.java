package org.telegram.telegrambots.api.objects.inlinequery.result.chached;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a video file stored on the Telegram servers. By default, this video
 * file will be sent by the user with an optional caption. Alternatively, you can use
 * input_message_content to send a message with the specified content instead of the video.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedVideo implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String VIDEO_FILE_ID_FIELD = "video_file_id";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "video"; ///< Type of the result, must be "video"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    @JsonProperty(VIDEO_FILE_ID_FIELD)
    private String videoFileId; ///< A valid file identifier for the video file
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the video to be sent, 0-200 characters
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the photo
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultCachedVideo() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedVideo setId(String id) {
        this.id = id;
        return this;
    }

    public String getVideoFileId() {
        return videoFileId;
    }

    public InlineQueryResultCachedVideo setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedVideo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultCachedVideo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedVideo setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultCachedVideo setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedVideo setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (videoFileId == null || videoFileId.isEmpty()) {
            throw new TelegramApiValidationException("VideoFileId parameter can't be empty", this);
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
        return "InlineQueryResultCachedVideo{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", caption='" + caption + '\'' +
                ", videoFileId='" + videoFileId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
