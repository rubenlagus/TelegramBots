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
 * Sends an HTML widget. In production widgets are currently limited to private chats and are automatically
 * protected from forwarding. HTML is 1-32768 UTF-8 bytes and at most 4096 bytes compressed with zlib's
 * maximum compression; public and chat data are each at most 1024 UTF-8 bytes. The caption may contain
 * up to 1024 characters after entity parsing; clients clamp width and height to the supported display range.
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
public class SendWidget extends BotApiMethodMessage {
    public static final String PATH = "sendWidget";

    @JsonProperty("chat_id") @NonNull private String chatId;
    @JsonProperty("message_thread_id") private Integer messageThreadId;
    @JsonProperty("html") @NonNull private String html;
    @JsonProperty("width") private Integer width;
    @JsonProperty("height") private Integer height;
    @JsonProperty("public_data") private String publicData;
    @JsonProperty("chat_data") private String chatData;
    @JsonProperty("caption") private String caption;
    @JsonProperty("parse_mode") private String parseMode;
    @JsonProperty("caption_entities") private List<MessageEntity> captionEntities;
    @JsonProperty("show_caption_above_media") private Boolean showCaptionAboveMedia;
    @JsonProperty("allow_queries") private Boolean allowQueries;
    @JsonProperty("is_shared") private Boolean isShared;
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
        WidgetValidations.validateHtml(html, this);
        WidgetValidations.validateOptionalData(publicData, "public_data", this);
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

    public static abstract class SendWidgetBuilder<C extends SendWidget, B extends SendWidgetBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendWidgetBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}