package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetValidations;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * Sends an existing shared widget created by the bot. In production widgets are currently limited to private
 * chats and are automatically protected from forwarding. Chat data is at most 1024 UTF-8 bytes; the caption
 * may contain up to 1024 characters after entity parsing.
 * @version 10.4
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
public class SendSharedWidget extends BotApiMethodMessage {
    public static final String PATH = "sendSharedWidget";

    @JsonProperty("chat_id") @NonNull private String chatId;
    @JsonProperty("message_thread_id") private Integer messageThreadId;
    @JsonProperty("widget_id") @NonNull private String widgetId;
    @JsonProperty("chat_data") private String chatData;
    @JsonProperty("caption") private String caption;
    @JsonProperty("parse_mode") private String parseMode;
    @JsonProperty("caption_entities") private List<MessageEntity> captionEntities;
    @JsonProperty("show_caption_above_media") private Boolean showCaptionAboveMedia;
    @JsonProperty("disable_notification") private Boolean disableNotification;
    @JsonProperty("protect_content") private Boolean protectContent;
    @JsonProperty("allow_paid_broadcast") private Boolean allowPaidBroadcast;
    @JsonProperty("message_effect_id") private String messageEffectId;
    @JsonProperty("reply_parameters") private ReplyParameters replyParameters;
    @JsonProperty("reply_markup") private ReplyKeyboard replyMarkup;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        if (widgetId == null || widgetId.isEmpty()) {
            throw new TelegramApiValidationException("widget_id can't be empty", this);
        }
        WidgetValidations.validateOptionalData(chatData, "chat_data", this);
        WidgetValidations.validateCaption(caption, parseMode, this);
        if (parseMode != null && captionEntities != null && !captionEntities.isEmpty()) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if caption entities are provided", this);
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static abstract class SendSharedWidgetBuilder<C extends SendSharedWidget, B extends SendSharedWidgetBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendSharedWidgetBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}