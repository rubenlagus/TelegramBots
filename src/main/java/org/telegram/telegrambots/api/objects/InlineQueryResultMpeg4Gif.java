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
 * @brief Represents a link to a video animation (H.264/MPEG-4 AVC video without sound).
 * By default, this animated MPEG-4 file will be sent by the user with optional caption.
 * Alternatively, you can provide message_text to send it instead of the animation.
 * @date 01 of January of 2016
 */
public class InlineQueryResultMpeg4Gif implements InlineQueryResult {

    public static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private final String type = "mpeg4_gif"; ///< Type of the result, must be "mpeg4_gif"
    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    public static final String MPEG4URL_FIELD = "mpeg4_url";
    @JsonProperty(MPEG4URL_FIELD)
    private String mpeg4Url; ///< A valid URL for the MP4 file. File size must not exceed 1MB
    public static final String MPEG4WIDTH_FIELD = "mpeg4_width";
    @JsonProperty(MPEG4WIDTH_FIELD)
    private Integer mpeg4Width; ///< Optional. Video width
    public static final String MPEG4HEIGHT_FIELD = "mpeg4_height";
    @JsonProperty(MPEG4HEIGHT_FIELD)
    private Integer mpeg4Height; ///< Optional. Video height
    public static final String THUMBURL_FIELD = "thumb_url";
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the static thumbnail (jpeg or gif) for the result
    public static final String TITLE_FIELD = "title";
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    public static final String CAPTION_FIELD = "caption";
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the MPEG-4 file to be sent
    public static final String MESSAGETEXT_FIELD = "message_text";
    @JsonProperty(MESSAGETEXT_FIELD)
    private String messageText; ///< Optional. Text of a message to be sent instead of the animation
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

    public void enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = "Markdown";
        } else {
            this.parseMode = null;
        }
    }

    public void enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = "html";
        } else {
            this.parseMode = null;
        }
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public String getMpeg4Url() {
        return mpeg4Url;
    }

    public void setMpeg4Url(String mpeg4Url) {
        this.mpeg4Url = mpeg4Url;
    }

    public Integer getMpeg4Width() {
        return mpeg4Width;
    }

    public void setMpeg4Width(Integer mpeg4Width) {
        this.mpeg4Width = mpeg4Width;
    }

    public Integer getMpeg4Height() {
        return mpeg4Height;
    }

    public void setMpeg4Height(Integer mpeg4Height) {
        this.mpeg4Height = mpeg4Height;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(TYPE_FIELD, this.type);
        jsonObject.put(ID_FIELD, this.id);
        jsonObject.put(MPEG4URL_FIELD, this.mpeg4Url);
        if (parseMode != null) {
            jsonObject.put(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            jsonObject.put(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (mpeg4Width != null) {
            jsonObject.put(MPEG4WIDTH_FIELD, this.mpeg4Width);
        }
        if (mpeg4Height != null) {
            jsonObject.put(MPEG4HEIGHT_FIELD, this.mpeg4Height);
        }
        if (thumbUrl != null) {
            jsonObject.put(THUMBURL_FIELD, this.thumbUrl);
        }
        if (title != null) {
            jsonObject.put(TITLE_FIELD, this.title);
        }
        if (caption != null) {
            jsonObject.put(CAPTION_FIELD, this.caption);
        }
        if (messageText != null) {
            jsonObject.put(MESSAGETEXT_FIELD, this.messageText);
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TYPE_FIELD, type);
        gen.writeStringField(ID_FIELD, id);
        gen.writeStringField(MPEG4URL_FIELD, this.mpeg4Url);

        if (parseMode != null) {
            gen.writeStringField(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            gen.writeBooleanField(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (mpeg4Width != null) {
            gen.writeNumberField(MPEG4WIDTH_FIELD, this.mpeg4Width);
        }
        if (mpeg4Height != null) {
            gen.writeNumberField(MPEG4HEIGHT_FIELD, this.mpeg4Height);
        }
        if (thumbUrl != null) {
            gen.writeStringField(THUMBURL_FIELD, this.thumbUrl);
        }
        if (title != null) {
            gen.writeStringField(TITLE_FIELD, this.title);
        }
        if (caption != null) {
            gen.writeStringField(CAPTION_FIELD, this.caption);
        }
        if (messageText != null) {
            gen.writeStringField(MESSAGETEXT_FIELD, this.messageText);
        }

        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
