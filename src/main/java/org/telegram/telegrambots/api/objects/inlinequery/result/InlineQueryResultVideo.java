package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;

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
    @JsonProperty(TYPE_FIELD)
    private static final String type = "video"; ///< Type of the result, must be "video"
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

    public static String getType() {
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
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(TYPE_FIELD, type);
        jsonObject.put(ID_FIELD, this.id);
        jsonObject.put(VIDEOURL_FIELD, this.videoUrl);
        if (mimeType != null) {
            jsonObject.put(MIMETYPE_FIELD, this.mimeType);
        }
        if (videoWidth != null) {
            jsonObject.put(VIDEOWIDTH_FIELD, this.videoWidth);
        }
        if (videoHeight != null) {
            jsonObject.put(VIDEOHEIGHT_FIELD, this.videoHeight);
        }
        if (videoDuration != null) {
            jsonObject.put(VIDEODURATION_FIELD, this.videoDuration);
        }
        if (thumbUrl != null) {
            jsonObject.put(THUMBURL_FIELD, this.thumbUrl);
        }
        if (title != null) {
            jsonObject.put(TITLE_FIELD, this.title);
        }
        if (description != null) {
            jsonObject.put(DESCRIPTION_FIELD, this.description);
        }
        if (caption != null) {
            jsonObject.put(CAPTION_FIELD, caption);
        }
        if (replyMarkup != null) {
            jsonObject.put(REPLY_MARKUP_FIELD, replyMarkup.toJson());
        }
        if (inputMessageContent != null) {
            jsonObject.put(INPUTMESSAGECONTENT_FIELD, inputMessageContent.toJson());
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TYPE_FIELD, type);
        gen.writeStringField(ID_FIELD, id);
        gen.writeStringField(VIDEOURL_FIELD, this.videoUrl);
        if (mimeType != null) {
            gen.writeStringField(MIMETYPE_FIELD, this.mimeType);
        }
        if (videoWidth != null) {
            gen.writeNumberField(VIDEOWIDTH_FIELD, this.videoWidth);
        }
        if (videoHeight != null) {
            gen.writeNumberField(VIDEOHEIGHT_FIELD, this.videoHeight);
        }
        if (videoDuration != null) {
            gen.writeNumberField(VIDEODURATION_FIELD, this.videoDuration);
        }
        if (thumbUrl != null) {
            gen.writeStringField(THUMBURL_FIELD, this.thumbUrl);
        }
        if (title != null) {
            gen.writeStringField(TITLE_FIELD, this.title);
        }
        if (description != null) {
            gen.writeStringField(DESCRIPTION_FIELD, this.description);
        }
        if (caption != null) {
            gen.writeStringField(CAPTION_FIELD, caption);
        }
        if (replyMarkup != null) {
            gen.writeObjectField(REPLY_MARKUP_FIELD, replyMarkup);
        }
        if (inputMessageContent != null) {
            gen.writeObjectField(INPUTMESSAGECONTENT_FIELD, inputMessageContent);
        }

        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
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
