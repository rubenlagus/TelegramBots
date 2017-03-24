package org.telegram.telegrambots.api.objects.inlinequery.result;



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


    private final String type = "video"; ///< Type of the result, must be "video"

    private String id; ///< Unique identifier of this result

    private String mime_type; ///< Mime type of the content of video url, i.e. “text/html” or “video/mp4”

    private String video_url; ///< A valid URL for the embedded video player or video file

    private Integer video_width; ///< Optional. Video width

    private Integer video_height; ///< Optional. Video height

    private Integer video_duration; ///< Optional. Video duration in seconds

    private String thumb_url; ///< Optional. URL of the thumbnail (jpeg only) for the video

    private String title; ///< Optional. Title for the result

    private String description; ///< Optional. Short description of the result

    private String caption; ///< Optional. Caption of the video to be sent, 0-200 characters

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the photo

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

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
        return mime_type;
    }

    public InlineQueryResultVideo setMimeType(String mimeType) {
        this.mime_type = mimeType;
        return this;
    }

    public String getVideoUrl() {
        return video_url;
    }

    public InlineQueryResultVideo setVideoUrl(String videoUrl) {
        this.video_url = videoUrl;
        return this;
    }

    public Integer getVideoWidth() {
        return video_width;
    }

    public InlineQueryResultVideo setVideoWidth(Integer videoWidth) {
        this.video_width = videoWidth;
        return this;
    }

    public Integer getVideoHeight() {
        return video_height;
    }

    public InlineQueryResultVideo setVideoHeight(Integer videoHeight) {
        this.video_height = videoHeight;
        return this;
    }

    public Integer getVideoDuration() {
        return video_duration;
    }

    public InlineQueryResultVideo setVideoDuration(Integer videoDuration) {
        this.video_duration = videoDuration;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultVideo setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
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
        return input_message_content;
    }

    public InlineQueryResultVideo setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultVideo setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (video_url == null || video_url.isEmpty()) {
            throw new TelegramApiValidationException("VideoUrl parameter can't be empty", this);
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
        return "InlineQueryResultVideo{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mimeType='" + mime_type + '\'' +
                ", videoUrl='" + video_url + '\'' +
                ", videoWidth=" + video_width +
                ", videoHeight=" + video_height +
                ", videoDuration=" + video_duration +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
