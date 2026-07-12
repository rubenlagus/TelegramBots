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
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Use this method to stream a partial rich message to a user while the message is being generated.
 * The streamed draft is ephemeral and acts as a temporary 30-second preview.
 * Returns True on success.
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
public class SendRichMessageDraft extends BotApiMethodBoolean {
    public static final String PATH = "sendRichMessageDraft";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String DRAFT_ID_FIELD = "draft_id";
    private static final String RICH_MESSAGE_FIELD = "rich_message";

    /**
     * Unique identifier for the target private chat
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private Long chatId;

    /**
     * Optional. Unique identifier for the target message thread
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;

    /**
     * Unique identifier of the message draft; must be non-zero.
     * Changes to drafts with the same identifier are animated.
     */
    @JsonProperty(DRAFT_ID_FIELD)
    @NonNull
    private Integer draftId;

    /**
     * The partial message to be streamed
     */
    @JsonProperty(RICH_MESSAGE_FIELD)
    @NonNull
    private InputRichMessage richMessage;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (draftId == null || draftId == 0) {
            throw new TelegramApiValidationException("DraftId parameter must be non-zero", this);
        }
        richMessage.validate();
    }
}
