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
 * @brief Use this method to send photos. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendPhoto extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendphoto";

    public static final String CHATID_FIELD = "chat_id";
    public static final String PHOTO_FIELD = "photo";
    public static final String CAPTION_FIELD = "caption";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String photo; ///< Photo to send. file_id as String to resend a photo that is already on the Telegram servers or URL to upload it
    private String caption; ///< Optional Photo caption (may also be used when resending photos by file_id).
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disable_notification;
    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean is_new_photo; ///< True if the photo must be uploaded from a file, file if it is a fileId
    private String photo_name; ///< Name of the photo
    private File new_photo_file; // New photo file
    private InputStream new_photo_stream; // New photo stream

    public SendPhoto() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendPhoto setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendPhoto setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public SendPhoto setPhoto(String photo) {
        this.photo = photo;
        this.is_new_photo = false;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public SendPhoto setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendPhoto setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendPhoto setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public boolean isNewPhoto() {
        return is_new_photo;
    }

    public String getPhotoName() {
        return photo_name;
    }

    public File getNewPhotoFile() {
        return new_photo_file;
    }

    public InputStream getNewPhotoStream() {
        return new_photo_stream;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendPhoto enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendPhoto disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public SendPhoto setNewPhoto(File file) {
        this.new_photo_file = file;
        this.is_new_photo = true;
        return this;
    }

    public SendPhoto setNewPhoto(String photoName, InputStream inputStream) {
    	Objects.requireNonNull(photoName, "photoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.photo_name = photoName;
        this.new_photo_stream = inputStream;
        this.is_new_photo = true;
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
                throw new TelegramApiRequestException("Error sending photo", result);
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

        if (is_new_photo) {
            if (new_photo_file == null && new_photo_stream == null) {
                throw new TelegramApiValidationException("Photo can't be empty", this);
            }
            if (new_photo_stream != null && (photo_name == null || photo_name.isEmpty())) {
                throw new TelegramApiValidationException("Photo name can't be empty", this);
            }
        } else if (photo == null) {
            throw new TelegramApiValidationException("Photo can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendPhoto{" +
                "chatId='" + chat_id + '\'' +
                ", photo='" + photo + '\'' +
                ", caption='" + caption + '\'' +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                ", isNewPhoto=" + is_new_photo +
                '}';
    }
}
