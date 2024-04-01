package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method when you need to tell the user that something is happening on the bot's
 * side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram
 * clients clear its typing status).
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendChatAction extends BotApiMethodBoolean {

    public static final String PATH = "sendChatAction";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String ACTION_FIELD = "action";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Type of action to broadcast. Choose one, depending on what the user is about to receive:
     * typing for text messages
     * upload_photo for photos
     * record_video or upload_video for videos
     * record_voice or upload_voice for voice notes
     * upload_document for general files
     * choose_sticker for stickers
     * find_location for location data
     * record_video_note
     * upload_video_note for video notes
     */
    @JsonProperty(ACTION_FIELD)
    @NonNull
    private String action;
    /**
     * Optional
     * Unique identifier for the target message thread; supergroups only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    /**
     * Optional.
     * Unique identifier of the business connection on behalf of which the action will be sent.
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @JsonIgnore
    public ActionType getActionType() {
        return ActionType.get(action);
    }

    @JsonIgnore
    public void setAction(ActionType action) {
        this.action = action.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (action.isEmpty()) {
            throw new TelegramApiValidationException("Action parameter can't be empty", this);
        }
    }

    public static abstract class SendChatActionBuilder<C extends SendChatAction, B extends SendChatActionBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public SendChatActionBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
