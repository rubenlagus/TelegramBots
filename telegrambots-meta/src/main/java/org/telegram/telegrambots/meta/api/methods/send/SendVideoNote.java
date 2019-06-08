package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

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
@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class SendVideoNote extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendvideonote";

    public static final String CHATID_FIELD = "chat_id";
    public static final String VIDEONOTE_FIELD = "video_note";
    public static final String DURATION_FIELD = "duration";
    public static final String LENGTH_FIELD = "length";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String THUMB_FIELD = "thumb";

    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private InputFile videoNote; ///< Videonote to send. file_id as String to resend a video that is already on the Telegram servers.
    private Integer duration; ///< Optional. Duration of sent video in seconds
    private Integer length; ///< Optional. Video width and height
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

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
        this.setVideoNote(checkNotNull(videoNote));
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param videoNote Video note file_id to send
     */
    public SendVideoNote(Long chatId, String videoNote) {

        this.chatId = checkNotNull(chatId).toString();
        this.setVideoNote(checkNotNull(videoNote));
    }

    /**
     * Creates a new video note with a new video note
     * @param chatId Chat Id to send the video note
     * @param videoNote Video note file to upload
     */
    public SendVideoNote(String chatId, File videoNote) {
        this.chatId = checkNotNull(chatId);
        this.setVideoNote(videoNote);
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param videoNote Video note file to upload
     */
    public SendVideoNote(Integer chatId, File videoNote) {
        this.chatId = checkNotNull(chatId).toString();
        this.setVideoNote(videoNote);
    }

    /**
     * Creates a new video note with a new video note
     * @param chatId Chat Id to send the video note
     * @param videoNoteName Name of the video note file
     * @param videoNote Video note file to upload
     */
    public SendVideoNote(String chatId, String videoNoteName, InputStream videoNote) {
        this.chatId = checkNotNull(chatId);
        this.setVideoNote(videoNoteName, videoNote);
    }

    /**
     * Creates a new video note with a video already present in telegram servers
     * @param chatId Chat Id to send the video note
     * @param videoNoteName Name of the video note file
     * @param videoNote Video note file to upload
     */
    public SendVideoNote(Integer chatId, String videoNoteName, InputStream videoNote) {
        this.chatId = checkNotNull(chatId).toString();
        this.setVideoNote(videoNoteName, videoNote);
    }

    public String getChatId() {
        return chatId;
    }

    public SendVideoNote setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public InputFile getVideoNote() {
        return videoNote;
    }

    public SendVideoNote setVideoNote(String videoNote) {
        this.videoNote = new InputFile(videoNote);
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

    public SendVideoNote setVideoNote(InputFile videoNote) {
        Objects.requireNonNull(videoNote, "videoNote cannot be null!");
        this.videoNote = videoNote;
        return this;
    }

    public SendVideoNote setVideoNote(File file) {
        Objects.requireNonNull(file, "file cannot be null!");
        this.videoNote = new InputFile(file, file.getName());
        return this;
    }

    public SendVideoNote setVideoNote(String videoName, InputStream inputStream) {
    	Objects.requireNonNull(videoName, "videoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.videoNote = new InputFile(inputStream, videoName);
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public SendVideoNote setThumb(InputFile thumb) {
        this.thumb = thumb;
        return this;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending video note", result);
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

        if (videoNote == null) {
            throw new TelegramApiValidationException("VideoNote parameter can't be empty", this);
        }

        videoNote.validate();

        if (thumb != null) {
            thumb.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendVideoNote{" +
                "chatId='" + chatId + '\'' +
                ", videoNote=" + videoNote +
                ", duration=" + duration +
                ", length=" + length +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", thumb=" + thumb +
                '}';
    }
}
