package org.telegram.telegrambots.api.methods.send;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send audio files, Use this method to send audio files, if you want
 * Telegram clients to display them in the music player. Your audio must be in an .mp3 format. On
 * success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in
 * size, this limit may be changed in the future.
 * @note For sending voice notes, use sendVoice method instead.
 * @date 16 of July of 2015
 */
public class SendAudio {
    public static final String PATH = "sendaudio";

    public static final String DURATION_FIELD = "duration";
    public static final String CHATID_FIELD = "chat_id";
    public static final String AUDIO_FIELD = "audio";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String PERFOMER_FIELD = "performer";
    public static final String TITLE_FIELD = "title";

    private Integer duration; ///< Integer	Duration of the audio in seconds as defined by sender
    private String chatId; ///< Unique identifier for the chat to send the message to (or Username fro channels)
    private String audio; ///< Audio file to send. file_id as String to resend an audio that is already on the Telegram servers
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String performer; ///< Optional. Performer of sent audio
    private String title; ///< Optional. Title of sent audio

    private boolean isNewAudio; ///< True to upload a new audio, false to use a fileId
    private String audioName;
    private File newAudioFile; ///< New audio file
    private InputStream newAudioStream; ///< New audio stream

    public SendAudio() {
        super();
    }

    public Integer getDuration() {
        return this.duration;
    }

    public SendAudio setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getChatId() {
        return chatId;
    }

    public SendAudio setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    /**
     * Use this method to set the audio to an audio existing in Telegram system
     *
     * @param audio File_id of the audio to send
     * @note The file_id must have already been received or sent by your bot
     */
    public SendAudio setAudio(String audio) {
        this.audio = audio;
        this.isNewAudio = false;
        return this;
    }

    /**
     * Use this method to set the audio to a new file
     *
     * @param audio     Path to the new file in your server
     * @param audioName Name of the file itself
     *
     * @deprecated use {@link #setNewAudio(File)} or {@link #setNewAudio(InputStream)} instead.
     */
    @Deprecated
    public SendAudio setNewAudio(String audio, String audioName) {
        this.audio = audio;
        this.isNewAudio = true;
        this.audioName = audioName;
        return this;
    }

    /**
     * Use this method to set the audio to a new file
     *
     * @param file New audio file
     */
    public SendAudio setNewAudio(File file) {
        this.audio = file.getName();
        this.isNewAudio = true;
        this.newAudioFile = file;
        return this;
    }

    public SendAudio setNewAudio(String audioName, InputStream inputStream) {
    	Objects.requireNonNull(audioName, "audioName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.audioName = audioName;
        this.isNewAudio = true;
        this.newAudioStream = inputStream;
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendAudio setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendAudio setReplyMarkup(ReplyKeyboard replyMarkup) {
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
    public SendAudio setReplayToMessageId(Integer replyToMessageId) {
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
    public SendAudio setReplayMarkup(ReplyKeyboard replyMarkup) {
        return setReplyMarkup(replyMarkup);
    }

    public String getPerformer() {
        return performer;
    }

    public SendAudio setPerformer(String performer) {
        this.performer = performer;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SendAudio setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendAudio enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendAudio disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public boolean isNewAudio() {
        return isNewAudio;
    }

    public String getAudioName() {
        return audioName;
    }

    public File getNewAudioFile() {
        return newAudioFile;
    }

    public InputStream getNewAudioStream() {
        return newAudioStream;
    }

    @Override
    public String toString() {
        return "SendAudio{" +
                "chatId='" + chatId + '\'' +
                ", audio='" + audio + '\'' +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", performer='" + performer + '\'' +
                ", title='" + title + '\'' +
                ", isNewAudio=" + isNewAudio +
                '}';
    }
}
