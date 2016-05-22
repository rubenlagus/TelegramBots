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
 * @brief Represents a link to an article or web page.
 * @date 01 of January of 2016
 */
public class InlineQueryResultArticle implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private static final String type = "article"; ///< Type of the result, must be “article”
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String URL_FIELD = "url";
    private static final String HIDEURL_FIELD = "hide_url";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Title of the result
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Content of the message to be sent
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. URL of the result
    @JsonProperty(HIDEURL_FIELD)
    private Boolean hideUrl; ///< Optional. Pass True, if you don't want the URL to be shown in the message
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. Url of the thumbnail for the result
    @JsonProperty(THUMBWIDTH_FIELD)
    private Integer thumbWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBHEIGHT_FIELD)
    private Integer thumbHeight; ///< Optional. Thumbnail height

    public static String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultArticle setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultArticle setTitle(String title) {
        this.title = title;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultArticle setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultArticle setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public InlineQueryResultArticle setUrl(String url) {
        this.url = url;
        return this;
    }

    public Boolean getHideUrl() {
        return hideUrl;
    }

    public InlineQueryResultArticle setHideUrl(Boolean hideUrl) {
        this.hideUrl = hideUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultArticle setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public InlineQueryResultArticle setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public InlineQueryResultArticle setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public InlineQueryResultArticle setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(TYPE_FIELD, type);
        jsonObject.put(ID_FIELD, id);
        jsonObject.put(TITLE_FIELD, this.title);
        jsonObject.put(INPUTMESSAGECONTENT_FIELD, inputMessageContent.toJson());
        if (replyMarkup != null) {
            jsonObject.put(REPLY_MARKUP_FIELD, replyMarkup.toJson());
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
        gen.writeObjectField(INPUTMESSAGECONTENT_FIELD, inputMessageContent);
        if (replyMarkup != null) {
            gen.writeObjectField(REPLY_MARKUP_FIELD, replyMarkup);
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
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                ", url='" + url + '\'' +
                ", hideUrl=" + hideUrl +
                ", description='" + description + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", thumbWidth=" + thumbWidth +
                ", thumbHeight=" + thumbHeight +
                '}';
    }
}
