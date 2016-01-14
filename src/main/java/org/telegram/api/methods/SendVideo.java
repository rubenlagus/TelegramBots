package org.telegram.api.methods;

import org.telegram.api.objects.ReplyKeyboard;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send video files,
 * Telegram clients support mp4 videos (other formats may be sent as Document).
 * On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendVideo {
    public static final String PATH = "sendvideo";

    public static final String CHATID_FIELD = "chat_id";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    public static final String VIDEO_FIELD = "video";
    private String video; ///< Video to send. file_id as String to resend a video that is already on the Telegram servers
    public static final String DURATION_FIELD = "duration";
    private Integer duration; ///< Optional. Duration of sent video in seconds
    public static final String CAPTION_FIELD = "caption";
    private String caption; ///< OptionaL. Video caption (may also be used when resending videos by file_id).
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    public static final String REPLYMARKUP_FIELD = "reply_markup";
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

    public void setVideo(String video) {
        this.video = video;
        this.isNewVideo = false;
    }

    public void setNewVideo(String video, String videoName) {
        this.video = video;
        this.isNewVideo = true;
        this.videoName = videoName;
    }
}
