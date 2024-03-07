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
 * Use this method to copy messages of any kind.
 * If some of the specified messages can't be found or copied, they are skipped.
 * Service messages, giveaway messages, giveaway winners messages, and invoice messages can't be copied.
 * A quiz poll can be copied only if the value of the field correct_option_id is known to the bot.
 * The method is analogous to the method forwardMessages, but the copied messages don't have a l
 * ink to the original message. Album grouping is kept for copied messages.
 *
 * On success, an array of MessageId of the messages sent is returned.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CopyMessages extends BotApiMethod<ArrayList<MessageId>> {
    public static final String PATH = "copyMessages";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String FROM_CHAT_ID_FIELD = "from_chat_id";
    private static final String MESSAGE_IDS_FIELD = "message_ids";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";
    private static final String REMOVE_CAPTION_FIELD = "remove_caption";

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
     * Identifiers of 1-100 messages in the chat from_chat_id to copy.
     * The identifiers must be specified in a strictly increasing order.
     */
    @JsonProperty(MESSAGE_IDS_FIELD)
    @NonNull
    @Singular
    private List<Integer> messageIds;
    /**
     * Optional
     * Sends the messages silently. Users will receive a notification with no sound.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;
    /**
     * Optional
     * Protects the contents of the sent messages from forwarding and saving
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;
    /**
     * Optional
     * Pass True to copy the messages without their captions
     */
    @JsonProperty(REMOVE_CAPTION_FIELD)
    private Boolean removeCaption;


    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Tolerate
    public void setFromChatId(@NonNull Long fromChatId) {
        this.fromChatId = fromChatId.toString();
    }

    public void enableNotification() {
        this.disableNotification = null;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<MessageId> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, MessageId.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (messageIds.isEmpty() || messageIds.size() > 100) {
            throw new TelegramApiValidationException("MessageIds parameter items count must be between 1 and 100", this);
        }
    }

    public static class CopyMessagesBuilder {

        @Tolerate
        public CopyMessagesBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public CopyMessagesBuilder fromChatId(@NonNull Long fromChatId) {
            this.fromChatId = fromChatId.toString();
            return this;
        }
    }
}
