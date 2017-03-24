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
 * @brief Use this method to send audio files, Use this method to send audio files, if you want
 * Telegram clients to display them in the music player. Your audio must be in an .mp3 format. On
 * success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in
 * size, this limit may be changed in the future.
 * @note For sending voice notes, use sendVoice method instead.
 * @date 16 of July of 2015
 */
public class SendAudio extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendaudio";

    public static final String DURATION_FIELD = "duration";
    public static final String CHATID_FIELD = "chat_id";
    public static final String AUDIO_FIELD = "audio";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String PERFOMER_FIELD = "performer";
    public static final String TITLE_FIELD = "title";
    public static final String CAPTION_FIELD = "caption";

    private Integer duration; ///< Integer	Duration of the audio in seconds as defined by sender
    private String chat_id; ///< Unique identifier for the chat to send the message to (or Username fro channels)
    private String audio; ///< Audio file to send. file_id as String to resend an audio that is already on the Telegram servers or Url to upload it
    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disable_notification;
    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String performer; ///< Optional. Performer of sent audio
    private String title; ///< Optional. Title of sent audio
    private String caption; ///< Optional. Audio caption (may also be used when resending documents by file_id), 0-200 characters

    private boolean is_new_audio; ///< True to upload a new audio, false to use a fileId
    private String audio_name;
    private File new_audio_file; ///< New audio file
    private InputStream new_audio_stream; ///< New audio stream

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
        return chat_id;
    }

    public SendAudio setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendAudio setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
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
        this.is_new_audio = false;
        return this;
    }

    /**
     * Use this method to set the audio to a new file
     *
     * @param file New audio file
     */
    public SendAudio setNewAudio(File file) {
        this.is_new_audio = true;
        this.new_audio_file = file;
        return this;
    }

    public SendAudio setNewAudio(String audioName, InputStream inputStream) {
    	Objects.requireNonNull(audioName, "audioName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.audio_name = audioName;
        this.is_new_audio = true;
        this.new_audio_stream = inputStream;
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendAudio setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendAudio setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
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
        return disable_notification;
    }

    public SendAudio enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendAudio disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public boolean isNewAudio() {
        return is_new_audio;
    }

    public String getAudioName() {
        return audio_name;
    }

    public File getNewAudioFile() {
        return new_audio_file;
    }

    public InputStream getNewAudioStream() {
        return new_audio_stream;
    }

    public String getCaption() {
        return caption;
    }

    public SendAudio setCaption(String caption) {
        this.caption = caption;
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
                throw new TelegramApiRequestException("Error sending audio", result);
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

        if (is_new_audio) {
            if (new_audio_file == null && new_audio_stream == null) {
                throw new TelegramApiValidationException("Audio can't be empty", this);
            }
            if (new_audio_stream != null && (audio_name == null || audio_name.isEmpty())) {
                throw new TelegramApiValidationException("Audio name can't be empty", this);
            }
        } else if (audio == null) {
            throw new TelegramApiValidationException("Audio can't be empty", this);
        }

        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendAudio{" +
                "duration=" + duration +
                ", chatId='" + chat_id + '\'' +
                ", audio='" + audio + '\'' +
                ", replyToMessageId=" + reply_to_message_id +
                ", disableNotification=" + disable_notification +
                ", replyMarkup=" + reply_markup +
                ", performer='" + performer + '\'' +
                ", title='" + title + '\'' +
                ", isNewAudio=" + is_new_audio +
                ", audioName='" + audio_name + '\'' +
                ", newAudioFile=" + new_audio_file +
                ", newAudioStream=" + new_audio_stream +
                ", caption='" + caption + '\'' +
                '}';
    }
}
