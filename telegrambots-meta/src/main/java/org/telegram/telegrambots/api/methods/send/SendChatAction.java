package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.ActionType;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method when you need to tell the user that something is happening on the bot's
 * side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram
 * clients clear its typing status).
 */
public class SendChatAction extends BotApiMethod<Boolean> {

    public static final String PATH = "sendChatAction";

    public static final String CHATID_FIELD = "chat_id";
    public static final String ACTION_FIELD = "action";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages,
     * upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files,
     * upload_document for general files, find_location for location data,
     * record_video_note or upload_video_note for video notes.
     */
    @JsonProperty(ACTION_FIELD)
    private String action;

    public SendChatAction() {
        super();
    }

    public SendChatAction(String chatId, String action) {
        this.chatId = checkNotNull(chatId);
        this.action = checkNotNull(action);
    }

    public SendChatAction(Long chatId, String action) {
        this.chatId = checkNotNull(chatId).toString();
        this.action = checkNotNull(action);
    }

    public String getChatId() {
        return chatId;
    }

    @JsonIgnore
    public ActionType getAction() {
        return ActionType.get(action);
    }

    public SendChatAction setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendChatAction setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    @JsonIgnore
    public SendChatAction setAction(ActionType action) {
        this.action = action.toString();
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending chat action", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (action == null) {
            throw new TelegramApiValidationException("Action parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "SendChatAction{" +
                "chatId='" + chatId + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
