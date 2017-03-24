package org.telegram.telegrambots.api.methods.send;


import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send video files, Telegram clients support mp4 videos (other formats
 * may be sent as Document). On success, the sent Message is returned.
 * @date 20 of June of 2015
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
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String video; ///< Video to send. file_id as String to resend a video that is already on the Telegram servers or URL to upload it
    private Integer duration; ///< Optional. Duration of sent video in seconds
    private String caption; ///< OptionaL. Video caption (may also be used when resending videos by file_id).
    private Integer width; ///< Optional. Video width
    private Integer height; ///< OptionaL. Video height
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disable_notification;
    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean is_new_video; ///< True to upload a new video, false to use a fileId
    private String video_name; ///< Name of the video
    private File new_video_file; ///< New video file
    private InputStream new_video_stream; ///< New video stream

    public SendVideo() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendVideo setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public String getVideo() {
        return video;
    }

    public SendVideo setVideo(String video) {
        this.video = video;
        this.is_new_video = false;
        return this;
    }

    public SendVideo setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
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
        return reply_to_message_id;
    }

    public SendVideo setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendVideo setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public boolean isNewVideo() {
        return is_new_video;
    }

    public String getVideoName() {
        return video_name;
    }

    public File getNewVideoFile() {
        return new_video_file;
    }

    public InputStream getNewVideoStream() {
        return new_video_stream;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendVideo enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendVideo disableNotification() {
        this.disable_notification = true;
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

    public SendVideo setNewVideo(File file) {
        this.is_new_video = true;
        this.new_video_file = file;
        return this;
    }

    public SendVideo setNewVideo(String videoName, InputStream inputStream) {
    	Objects.requireNonNull(videoName, "videoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.video_name = videoName;
        this.is_new_video = true;
        this.new_video_stream = inputStream;
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
        if (chat_id == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (is_new_video) {
            if (new_video_file == null && new_video_stream == null) {
                throw new TelegramApiValidationException("Video can't be empty", this);
            }
            if (new_video_stream != null && (video_name == null || video_name.isEmpty())) {
                throw new TelegramApiValidationException("Video name can't be empty", this);
            }
        } else if (video == null) {
            throw new TelegramApiValidationException("Video can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendVideo{" +
                "chatId='" + chat_id + '\'' +
                ", video='" + video + '\'' +
                ", duration=" + duration +
                ", caption='" + caption + '\'' +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                ", isNewVideo=" + is_new_video +
                '}';
    }
}
