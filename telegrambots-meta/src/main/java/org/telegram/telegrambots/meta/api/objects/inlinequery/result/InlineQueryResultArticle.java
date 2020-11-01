package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

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
@NoArgsConstructor
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
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";

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
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. Url of the thumbnail for the result
    @JsonProperty(THUMBWIDTH_FIELD)
    private Integer thumbWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBHEIGHT_FIELD)
    private Integer thumbHeight; ///< Optional. Thumbnail height

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (inputMessageContent == null) {
            throw new TelegramApiValidationException("InputMessageContent parameter can't be null", this);
        }
        inputMessageContent.validate();
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
