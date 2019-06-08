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
 * Use this method to send photos. On success, the sent Message is returned.
 */
public class SendPhoto extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendphoto";

    public static final String CHATID_FIELD = "chat_id";
    public static final String PHOTO_FIELD = "photo";
    public static final String CAPTION_FIELD = "caption";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String PARSEMODE_FIELD = "parse_mode";

    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private InputFile photo; ///< Photo to send. file_id as String to resend a photo that is already on the Telegram servers or URL to upload it
    private String caption; ///< Optional Photo caption (may also be used when resending photos by file_id).
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.

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

    public InputFile getPhoto() {
        return photo;
    }

    public SendPhoto setPhoto(String photo) {
        this.photo = new InputFile(photo);
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

    public SendPhoto setPhoto(File file) {
        Objects.requireNonNull(file, "file cannot be null!");
        this.photo = new InputFile(file, file.getName());
        return this;
    }

    public SendPhoto setPhoto(InputFile photo) {
        Objects.requireNonNull(photo, "photo cannot be null!");
        this.photo = photo;
        return this;
    }

    public SendPhoto setPhoto(String photoName, InputStream inputStream) {
    	Objects.requireNonNull(photoName, "photoName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.photo = new InputFile(inputStream, photoName);
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public SendPhoto setParseMode(String parseMode) {
        this.parseMode = parseMode;
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

        if (photo == null) {
            throw new TelegramApiValidationException("Photo parameter can't be empty", this);
        }

        photo.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendPhoto{" +
                "chatId='" + chatId + '\'' +
                ", photo=" + photo +
                ", caption='" + caption + '\'' +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", parseMode='" + parseMode + '\'' +
                '}';
    }
}
