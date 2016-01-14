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
 * @brief Represents a link to a photo. By default, this photo will be sent by the user with optional caption.
 * Alternatively, you can provide message_text to send it instead of photo.
 * @date 01 of January of 2016
 */
public class InlineQueryResultPhoto implements InlineQueryResult {

    public static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private final String type = "photo"; ///< Type of the result, must be “photo”
    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result
    public static final String PHOTOURL_FIELD = "photo_url";
    @JsonProperty(PHOTOURL_FIELD)
    private String photoUrl; ///< A valid URL of the photo. Photo size must not exceed 5MB
    public static final String MIMETYPE_FIELD = "mime_type";
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Optional. MIME type of the photo, defaults to image/jpeg
    public static final String PHOTOWIDTH_FIELD = "photo_width";
    @JsonProperty(PHOTOWIDTH_FIELD)
    private Integer photoWidth; ///< Optional. Width of the photo
    public static final String PHOTOHEIGHT_FIELD = "photo_height";
    @JsonProperty(PHOTOHEIGHT_FIELD)
    private Integer photoHeight; ///< Optional. Height of the photo
    public static final String THUMBURL_FIELD = "thumb_url";
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the thumbnail for the photo
    public static final String TITLE_FIELD = "title";
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    public static final String DESCRIPTION_FIELD = "description";
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    public static final String CAPTION_FIELD = "caption";
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the photo to be sent
    public static final String MESSAGETEXT_FIELD = "message_text";
    @JsonProperty(MESSAGETEXT_FIELD)
    private String messageText; ///< Optional. Text of a message to be sent instead of the photo
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(Integer photoWidth) {
        this.photoWidth = photoWidth;
    }

    public Integer getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(Integer photoHeight) {
        this.photoHeight = photoHeight;
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
        jsonObject.put(PHOTOURL_FIELD, this.photoUrl);
        if (parseMode != null) {
            jsonObject.put(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            jsonObject.put(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (mimeType != null) {
            jsonObject.put(MIMETYPE_FIELD, this.mimeType);
        }
        if (photoWidth != null) {
            jsonObject.put(PHOTOWIDTH_FIELD, this.photoWidth);
        }
        if (photoHeight != null) {
            jsonObject.put(PHOTOHEIGHT_FIELD, this.photoHeight);
        }
        if (description != null) {
            jsonObject.put(DESCRIPTION_FIELD, this.description);
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
        gen.writeStringField(PHOTOURL_FIELD, this.photoUrl);
        if (parseMode != null) {
            gen.writeStringField(PARSEMODE_FIELD, this.parseMode);
        }
        if (disableWebPagePreview != null) {
            gen.writeBooleanField(DISABLEWEBPAGEPREVIEW_FIELD, this.disableWebPagePreview);
        }
        if (mimeType != null) {
            gen.writeStringField(MIMETYPE_FIELD, this.mimeType);
        }
        if (photoWidth != null) {
            gen.writeNumberField(PHOTOWIDTH_FIELD, this.photoWidth);
        }
        if (photoHeight != null) {
            gen.writeNumberField(PHOTOHEIGHT_FIELD, this.photoHeight);
        }
        if (thumbUrl != null) {
            gen.writeStringField(THUMBURL_FIELD, this.thumbUrl);
        }
        if (description != null) {
            gen.writeStringField(DESCRIPTION_FIELD, this.description);
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
