package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a link to a file. By default, this file will be sent by the user with an
 * optional caption. Alternatively, you can use input_message_content to send a message with the
 * specified content instead of the file.
 * @apiNote Currently, only .PDF and .ZIP files can be sent using this method.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class InlineQueryResultDocument implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DOCUMENTURL_FIELD = "document_url";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBNAIL_URL_FIELD = "thumbnail_url";
    private static final String THUMBNAIL_WIDTH_FIELD = "thumbnail_width";
    private static final String THUMBNAUK_HEIGHT_FIELD = "thumbnail_height";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";

    @JsonProperty(TYPE_FIELD)
    private final String type = "document"; ///< Type of the result, must be "document"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Title for the result
    @JsonProperty(DOCUMENTURL_FIELD)
    @NonNull
    private String documentUrl; ///< A valid URL for the file
    @JsonProperty(MIMETYPE_FIELD)
    @NonNull
    private String mimeType; ///< Mime type of the content of the file, either “application/pdf” or “application/zip”
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the document to be sent, 0-200 characters
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the file
    @JsonProperty(THUMBNAIL_URL_FIELD)
    private String thumbnailUrl; ///< Optional. URL of the thumbnail (jpeg only) for the file
    @JsonProperty(THUMBNAIL_WIDTH_FIELD)
    private Integer thumbnailWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBNAUK_HEIGHT_FIELD)
    private Integer thumbnailHeight; ///< Optional. Thumbnail height
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> captionEntities; ///< Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (mimeType.isEmpty()) {
            throw new TelegramApiValidationException("Mimetype parameter can't be empty", this);
        }
        if (documentUrl.isEmpty()) {
            throw new TelegramApiValidationException("DocumentUrl parameter can't be empty", this);
        }
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (thumbnailUrl != null && !"image/jpeg".equalsIgnoreCase(thumbnailUrl)) {
            throw new TelegramApiValidationException("Thumbnail Url must be JPEG", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    /**
     * @deprecated Use {{@link #getThumbnailUrl()}}
     */
    @JsonIgnore
    @Deprecated
    public String getThumbUrl() {
        return thumbnailUrl;
    }

    /**
     * @deprecated Use {{@link #setThumbnailUrl(String)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumbUrl(String thumbUrl) {
        this.thumbnailUrl = thumbUrl;
    }

    /**
     * @deprecated Use {{@link #getThumbnailWidth()}}
     */
    @JsonIgnore
    @Deprecated
    public Integer getThumbWidth() {
        return thumbnailWidth;
    }

    /**
     * @deprecated Use {{@link #setThumbnailWidth(Integer)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumbWidth(Integer thumbWidth) {
        this.thumbnailWidth = thumbWidth;
    }

    /**
     * @deprecated Use {{@link #getThumbnailHeight()}}
     */
    @JsonIgnore
    @Deprecated
    public Integer getThumbHeight() {
        return thumbnailHeight;
    }

    /**
     * @deprecated Use {{@link #setThumbnailHeight(Integer)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumbHeight(Integer thumbHeight) {
        this.thumbnailHeight = thumbHeight;
    }

    public static class InlineQueryResultDocumentBuilder {

        @Tolerate
        @Deprecated
        public InlineQueryResultDocumentBuilder thumbUrl(String thumbUrl) {
            this.thumbnailUrl = thumbUrl;
            return this;
        }

        @Tolerate
        @Deprecated
        public InlineQueryResultDocumentBuilder thumbHeight(Integer thumbHeight) {
            this.thumbnailHeight = thumbHeight;
            return this;
        }

        @Tolerate
        @Deprecated
        public InlineQueryResultDocumentBuilder thumbWidth(Integer thumbWidth) {
            this.thumbnailWidth = thumbWidth;
            return this;
        }
    }
}
