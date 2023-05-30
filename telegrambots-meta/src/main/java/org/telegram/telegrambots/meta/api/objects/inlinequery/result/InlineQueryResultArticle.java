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
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a link to an article or web page.
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
public class InlineQueryResultArticle implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String URL_FIELD = "url";
    private static final String HIDEURL_FIELD = "hide_url";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String THUMBNAIL_URL_FIELD = "thumbnail_url";
    private static final String THUMBNAIL_WIDTH_FIELD = "thumbnail_width";
    private static final String THUMBNAUK_HEIGHT_FIELD = "thumbnail_height";

    @JsonProperty(TYPE_FIELD)
    private final String type = "article"; ///< Type of the result, must be “article”
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Title of the result
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    @NonNull
    private InputMessageContent inputMessageContent; ///< Content of the message to be sent
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. URL of the result
    @JsonProperty(HIDEURL_FIELD)
    private Boolean hideUrl; ///< Optional. Pass True, if you don't want the URL to be shown in the message
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(THUMBNAIL_URL_FIELD)
    private String thumbnailUrl; ///< Optional. Url of the thumbnail for the result
    @JsonProperty(THUMBNAIL_WIDTH_FIELD)
    private Integer thumbnailWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBNAUK_HEIGHT_FIELD)
    private Integer thumbnailHeight; ///< Optional. Thumbnail height

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }

        inputMessageContent.validate();

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

    public static class InlineQueryResultArticleBuilder {

        @Tolerate
        @Deprecated
        public InlineQueryResultArticleBuilder thumbUrl(String thumbUrl) {
            this.thumbnailUrl = thumbUrl;
            return this;
        }

        @Tolerate
        @Deprecated
        public InlineQueryResultArticleBuilder thumbHeight(Integer thumbHeight) {
            this.thumbnailHeight = thumbHeight;
            return this;
        }

        @Tolerate
        @Deprecated
        public InlineQueryResultArticleBuilder thumbWidth(Integer thumbWidth) {
            this.thumbnailWidth = thumbWidth;
            return this;
        }
    }
}
