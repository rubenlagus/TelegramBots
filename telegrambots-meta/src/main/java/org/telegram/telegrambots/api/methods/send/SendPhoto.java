package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.core.type.TypeReference;

import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

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
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String photo; ///< Photo to send. file_id as String to resend a photo that is already on the Telegram servers or URL to upload it
    private String caption; ///< Optional Photo caption (may also be used when resending photos by file_id).
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewPhoto; ///< True if the photo must be uploaded from a file, file if it is a fileId
    private String photoName; ///< Name of the photo
    private File newPhotoFile; // New photo file
    private InputStream newPhotoStream; // New photo stream

    public SendPhoto() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public SendPhoto setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendPhoto setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public SendPhoto setPhoto(String photo) {
        this.photo = photo;
        this.isNewPhoto = false;
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
        return replyToMessageId;
    }

    public SendPhoto setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendPhoto setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public boolean isNewPhoto() {
        return isNewPhoto;
    }

    public String getPhotoName() {
        return photoName;
    }

    public File getNewPhotoFile() {
        return newPhotoFile;
    }

    public InputStream getNewPhotoStream() {
        return newPhotoStream;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendPhoto enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendPhoto disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public SendPhoto setNewPhoto(File file) {
        this.newPhotoFile = file;
        this.isNewPhoto = true;
        return this;
    }

    public SendPhoto setNewPhoto(String photoName, InputStream inputStream) {
    	Objects.requireNonNull(photoName, "photoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.photoName = photoName;
        this.newPhotoStream = inputStream;
        this.isNewPhoto = true;
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
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (isNewPhoto) {
            if (newPhotoFile == null && newPhotoStream == null) {
                throw new TelegramApiValidationException("Photo can't be empty", this);
            }
            if (newPhotoStream != null && (photoName == null || photoName.isEmpty())) {
                throw new TelegramApiValidationException("Photo name can't be empty", this);
            }
        } else if (photo == null) {
            throw new TelegramApiValidationException("Photo can't be empty", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendPhoto{" +
                "chatId='" + chatId + '\'' +
                ", photo='" + photo + '\'' +
                ", caption='" + caption + '\'' +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", isNewPhoto=" + isNewPhoto +
                '}';
    }
}
