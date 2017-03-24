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
 * @brief Use this method to send voice notes, if you want Telegram clients to display the file as a
 * playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS
 * (other formats may be sent as Audio or Document).
 * @date 16 of July of 2015
 */
public class SendVoice extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendvoice";

    public static final String CHATID_FIELD = "chat_id";
    public static final String VOICE_FIELD = "voice";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String DURATION_FIELD = "duration";
    public static final String CAPTION_FIELD = "caption";

    private String chat_id; ///< Unique identifier for the chat sent message to (Or username for channels)
    private String voice; ///< Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disable_notification;
    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private Integer duration; ///< Optional. Duration of sent audio in seconds
    private String caption; ///< Optional. Voice caption (may also be used when resending videos by file_id).

    private boolean is_new_voice; ///< True to upload a new voice note, false to use a fileId
    private String voice_name; ///< Name of the voice note
    private File new_voice_file; ///< New voice note file
    private InputStream new_voice_stream; ///< New voice note stream

    public SendVoice() {
        super();
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendVoice enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendVoice disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public String getChatId() {
        return chat_id;
    }

    public SendVoice setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendVoice setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public String getVoice() {
        return voice;
    }

    public SendVoice setVoice(String voice) {
        this.voice = voice;
        this.is_new_voice = false;
        return this;
    }

    public SendVoice setNewVoice(File file) {
        this.is_new_voice = true;
        this.new_voice_file = file;
        return this;
    }

    public SendVoice setNewVoice(String voiceName, InputStream inputStream) {
    	Objects.requireNonNull(voiceName, "voiceName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.voice_name = voiceName;
        this.is_new_voice = true;
        this.new_voice_stream = inputStream;
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendVoice setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendVoice setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
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
        return is_new_voice;
    }

    public String getVoiceName() {
        return voice_name;
    }

    public File getNewVoiceFile() {
        return new_voice_file;
    }

    public InputStream getNewVoiceStream() {
        return new_voice_stream;
    }

    public String getCaption() {
        return caption;
    }

    public SendVoice setCaption(String caption) {
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
                throw new TelegramApiRequestException("Error sending voice", result);
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

        if (is_new_voice) {
            if (new_voice_file == null && new_voice_stream == null) {
                throw new TelegramApiValidationException("Voice can't be empty", this);
            }
            if (new_voice_stream != null && (voice_name == null || voice_name.isEmpty())) {
                throw new TelegramApiValidationException("Voice name can't be empty", this);
            }
        } else if (voice == null) {
            throw new TelegramApiValidationException("Voice can't be empty", this);
        }

        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendVoice{" +
                "chatId='" + chat_id + '\'' +
                ", voice='" + voice + '\'' +
                ", disableNotification=" + disable_notification +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                ", duration=" + duration +
                ", caption='" + caption + '\'' +
                ", isNewVoice=" + is_new_voice +
                ", voiceName='" + voice_name + '\'' +
                ", newVoiceFile=" + new_voice_file +
                ", newVoiceStream=" + new_voice_stream +
                '}';
    }
}
