package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long.
 * Use this method to send video messages. On success, the sent Message is returned.
 */
public class SendVideoNote extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendvideonote";

    public static final String CHATID_FIELD = "chat_id";
    public static final String VIDEONOTE_FIELD = "video_note";
    public static final String DURATION_FIELD = "duration";
    public static final String LENGTH_FIELD = "length";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";

    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String videoNote; ///< Videonote to send. file_id as String to resend a video that is already on the Telegram servers.
    private Integer duration; ///< Optional. Duration of sent video in seconds
    private Integer length; ///< Optional. Video width and height
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewVideoNote; ///< True to upload a new video note, false to use a fileId
    private String videoNoteName; ///< Name of the video
    private File newVideoNoteFile; ///< New video note file
    private InputStream newVideoNoteStream; ///< New video note stream

    public SendVideoNote() {
        super();
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param videoNote Video note file_id to send
     */
    public SendVideoNote(String chatId, String videoNote) {
        this.chatId = checkNotNull(chatId);
        this.videoNote = checkNotNull(videoNote);
        this.isNewVideoNote = false;
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param videoNote Video note file_id to send
     */
    public SendVideoNote(Long chatId, String videoNote) {
        this.chatId = checkNotNull(chatId).toString();
        this.videoNote = checkNotNull(videoNote);
        this.isNewVideoNote = false;
    }

    /**
     * Creates a new video note with a new video note
     * @param chatId Chat Id to send the video note
     * @param newVideoNoteFile Video note file to upload
     */
    public SendVideoNote(String chatId, File newVideoNoteFile) {
        this.chatId = checkNotNull(chatId);
        this.newVideoNoteFile = checkNotNull(newVideoNoteFile);
        this.isNewVideoNote = true;
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param newVideoNoteFile Video note file to upload
     */
    public SendVideoNote(Integer chatId, File newVideoNoteFile) {
        this.chatId = checkNotNull(chatId).toString();
        this.newVideoNoteFile = checkNotNull(newVideoNoteFile);
        this.isNewVideoNote = true;
    }



    /**
     * Creates a new video note with a new video note
     * @param chatId Chat Id to send the video note
     * @param videoNoteName Name of the video note file
     * @param newVideoNoteStream Video note file to upload
     */
    public SendVideoNote(String chatId, String videoNoteName, InputStream newVideoNoteStream) {
        this.chatId = checkNotNull(chatId);
        this.videoNoteName = checkNotNull(videoNoteName);
        this.newVideoNoteStream = checkNotNull(newVideoNoteStream);
        this.isNewVideoNote = true;
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param videoNoteName Name of the video note file
     * @param newVideoNoteStream Video note file to upload
     */
    public SendVideoNote(Integer chatId, String videoNoteName, InputStream newVideoNoteStream) {
        this.chatId = checkNotNull(chatId).toString();
        this.videoNoteName = checkNotNull(videoNoteName);
        this.newVideoNoteStream = checkNotNull(newVideoNoteStream);
        this.isNewVideoNote = true;
    }

    public String getChatId() {
        return chatId;
    }

    public SendVideoNote setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getVideoNote() {
        return videoNote;
    }

    public SendVideoNote setVideoNote(String videoNote) {
        this.videoNote = videoNote;
        this.isNewVideoNote = false;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public SendVideoNote setLength(Integer length) {
        this.length = length;
        return this;
    }

    public SendVideoNote setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public SendVideoNote setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendVideoNote setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendVideoNote setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendVideoNote enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendVideoNote disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public boolean isNewVideoNote() {
        return isNewVideoNote;
    }

    public SendVideoNote setNewVideoNote(File file) {
        this.isNewVideoNote = true;
        this.newVideoNoteFile = file;
        return this;
    }

    public SendVideoNote setNewVideo(String videoName, InputStream inputStream) {
    	Objects.requireNonNull(videoName, "videoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.videoNoteName = videoName;
        this.isNewVideoNote = true;
        this.newVideoNoteStream = inputStream;
        return this;
    }

    public String getVideoNoteName() {
        return videoNoteName;
    }

    public File getNewVideoNoteFile() {
        return newVideoNoteFile;
    }

    public InputStream getNewVideoNoteStream() {
        return newVideoNoteStream;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending video", result);
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

        if (isNewVideoNote) {
            if (newVideoNoteFile == null && newVideoNoteStream == null) {
                throw new TelegramApiValidationException("Videonote  can't be empty", this);
            }
            if (newVideoNoteStream != null && (videoNoteName == null || videoNoteName.isEmpty())) {
                throw new TelegramApiValidationException("Video note name can't be empty", this);
            }
        } else if (videoNote == null) {
            throw new TelegramApiValidationException("Video note can't be empty", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendVideoNote{" +
                "chatId='" + chatId + '\'' +
                ", videoNote='" + videoNote + '\'' +
                ", duration=" + duration +
                ", length=" + length +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", isNewVideoNote=" + isNewVideoNote +
                ", videoNoteName='" + videoNoteName + '\'' +
                ", newVideoNoteFile=" + newVideoNoteFile +
                ", newVideoNoteStream=" + newVideoNoteStream +
                '}';
    }
}
