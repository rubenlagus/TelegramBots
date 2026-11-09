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
 * Content of an inline widget message. Widgets cannot currently be sent through inline queries in production.
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
public class InputWidgetMessageContent implements InputMessageContent {
    @JsonProperty("html")
    @NonNull
    private String html;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("public_data")
    private String publicData;
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
    @JsonProperty("allow_queries")
    private Boolean allowQueries;
    @JsonProperty("is_shared")
    private Boolean isShared;

    @Override
    public void validate() throws TelegramApiValidationException {
        WidgetValidations.validateHtml(html, this);
        WidgetValidations.validateOptionalData(publicData, "public_data", this);
        WidgetValidations.validateOptionalData(chatData, "chat_data", this);
        WidgetValidations.validateCaption(caption, parseMode, this);
        if (parseMode != null && captionEntities != null && !captionEntities.isEmpty()) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if caption entities are provided", this);
        }
    }
}