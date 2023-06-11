package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
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
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SendChatAction extends BotApiMethodBoolean {

    public static final String PATH = "sendChatAction";

    public static final String CHATID_FIELD = "chat_id";
    private static final String ACTION_FIELD = "action";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";

    @JsonProperty(CHATID_FIELD)
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
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;

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

    public static class SendChatActionBuilder {

        @Tolerate
        public SendChatActionBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
