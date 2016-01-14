package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents link to a page containing an embedded video player or a video file.
 * @date 01 of January of 2016
 */
public class InlineQueryResultVideo implements InlineQueryResult {

    public static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private final String type = "video"; ///< Type of the result, must be "video"
    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    public static final String MIMETYPE_FIELD = "mime_type";
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Mime type of the content of video url, i.e. “text/html” or “video/mp4”
    public static final String VIDEOURL_FIELD = "video_url";
    @JsonProperty(VIDEOURL_FIELD)
    private String videoUrl; ///< A valid URL for the embedded video player or video file
    public static final String VIDEOWIDTH_FIELD = "video_width";
    @JsonProperty(VIDEOWIDTH_FIELD)
    private Integer videoWidth; ///< Optional. Video width
    public static final String VIDEOHEIGHT_FIELD = "video_height";
    @JsonProperty(VIDEOHEIGHT_FIELD)
    private Integer videoHeight; ///< Optional. Video height
    public static final String VIDEODURATION_FIELD = "video_duration";
    @JsonProperty(VIDEODURATION_FIELD)
    private Integer videoDuration; ///< Optional. Video duration in seconds
    public static final String THUMBURL_FIELD = "thumb_url";
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the thumbnail (jpeg only) for the video
    public static final String TITLE_FIELD = "title";
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    public static final String DESCRIPTION_FIELD = "description";
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    public static final String MESSAGETEXT_FIELD = "message_text";
    @JsonProperty(MESSAGETEXT_FIELD)
    private String messageText; ///< Optional. Text of a message to be sent instead of the video
    public static final String PARSEMODE_FIELD = "parse_mode";
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
    public static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";
    @JsonProperty(DISABLEWEBPAGEPREVIEW_FIELD)
    private Boolean disableWebPagePreview; ///< Optional. Disables link previews for links in the sent message

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public Integer getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Integer videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(TYPE_FIELD, this.type);
        jsonObject.put(ID_FIELD, this.id);
        jsonObject.put(VIDEOURL_FIELD, this.videoUrl);
        if (mimeType != null) {
            jsonObject.put(MIMETYPE_FIELD, this.mimeType);
        }
        if (messageText != null) {
            jsonObject.put(MESSAGETEXT_FIELD, this.messageText);
        }
        if (parseMode != null) {
            jsonObject.put(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            jsonObject.put(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
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
        if (messageText != null) {
            gen.writeStringField(MESSAGETEXT_FIELD, this.messageText);
        }
        if (parseMode != null) {
            gen.writeStringField(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            gen.writeBooleanField(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
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

        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
