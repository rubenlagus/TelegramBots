package org.telegram.telegrambots.api.methods.send;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

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
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewVideo; ///< True to upload a new video, false to use a fileId
    private String videoName; ///< Name of the video

    public SendVideo() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
        this.isNewVideo = false;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getReplayToMessageId() {
        return replayToMessageId;
    }

    public void setReplayToMessageId(Integer replayToMessageId) {
        this.replayToMessageId = replayToMessageId;
    }

    public ReplyKeyboard getReplayMarkup() {
        return replayMarkup;
    }

    public void setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replayMarkup = replayMarkup;
    }

    public boolean isNewVideo() {
        return isNewVideo;
    }

    public String getVideoName() {
        return videoName;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setNewVideo(String video, String videoName) {
        this.video = video;
        this.isNewVideo = true;
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "SendVideo{" +
                "chatId='" + chatId + '\'' +
                ", video='" + video + '\'' +
                ", duration=" + duration +
                ", caption='" + caption + '\'' +
                ", replayToMessageId=" + replayToMessageId +
                ", replayMarkup=" + replayMarkup +
                ", isNewVideo=" + isNewVideo +
                ", videoName='" + videoName + '\'' +
                '}';
    }
}
