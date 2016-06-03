package org.telegram.telegrambots.api.methods.send;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send voice notes, if you want Telegram clients to display the file as a
 * playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS
 * (other formats may be sent as Audio or Document).
 * @date 16 of July of 2015
 */
public class SendVoice {
    public static final String PATH = "sendvoice";

    public static final String CHATID_FIELD = "chat_id";
    public static final String AUDIO_FIELD = "audio";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String DURATION_FIELD = "duration";
    private String chatId; ///< Unique identifier for the chat sent message to (Or username for channels)
    private String audio; ///< Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private Integer duration; ///< Optional. Duration of sent audio in seconds

    private boolean isNewVoice; ///< True to upload a new voice note, false to use a fileId
    private File newVoiceFile; ///< New voice note file

    public SendVoice() {
        super();
    }

    @Override
    public String toString() {
        return "SendVoice{" +
                "chatId='" + chatId + '\'' +
                ", audio='" + audio + '\'' +
                ", replayToMessageId=" + replayToMessageId +
                ", replayMarkup=" + replayMarkup +
                ", duration=" + duration +
                '}';
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendVoice enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendVoice disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public String getChatId() {
        return chatId;
    }

    public SendVoice setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public SendVoice setAudio(String audio) {
        this.audio = audio;
        this.isNewVoice = false;
        return this;
    }

    public SendVoice setNewAudio(File file) {
        this.audio = file.getName();
        this.isNewVoice = true;
        this.newVoiceFile = file;
        return this;
    }

    public Integer getReplayToMessageId() {
        return replayToMessageId;
    }

    public SendVoice setReplayToMessageId(Integer replayToMessageId) {
        this.replayToMessageId = replayToMessageId;
        return this;
    }

    public ReplyKeyboard getReplayMarkup() {
        return replayMarkup;
    }

    public SendVoice setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replayMarkup = replayMarkup;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public SendVoice setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public boolean isNewVoice() {
        return isNewVoice;
    }

    public File getNewVoiceFile() {
        return newVoiceFile;
    }
}
