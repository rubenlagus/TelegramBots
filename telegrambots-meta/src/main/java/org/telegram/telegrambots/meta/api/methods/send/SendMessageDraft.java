package org.telegram.telegrambots.meta.api.methods.send;

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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.3
 * Use this method to stream a partial message to a user while the message is being generated;
 * supported only for bots with forum topic mode enabled. Returns True on success.
 */
@SuppressWarnings("unused")
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
public class SendMessageDraft extends BotApiMethodBoolean {
    public static final String PATH = "sendMessageDraft";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String DRAFT_ID_FIELD = "draft_id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String ENTITIES_FIELD = "entities";

    /**
     * Unique identifier for the target private chat
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private Long chatId;

    /**
     * Optional.
     * Unique identifier for the target message thread
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;

    /**
     * Unique identifier of the message draft; must be non-zero.
     * Changes of drafts with the same identifier are animated
     */
    @JsonProperty(DRAFT_ID_FIELD)
    @NonNull
    private Integer draftId;

    /**
     * Text of the message to be sent, 1-4096 characters after entities parsing
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;

    /**
     * Optional.
     * Mode for parsing entities in the message text. See formatting options for more details.
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;

    /**
     * Optional.
     * A JSON-serialized list of special entities that appear in message text,
     * which can be specified instead of parse_mode
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId == 0L) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (draftId == null || draftId == 0) {
            throw new TelegramApiValidationException("DraftId can't be empty and must be non-zero", this);
        }
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Text can't be empty", this);
        }
        if (text.length() > 4096) {
            throw new TelegramApiValidationException("Text can't be longer than 4096 characters", this);
        }
    }
}
