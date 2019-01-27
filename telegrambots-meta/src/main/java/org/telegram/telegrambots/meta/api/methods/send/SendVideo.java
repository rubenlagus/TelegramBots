package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send video files, Telegram clients support mp4 videos (other formats
 * may be sent as Document). On success, the sent Message is returned.
 */
public class SendVideo extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendvideo";

    public static final String CHATID_FIELD = "chat_id";
    public static final String VIDEO_FIELD = "video";
    public static final String DURATION_FIELD = "duration";
    public static final String CAPTION_FIELD = "caption";
    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String SUPPORTSSTREAMING_FIELD = "supports_streaming";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String THUMB_FIELD = "thumb";

    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private InputFile video; ///< Video to send. file_id as String to resend a video that is already on the Telegram servers or URL to upload it
    private Integer duration; ///< Optional. Duration of sent video in seconds
    private String caption; ///< Optional. Video caption (may also be used when resending videos by file_id).
    private Integer width; ///< Optional. Video width
    private Integer height; ///< Optional. Video height
    private Boolean supportsStreaming; ///< Optional. Pass True, if the uploaded video is suitable for streaming
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail‘s width and height should not exceed 90.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

    public SendVideo() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public SendVideo setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public InputFile getVideo() {
        return video;
    }

    public SendVideo setVideo(String video) {
        this.video = new InputFile(video);
        return this;
    }

    public SendVideo setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public SendVideo setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public SendVideo setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendVideo setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendVideo setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendVideo enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendVideo disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public SendVideo setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public SendVideo setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public SendVideo setVideo(InputFile video) {
        Objects.requireNonNull(video, "video cannot be null!");
        this.video = video;
        return this;
    }

    public SendVideo setVideo(File file) {
        Objects.requireNonNull(file, "file cannot be null!");
        this.video = new InputFile(file, file.getName());
        return this;
    }

    public SendVideo setVideo(String videoName, InputStream inputStream) {
    	Objects.requireNonNull(videoName, "videoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.video = new InputFile(inputStream, videoName);
        return this;
    }

    public Boolean getSupportsStreaming() {
        return supportsStreaming;
    }

    public SendVideo setSupportsStreaming(Boolean supportsStreaming) {
        this.supportsStreaming = supportsStreaming;
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public SendVideo setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public SendVideo setThumb(InputFile thumb) {
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

        if (video == null) {
            throw new TelegramApiValidationException("Video parameter can't be empty", this);
        }

        video.validate();

        if (thumb != null) {
            thumb.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendVideo{" +
                "chatId='" + chatId + '\'' +
                ", video=" + video +
                ", duration=" + duration +
                ", caption='" + caption + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", supportsStreaming=" + supportsStreaming +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", parseMode='" + parseMode + '\'' +
                ", thumb=" + thumb +
                '}';
    }
}
