package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

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
public class SendSticker extends BotApiMethod<Message> {
    public static final String PATH = "sendsticker";

    public static final String CHATID_FIELD = "chat_id";
    public static final String STICKER_FIELD = "sticker";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    private String sticker; ///< Sticker file to send. file_id as String to resend a sticker that is already on the Telegram servers
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewSticker; ///< True to upload a new sticker, false to use a fileId
    private String stickerName;
    private File newStickerFile; ///< New sticker file
    private InputStream newStickerStream; ///< New sticker stream

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

    public String getSticker() {
        return sticker;
    }

    public SendSticker setSticker(String sticker) {
        this.sticker = sticker;
        this.isNewSticker = false;
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
    public SendSticker setReplayToMessageId(Integer replyToMessageId) {
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
    public SendSticker setReplayMarkup(ReplyKeyboard replyMarkup) {
        return setReplyMarkup(replyMarkup);
    }

    /**
     * Use this method to set the sticker to a new file
     *
     * @param sticker     Path to the new file in your server
     * @param stickerName Name of the file itself
     * @deprecated use {@link #setNewSticker(File)} or {@link #setNewSticker(InputStream)} instead.
     */
    @Deprecated
    public SendSticker setSticker(String sticker, String stickerName) {
        this.sticker = sticker;
        this.isNewSticker = true;
        this.stickerName = stickerName;
        return this;
    }

    public SendSticker setNewSticker(File file) {
        this.sticker = file.getName();
        this.isNewSticker = true;
        this.newStickerFile = file;
        return this;
    }

    public SendSticker setNewSticker(String stickerName, InputStream inputStream) {
        Objects.requireNonNull(stickerName, "stickerName cannot be null!");
        Objects.requireNonNull(inputStream, "inputStream cannot be null!");
        this.stickerName = stickerName;
        this.isNewSticker = true;
        this.newStickerStream = inputStream;
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

    public boolean isNewSticker() {
        return isNewSticker;
    }

    public String getStickerName() {
        return stickerName;
    }

    public File getNewStickerFile() {
        return newStickerFile;
    }

    public InputStream getNewStickerStream() {
        return newStickerStream;
    }

    @Override
    public String toString() {
        return "SendSticker{" +
                "chatId='" + chatId + '\'' +
                ", sticker='" + sticker + '\'' +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", isNewSticker=" + isNewSticker +
                '}';
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            return new Message(answer.getJSONObject(Constants.RESPONSEFIELDRESULT));
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeStringField(CHATID_FIELD, chatId);
        gen.writeStringField(STICKER_FIELD, sticker);

        if (disableNotification != null) {
            gen.writeBooleanField(DISABLENOTIFICATION_FIELD, disableNotification);
        }
        if (replyToMessageId != null) {
            gen.writeNumberField(REPLYTOMESSAGEID_FIELD, replyToMessageId);
        }
        if (replyMarkup != null) {
            gen.writeObjectField(REPLYMARKUP_FIELD, replyMarkup);
        }

        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        serialize(jsonGenerator, serializerProvider);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CHATID_FIELD, chatId);
        jsonObject.put(STICKER_FIELD, sticker);

        if (disableNotification != null) {
            jsonObject.put(DISABLENOTIFICATION_FIELD, disableNotification);
        }
        if (replyToMessageId != null) {
            jsonObject.put(REPLYTOMESSAGEID_FIELD, replyToMessageId);
        }
        if (replyMarkup != null) {
            jsonObject.put(REPLYMARKUP_FIELD, replyMarkup.toJson());
        }

        return jsonObject;
    }
}
