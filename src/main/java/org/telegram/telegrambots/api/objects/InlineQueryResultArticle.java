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
 * @brief Represents a link to an article or web page.
 * @date 01 of January of 2016
 */
public class InlineQueryResultArticle implements InlineQueryResult {

    public static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private final String type = "article"; ///< Type of the result, must be “article”
    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    public static final String TITLE_FIELD = "title";
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Title of the result
    public static final String MESSAGETEXT_FIELD = "message_text";
    @JsonProperty(MESSAGETEXT_FIELD)
    private String messageText; ///< Text of a message to be sent, 1-4096 characters
    public static final String PARSEMODE_FIELD = "parse_mode";
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
    public static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";
    @JsonProperty(DISABLEWEBPAGEPREVIEW_FIELD)
    private Boolean disableWebPagePreview; ///< Optional. Disables link previews for links in the sent message
    public static final String URL_FIELD = "url";
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. URL of the result
    public static final String HIDEURL_FIELD = "hide_url";
    @JsonProperty(HIDEURL_FIELD)
    private Boolean hideUrl; ///< Optional. Pass True, if you don't want the URL to be shown in the message
    public static final String DESCRIPTION_FIELD = "description";
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    public static final String THUMBURL_FIELD = "thumb_url";
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. Url of the thumbnail for the result
    public static final String THUMBWIDTH_FIELD = "thumb_width";
    @JsonProperty(THUMBWIDTH_FIELD)
    private Integer thumbWidth; ///< Optional. Thumbnail width
    public static final String THUMBHEIGHT_FIELD = "thumb_height";
    @JsonProperty(THUMBHEIGHT_FIELD)
    private Integer thumbHeight; ///< Optional. Thumbnail height

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHideUrl() {
        return hideUrl;
    }

    public void setHideUrl(Boolean hideUrl) {
        this.hideUrl = hideUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(TYPE_FIELD, this.type);
        jsonObject.put(ID_FIELD, this.id);
        jsonObject.put(TITLE_FIELD, this.title);
        jsonObject.put(MESSAGETEXT_FIELD, this.messageText);
        if (parseMode != null) {
            jsonObject.put(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            jsonObject.put(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (url != null) {
            jsonObject.put(URL_FIELD, this.url);
        }
        if (hideUrl != null) {
            jsonObject.put(HIDEURL_FIELD, this.hideUrl);
        }
        if (description != null) {
            jsonObject.put(DESCRIPTION_FIELD, this.description);
        }
        if (thumbUrl != null) {
            jsonObject.put(THUMBURL_FIELD, this.thumbUrl);
        }
        if (thumbWidth != null) {
            jsonObject.put(THUMBWIDTH_FIELD, this.thumbWidth);
        }
        if (thumbHeight != null) {
            jsonObject.put(THUMBHEIGHT_FIELD, this.thumbHeight);
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TYPE_FIELD, type);
        gen.writeStringField(ID_FIELD, id);
        gen.writeStringField(TITLE_FIELD, title);
        gen.writeStringField(MESSAGETEXT_FIELD, messageText);

        if (parseMode != null) {
            gen.writeStringField(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            gen.writeBooleanField(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (url != null) {
            gen.writeStringField(URL_FIELD, this.url);
        }
        if (hideUrl != null) {
            gen.writeBooleanField(HIDEURL_FIELD, this.hideUrl);
        }
        if (description != null) {
            gen.writeStringField(DESCRIPTION_FIELD, this.description);
        }
        if (thumbUrl != null) {
            gen.writeStringField(THUMBURL_FIELD, this.thumbUrl);
        }
        if (thumbWidth != null) {
            gen.writeNumberField(THUMBWIDTH_FIELD, this.thumbWidth);
        }
        if (thumbHeight != null) {
            gen.writeNumberField(THUMBHEIGHT_FIELD, this.thumbHeight);
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
        return "InlineQueryResultArticle{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", messageText='" + messageText + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", disableWebPagePreview=" + disableWebPagePreview +
                ", url='" + url + '\'' +
                ", hideUrl=" + hideUrl +
                ", description='" + description + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", thumbWidth=" + thumbWidth +
                ", thumbHeight=" + thumbHeight +
                '}';
    }
}
