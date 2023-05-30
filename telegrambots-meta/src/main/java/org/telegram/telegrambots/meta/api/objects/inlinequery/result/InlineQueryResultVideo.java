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
 * Represents link to a page containing an embedded video player or a video file.
 * Alternatively, you can use input_message_content to send a message with the specified content
 * instead of the video.
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
public class InlineQueryResultVideo implements InlineQueryResult {
    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String VIDEOURL_FIELD = "video_url";
    private static final String VIDEOWIDTH_FIELD = "video_width";
    private static final String VIDEOHEIGHT_FIELD = "video_height";
    private static final String VIDEODURATION_FIELD = "video_duration";
    private static final String THUMBNAILURL_FIELD = "thumbnail_url";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";

    @JsonProperty(TYPE_FIELD)
    private final String type = "video"; ///< Type of the result, must be "video"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result
    @JsonProperty(MIMETYPE_FIELD)
    @NonNull
    private String mimeType; ///< Mime type of the content of video url, i.e. “text/html” or “video/mp4”
    @JsonProperty(VIDEOURL_FIELD)
    @NonNull
    private String videoUrl; ///< A valid URL for the embedded video player or video file
    @JsonProperty(VIDEOWIDTH_FIELD)
    private Integer videoWidth; ///< Optional. Video width
    @JsonProperty(VIDEOHEIGHT_FIELD)
    private Integer videoHeight; ///< Optional. Video height
    @JsonProperty(VIDEODURATION_FIELD)
    private Integer videoDuration; ///< Optional. Video duration in seconds
    @JsonProperty(THUMBNAILURL_FIELD)
    private String thumbnailUrl; ///< Optional. URL of the thumbnail (jpeg only) for the video
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
        if (videoUrl.isEmpty()) {
            throw new TelegramApiValidationException("VideoUrl parameter can't be empty", this);
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

    public static class InlineQueryResultVideoBuilder {

        @Tolerate
        @Deprecated
        public InlineQueryResultVideoBuilder thumbUrl(String thumbUrl) {
            this.thumbnailUrl = thumbUrl;
            return this;
        }
    }
}
