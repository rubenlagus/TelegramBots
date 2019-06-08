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

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send audio files, Use this method to send audio files, if you want
 * Telegram clients to display them in the music player. Your audio must be in an .mp3 format. On
 * success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in
 * size, this limit may be changed in the future.
 * @note For sending voice notes, use sendVoice method instead.
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
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String THUMB_FIELD = "thumb";

    private Integer duration; ///< Integer	Duration of the audio in seconds as defined by sender
    private String chatId; ///< Unique identifier for the chat to send the message to (or Username fro channels)
    private InputFile audio; ///< Audio file to send. file_id as String to resend an audio that is already on the Telegram servers or Url to upload it
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String performer; ///< Optional. Performer of sent audio
    private String title; ///< Optional. Title of sent audio
    private String caption; ///< Optional. Audio caption (may also be used when resending documents by file_id), 0-200 characters
    private String parseMode; ///< Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass
     * “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

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

    public SendAudio setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public InputFile getAudio() {
        return audio;
    }

    /**
     * Use this method to set the audio to an audio existing in Telegram system
     *
     * @param audio File_id of the audio to send
     * @note The file_id must have already been received or sent by your bot
     */
    public SendAudio setAudio(String audio) {
        this.audio = new InputFile(audio);
        return this;
    }

    /**
     * Use this method to set the audio to a new file
     *
     * @param file New audio file
     */
    public SendAudio setAudio(File file) {
        Objects.requireNonNull(file, "file cannot be null!");
        this.audio = new InputFile(file, file.getName());
        return this;
    }

    public SendAudio setAudio(String audioName, InputStream inputStream) {
    	Objects.requireNonNull(audioName, "audioName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.audio = new InputFile(inputStream, audioName);
        return this;
    }

    public SendAudio setAudio(InputFile audio) {
        Objects.requireNonNull(audio, "audio cannot be null!");
        this.audio = audio;
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

    public String getCaption() {
        return caption;
    }

    public SendAudio setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public SendAudio setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public SendAudio setThumb(InputFile thumb) {
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
                throw new TelegramApiRequestException("Error sending audio", result);
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

        if (audio == null) {
            throw new TelegramApiValidationException("Audio parameter can't be empty", this);
        }

        audio.validate();

        if (thumb != null) {
            thumb.validate();
        }

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendAudio{" +
                "duration=" + duration +
                ", chatId='" + chatId + '\'' +
                ", audio=" + audio +
                ", replyToMessageId=" + replyToMessageId +
                ", disableNotification=" + disableNotification +
                ", replyMarkup=" + replyMarkup +
                ", performer='" + performer + '\'' +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", thumb=" + thumb +
                '}';
    }
}
