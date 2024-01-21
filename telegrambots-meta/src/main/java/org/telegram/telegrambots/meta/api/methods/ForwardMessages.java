package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to forward multiple messages of any kind.
 * If some of the specified messages can't be found or forwarded, they are skipped.
 * Service messages and messages with protected content can't be forwarded.
 * Album grouping is kept for forwarded messages.
 *
 * On success, an array of MessageId of the messages sent is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ForwardMessages extends BotApiMethod<ArrayList<MessageId>> {
    public static final String PATH = "forwardMessages";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String FROM_CHAT_ID_FIELD = "from_chat_id";
    private static final String MESSAGE_IDS_FIELD = "message_ids";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Optional
     * Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    /**
     * Unique identifier for the chat where the original messages were sent (or channel username in the format @channelusername)
     */
    @JsonProperty(FROM_CHAT_ID_FIELD)
    @NonNull
    private String fromChatId;
    /**
     * Identifiers of 1-100 messages in the chat from_chat_id to forward.
     * The identifiers must be specified in a strictly increasing order.
     */
    @JsonProperty(MESSAGE_IDS_FIELD)
    @NonNull
    @Singular
    private List<Integer> messageIds;
    /**
     * Optional.
     * Sends the messages silently.
     * Users will receive a notification with no sound.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;
    /**
     * Optional.
     * Protects the contents of the forwarded messages from forwarding and saving
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Tolerate
    public void setFromChatId(@NonNull Long fromChatId) {
        this.fromChatId = fromChatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (fromChatId.isEmpty()) {
            throw new TelegramApiValidationException("FromChatId can't be empty", this);
        }
        if (messageIds.isEmpty() || messageIds.size() > 100) {
            throw new TelegramApiValidationException("MessageIds parameter items count must be between 1 and 100", this);
        }
    }

    @Override
    public ArrayList<MessageId> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, MessageId.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static class ForwardMessagesBuilder {

        @Tolerate
        public ForwardMessagesBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public ForwardMessagesBuilder fromChatId(@NonNull Long fromChatId) {
            this.fromChatId = fromChatId.toString();
            return this;
        }
    }
}
