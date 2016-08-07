package org.telegram.telegrambots.api.methods.send;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send video files, Telegram clients support mp4 videos (other formats
 * may be sent as Document). On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendVideo {
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
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String video; ///< Video to send. file_id as String to resend a video that is already on the Telegram servers
    private Integer duration; ///< Optional. Duration of sent video in seconds
    private String caption; ///< OptionaL. Video caption (may also be used when resending videos by file_id).
    private Integer width; ///< Optional. Video width
    private Integer height; ///< OptionaL. Video height
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewVideo; ///< True to upload a new video, false to use a fileId
    private String videoName; ///< Name of the video
    private File newVideoFile; ///< New video file
    private InputStream newVideoStream; ///< New video stream

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

    public String getVideo() {
        return video;
    }

    public SendVideo setVideo(String video) {
        this.video = video;
        this.isNewVideo = false;
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

    /**
     * @deprecated Use {@link #getReplyToMessageId()} instead.
     */
    @Deprecated
    public Integer getReplayToMessageId() {
        return getReplyToMessageId();
    }

    /**
     * @deprecated Use {@link #setReplyToMessageId(Integer)} instead.
     */
    @Deprecated
    public SendVideo setReplayToMessageId(Integer replyToMessageId) {
        return setReplyToMessageId(replyToMessageId);
    }

    /**
     * @deprecated Use {@link #getReplyMarkup()} instead.
     */
    @Deprecated
    public ReplyKeyboard getReplayMarkup() {
        return getReplyMarkup();
    }

    /**
     * @deprecated Use {@link #setReplyMarkup(ReplyKeyboard)} instead.
     */
    @Deprecated
    public SendVideo setReplayMarkup(ReplyKeyboard replyMarkup) {
        return setReplyMarkup(replyMarkup);
    }

    public boolean isNewVideo() {
        return isNewVideo;
    }

    public String getVideoName() {
        return videoName;
    }

    public File getNewVideoFile() {
        return newVideoFile;
    }

    public InputStream getNewVideoStream() {
        return newVideoStream;
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

    /**
     * Use this method to set the video to a new file
     *
     * @param video     Path to the new file in your server
     * @param videoName Name of the file itself
     *
     * @deprecated use {@link #setNewVideo(File)} or {@link #setNewVideo(InputStream)} instead.
     */
    @Deprecated
    public SendVideo setNewVideo(String video, String videoName) {
        this.video = video;
        this.isNewVideo = true;
        this.videoName = videoName;
        return this;
    }

    public SendVideo setNewVideo(File file) {
        this.video = file.getName();
        this.isNewVideo = true;
        this.newVideoFile = file;
        return this;
    }

    public SendVideo setNewVideo(String videoName, InputStream inputStream) {
    	Objects.requireNonNull(videoName, "videoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.videoName = videoName;
        this.isNewVideo = true;
        this.newVideoStream = inputStream;
        return this;
    }

    @Override
    public String toString() {
        return "SendVideo{" +
                "chatId='" + chatId + '\'' +
                ", video='" + video + '\'' +
                ", duration=" + duration +
                ", caption='" + caption + '\'' +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", isNewVideo=" + isNewVideo +
                '}';
    }
}
