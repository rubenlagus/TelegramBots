package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents link to a page containing an embedded video player or a video file.
 * Alternatively, you can use input_message_content to send a message with the specified content
 * instead of the video.
 * @date 01 of January of 2016
 */
public class InlineQueryResultVideo implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String VIDEOURL_FIELD = "video_url";
    private static final String VIDEOWIDTH_FIELD = "video_width";
    private static final String VIDEOHEIGHT_FIELD = "video_height";
    private static final String VIDEODURATION_FIELD = "video_duration";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "video"; ///< Type of the result, must be "video"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Mime type of the content of video url, i.e. “text/html” or “video/mp4”
    @JsonProperty(VIDEOURL_FIELD)
    private String videoUrl; ///< A valid URL for the embedded video player or video file
    @JsonProperty(VIDEOWIDTH_FIELD)
    private Integer videoWidth; ///< Optional. Video width
    @JsonProperty(VIDEOHEIGHT_FIELD)
    private Integer videoHeight; ///< Optional. Video height
    @JsonProperty(VIDEODURATION_FIELD)
    private Integer videoDuration; ///< Optional. Video duration in seconds
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the thumbnail (jpeg only) for the video
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

    public InlineQueryResultVideo() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultVideo setId(String id) {
        this.id = id;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public InlineQueryResultVideo setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public InlineQueryResultVideo setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public InlineQueryResultVideo setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
        return this;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public InlineQueryResultVideo setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
        return this;
    }

    public Integer getVideoDuration() {
        return videoDuration;
    }

    public InlineQueryResultVideo setVideoDuration(Integer videoDuration) {
        this.videoDuration = videoDuration;
        return this;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public InlineQueryResultVideo setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultVideo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultVideo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultVideo setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultVideo setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultVideo setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (videoUrl == null || videoUrl.isEmpty()) {
            throw new TelegramApiValidationException("VideoUrl parameter can't be empty", this);
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
        return "InlineQueryResultVideo{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                ", videoDuration=" + videoDuration +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
