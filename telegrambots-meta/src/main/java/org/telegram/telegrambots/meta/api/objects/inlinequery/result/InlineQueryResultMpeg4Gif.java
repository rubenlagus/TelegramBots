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
 * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound). By default,
 * this animated MPEG-4 file will be sent by the user with optional caption. Alternatively, you can
 * use input_message_content to send a message with the specified content instead of the animation.
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
public class InlineQueryResultMpeg4Gif implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String MPEG4URL_FIELD = "mpeg4_url";
    private static final String MPEG4WIDTH_FIELD = "mpeg4_width";
    private static final String MPEG4HEIGHT_FIELD = "mpeg4_height";
    private static final String THUMBNAIL_URL_FIELD = "thumbnail_url";
    private static final String THUMBNAIL_MIMETYPE_FIELD = "thumbnail_mime_type";
    private static final String TITLE_FIELD = "title";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String MPEG4_DURATION_FIELD = "mpeg4_duration";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";

    @JsonProperty(TYPE_FIELD)
    private final String type = "mpeg4_gif"; ///< Type of the result, must be "mpeg4_gif"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(MPEG4URL_FIELD)
    @NonNull
    private String mpeg4Url; ///< A valid URL for the MP4 file. File size must not exceed 1MB
    @JsonProperty(MPEG4WIDTH_FIELD)
    private Integer mpeg4Width; ///< Optional. Video width
    @JsonProperty(MPEG4HEIGHT_FIELD)
    private Integer mpeg4Height; ///< Optional. Video height
    @JsonProperty(THUMBNAIL_URL_FIELD)
    private String thumbnailUrl; ///< Optional. URL of the static thumbnail (jpeg or gif) for the result
    @JsonProperty(THUMBNAIL_MIMETYPE_FIELD)
    private String thumbnailMimeType; ///< Optional. MIME type of the thumbnail, must be one of “image/jpeg”, “image/gif”, or “video/mp4”
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the MPEG-4 file to be sent
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the photo
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(MPEG4_DURATION_FIELD)
    private Integer mpeg4Duration; ///< Optional. Video duration
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
        if (mpeg4Url.isEmpty()) {
            throw new TelegramApiValidationException("Mpeg4Url parameter can't be empty", this);
        }
        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (thumbnailMimeType != null && !VALIDTHUMBTYPES.contains(thumbnailMimeType)) {
            throw new TelegramApiValidationException("ThumbUrlType parameter must be one of “image/jpeg”, “image/gif”, or “video/mp4”", this);
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

    public static class InlineQueryResultMpeg4GifBuilder {

        @Tolerate
        @Deprecated
        public InlineQueryResultMpeg4GifBuilder thumbUrl(String thumbUrl) {
            this.thumbnailUrl = thumbUrl;
            return this;
        }
    }
}
