package org.telegram.telegrambots.meta.api.methods.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.checklist.InputChecklist;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author JetBrains
 * @version 7.0
 * Use this method to edit a checklist on behalf of a connected business account.
 * On success, the edited Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditMessageChecklist extends BotApiMethodMessage {
    public static final String PATH = "editMessageChecklist";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String CHECKLIST_FIELD = "checklist";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    /**
     * Unique identifier of the business connection on behalf of which the message will be sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Unique identifier for the target chat
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;

    /**
     * Unique identifier for the target message
     */
    @JsonProperty(MESSAGE_ID_FIELD)
    @NonNull
    private Integer messageId;

    /**
     * A JSON-serialized object for the new checklist
     */
    @JsonProperty(CHECKLIST_FIELD)
    @NonNull
    private InputChecklist checklist;

    /**
     * Optional. A JSON-serialized object for the new inline keyboard for the message
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;

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
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty", this);
        }
        Validations.requiredChatId(chatId, this);
        checklist.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    public static abstract class EditMessageChecklistBuilder<C extends EditMessageChecklist, B extends EditMessageChecklistBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public EditMessageChecklistBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
