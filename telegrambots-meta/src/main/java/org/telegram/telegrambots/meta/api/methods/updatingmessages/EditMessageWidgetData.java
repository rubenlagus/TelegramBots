package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetValidations;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Edits widget data in a message sent by or via the bot. Empty strings remove data; null leaves it unchanged.
 * Public data of a shared widget can be changed at most once per second.
 * @version 10.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditMessageWidgetData extends BotApiMethodBoolean {
    public static final String PATH = "editMessageWidgetData";
    @JsonProperty("chat_id") private String chatId;
    @JsonProperty("message_id") private Integer messageId;
    @JsonProperty("inline_message_id") private String inlineMessageId;
    @JsonProperty("public_data") private String publicData;
    @JsonProperty("chat_data") private String chatData;

    @Tolerate
    public void setChatId(Long chatId) {
        this.chatId = chatId == null ? null : chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inlineMessageId == null) {
            if (chatId == null || chatId.isEmpty() || messageId == null) {
                throw new TelegramApiValidationException("chat_id and message_id are required without inline_message_id", this);
            }
        } else if (inlineMessageId.isEmpty() || chatId != null || messageId != null) {
            throw new TelegramApiValidationException("inline_message_id must be nonempty and used without chat_id or message_id", this);
        }
        WidgetValidations.validateOptionalData(publicData, "public_data", this);
        WidgetValidations.validateOptionalData(chatData, "chat_data", this);
    }

    public static abstract class EditMessageWidgetDataBuilder<C extends EditMessageWidgetData, B extends EditMessageWidgetDataBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public EditMessageWidgetDataBuilder<C, B> chatId(Long chatId) {
            this.chatId = chatId == null ? null : chatId.toString();
            return this;
        }
    }
}