package org.telegram.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to an animated GIF file. By default, this animated GIF file will be sent by the user with optional caption.
 * Alternatively, you can provide message_text to send it instead of the animation.
 * @date 01 of January of 2016
 */
public class InlineQueryResultGif implements InlineQueryResult {

    public static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private final String type = "gif"; ///< Type of the result, must be "gif"
    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    public static final String GIFURL_FIELD = "gif_url";
    @JsonProperty(GIFURL_FIELD)
    private String gifUrl; ///< A valid URL for the GIF file. File size must not exceed 1MB
    public static final String GIFWIDTH_FIELD = "gif_width";
    @JsonProperty(GIFWIDTH_FIELD)
    private Integer gifWidth; ///< Optional. Width of the GIF
    public static final String GIFHEIGHT_FIELD = "gif_height";
    @JsonProperty(GIFHEIGHT_FIELD)
    private Integer gifHeight; ///< Optional. Height of the GIF
    public static final String THUMBURL_FIELD = "thumb_url";
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of a static thumbnail for the result (jpeg or gif)
    public static final String TITLE_FIELD = "title";
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    public static final String CAPTION_FIELD = "caption";
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the GIF file to be sent
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

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public Integer getGifWidth() {
        return gifWidth;
    }

    public void setGifWidth(Integer gifWidth) {
        this.gifWidth = gifWidth;
    }

    public Integer getGifHeight() {
        return gifHeight;
    }

    public void setGifHeight(Integer gifHeight) {
        this.gifHeight = gifHeight;
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
        jsonObject.put(GIFURL_FIELD, this.gifUrl);
        if (parseMode != null) {
            jsonObject.put(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            jsonObject.put(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (gifWidth != null) {
            jsonObject.put(GIFWIDTH_FIELD, this.gifWidth);
        }
        if (gifHeight != null) {
            jsonObject.put(GIFHEIGHT_FIELD, this.gifHeight);
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
        gen.writeStringField(GIFURL_FIELD, this.gifUrl);

        if (parseMode != null) {
            gen.writeStringField(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            gen.writeBooleanField(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (gifWidth != null) {
            gen.writeNumberField(GIFWIDTH_FIELD, this.gifWidth);
        }
        if (gifHeight != null) {
            gen.writeNumberField(GIFHEIGHT_FIELD, this.gifHeight);
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
