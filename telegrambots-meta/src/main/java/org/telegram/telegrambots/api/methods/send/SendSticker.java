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
 * @brief Use this method to send .webp stickers. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendSticker extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendsticker";

    public static final String CHATID_FIELD = "chat_id";
    public static final String STICKER_FIELD = "sticker";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String sticker; ///< Sticker file to send. file_id as String to resend a sticker that is already on the Telegram servers or URL to upload it
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disable_notification;
    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean is_new_sticker; ///< True to upload a new sticker, false to use a fileId
    private String sticker_name;
    private File new_sticker_file; ///< New sticker file
    private InputStream new_sticker_stream; ///< New sticker stream

    public SendSticker() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendSticker setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendSticker setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public String getSticker() {
        return sticker;
    }

    public SendSticker setSticker(String sticker) {
        this.sticker = sticker;
        this.is_new_sticker = false;
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendSticker setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendSticker setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public SendSticker setNewSticker(File file) {
        this.is_new_sticker = true;
        this.new_sticker_file = file;
        return this;
    }

    public SendSticker setNewSticker(String stickerName, InputStream inputStream) {
    	Objects.requireNonNull(stickerName, "stickerName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.sticker_name = stickerName;
        this.is_new_sticker = true;
        this.new_sticker_stream = inputStream;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendSticker enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendSticker disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public boolean isNewSticker() {
        return is_new_sticker;
    }

    public String getStickerName() {
        return sticker_name;
    }

    public File getNewStickerFile() {
        return new_sticker_file;
    }

    public InputStream getNewStickerStream() {
        return new_sticker_stream;
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
        if (chat_id == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (is_new_sticker) {
            if (new_sticker_file == null && new_sticker_stream == null) {
                throw new TelegramApiValidationException("Sticker can't be empty", this);
            }
            if (new_sticker_stream != null && (sticker_name == null || sticker_name.isEmpty())) {
                throw new TelegramApiValidationException("Sticker name can't be empty", this);
            }
        } else if (sticker == null) {
            throw new TelegramApiValidationException("Sticker can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendSticker{" +
                "chatId='" + chat_id + '\'' +
                ", sticker='" + sticker + '\'' +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                ", isNewSticker=" + is_new_sticker +
                '}';
    }
}
