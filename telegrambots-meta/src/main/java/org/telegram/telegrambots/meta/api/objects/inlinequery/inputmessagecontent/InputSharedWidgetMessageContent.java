package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetValidations;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @version 10.4
 * Content of an inline shared-widget message. Widgets cannot currently be sent through inline queries in production.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputSharedWidgetMessageContent implements InputMessageContent {
    @JsonProperty("widget_id")
    @NonNull
    private String widgetId;
    @JsonProperty("chat_data")
    private String chatData;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("parse_mode")
    private String parseMode;
    @JsonProperty("caption_entities")
    private List<MessageEntity> captionEntities;
    @JsonProperty("show_caption_above_media")
    private Boolean showCaptionAboveMedia;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (widgetId == null || widgetId.isEmpty()) {
            throw new TelegramApiValidationException("WidgetId must not be empty", this);
        }
        WidgetValidations.validateOptionalData(chatData, "chat_data", this);
        WidgetValidations.validateCaption(caption, parseMode, this);
        if (parseMode != null && captionEntities != null && !captionEntities.isEmpty()) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if caption entities are provided", this);
        }
    }
}