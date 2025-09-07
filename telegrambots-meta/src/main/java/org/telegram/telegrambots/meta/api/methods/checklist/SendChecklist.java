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
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.checklist.InputChecklist;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author JetBrains
 * @version 7.0
 * Use this method to send a checklist on behalf of a connected business account.
 * On success, the sent Message is returned.
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
public class SendChecklist extends BotApiMethodMessage {
    public static final String PATH = "sendChecklist";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String CHECKLIST_FIELD = "checklist";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";
    private static final String MESSAGE_EFFECT_ID_FIELD = "message_effect_id";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
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
     * A JSON-serialized object for the checklist to send
     */
    @JsonProperty(CHECKLIST_FIELD)
    @NonNull
    private InputChecklist checklist;

    /**
     * Optional. Sends the message silently. Users will receive a notification with no sound.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;

    /**
     * Optional. Protects the contents of the sent message from forwarding and saving
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;

    /**
     * Optional. Unique identifier of the message effect to be added to the message
     */
    @JsonProperty(MESSAGE_EFFECT_ID_FIELD)
    private String messageEffectId;

    /**
     * Optional. A JSON-serialized object for description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;

    /**
     * Optional. A JSON-serialized object for an inline keyboard
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

        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static abstract class SendChecklistBuilder<C extends SendChecklist, B extends SendChecklistBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendChecklistBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
