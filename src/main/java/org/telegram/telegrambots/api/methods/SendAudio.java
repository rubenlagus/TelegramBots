package org.telegram.telegrambots.api.methods;

import org.telegram.telegrambots.api.objects.ReplyKeyboard;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send audio files,
 * Use this method to send audio files, if you want Telegram clients to display them in the music player.
 * Your audio must be in an .mp3 format. On success, the sent Message is returned.
 * Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
 *
 * @note For sending voice notes, use sendVoice method instead.
 *
 * @date 16 of July of 2015
 */
public class SendAudio {
    public static final String PATH = "sendaudio";

	public static final String DURATION_FIELD = "duration";
	private Integer duration; ///< Integer	Duration of the audio in seconds as defined by sender

    public static final String CHATID_FIELD = "chat_id";
    private String chatId; ///< Unique identifier for the chat to send the message to (or Username fro channels)
    public static final String AUDIO_FIELD = "audio";
    private String audio; ///< Audio file to send. file_id as String to resend an audio that is already on the Telegram servers
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */
    private Boolean disableNotification;
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    public static final String PERFOMER_FIELD = "performer";
    private String performer; ///< Optional. Performer of sent audio
    public static final String TITLE_FIELD = "title";
    private String title; ///< Optional. Title of sent audio
    private boolean isNewAudio;
    private String audioName;

    public SendAudio() {
        super();
    }

	public void setDuration(Integer duration) {
        this.duration = duration;
    }

	public Integer getDuration(){
		return this.duration;
	}

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getAudio() {
        return audio;
    }

    /**
     * Use this method to set the audio to an audio existing in Telegram system
     * @param audio File_id of the audio to send
     *
     * @note The file_id must have already been received or sent by your bot
     */
    public void setAudio(String audio) {
        this.audio = audio;
        this.isNewAudio = false;
    }

    /**
     * Use this method to set the audio to a new file
     * @param audio Path to the new file in your server
     * @param audioName Name of the file itself
     */
    public void setNewAudio(String audio, String audioName) {
        this.audio = audio;
        this.isNewAudio = true;
        this.audioName = audioName;
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

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isNewAudio() {
        return isNewAudio;
    }

    public String getAudioName() {
        return audioName;
    }
}
