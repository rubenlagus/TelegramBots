package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

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
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents the content of a text message to be sent as the result of an inline query.
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
public class InputTextMessageContent implements InputMessageContent {

    private static final String MESSAGETEXT_FIELD = "message_text";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview";
    private static final String ENTITIES_FIELD = "entities";
    private static final String LINK_PREVIEW_OPTIONS_FIELD = "link_preview_options";

    /**
     * Text of a message to be sent, 1-4096 characters
     */
    @JsonProperty(MESSAGETEXT_FIELD)
    @NonNull
    private String messageText;
    /**
     * Optional.
     * Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     */
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode;
    /**
     * Optional.
     * Disables link previews for links in the sent message
     */
    @JsonProperty(DISABLEWEBPAGEPREVIEW_FIELD)
    private Boolean disableWebPagePreview;
    /**
     * Optional.
     * List of special entities that appear in message text, which can be specified instead of parse_mode
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * Optional
     * Link preview generation options for the message
     */
    @JsonProperty(LINK_PREVIEW_OPTIONS_FIELD)
    private LinkPreviewOptions linkPreviewOptions;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (messageText.isEmpty()) {
            throw new TelegramApiValidationException("MessageText parameter can't be empty", this);
        }
        if (parseMode != null && (entities != null && !entities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (linkPreviewOptions != null) {
            linkPreviewOptions.validate();
        }
    }
}
