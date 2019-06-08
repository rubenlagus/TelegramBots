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
 * Use this method to send .webp stickers. On success, the sent Message is returned.
 */
public class SendSticker extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendsticker";

    public static final String CHATID_FIELD = "chat_id";
    public static final String STICKER_FIELD = "sticker";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private InputFile sticker; ///< Sticker file to send. file_id as String to resend a sticker that is already on the Telegram servers or URL to upload it
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendSticker() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public SendSticker setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendSticker setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public InputFile getSticker() {
        return sticker;
    }

    public SendSticker setSticker(String sticker) {
        this.sticker = new InputFile(sticker);
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendSticker setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendSticker setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public SendSticker setSticker(InputFile sticker) {
        Objects.requireNonNull(sticker, "sticker cannot be null!");
        this.sticker = sticker;
        return this;
    }

    public SendSticker setSticker(File file) {
        Objects.requireNonNull(file, "file cannot be null!");
        this.sticker = new InputFile(file, file.getName());
        return this;
    }

    public SendSticker setSticker(String stickerName, InputStream inputStream) {
    	Objects.requireNonNull(stickerName, "stickerName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.sticker = new InputFile(inputStream, stickerName);
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendSticker enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendSticker disableNotification() {
        this.disableNotification = true;
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
                throw new TelegramApiRequestException("Error sending sticker", result);
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

        if (sticker == null) {
            throw new TelegramApiValidationException("Sticker parameter can't be empty", this);
        }

        sticker.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendSticker{" +
                "chatId='" + chatId + '\'' +
                ", sticker=" + sticker +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                '}';
    }
}
