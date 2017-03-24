package org.telegram.telegrambots.api.methods.send;



import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send point on the map. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendLocation extends BotApiMethod<Message> {
    public static final String PATH = "sendlocation";

    private static final String CHATID_FIELD = "chat_id";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    private Float latitude; ///< Latitude of location

    private Float longitude; ///< Longitude of location
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */

    private Boolean disable_notification;

    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message

    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendLocation() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendLocation setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendLocation setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public SendLocation setLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getLongitude() {
        return longitude;
    }

    public SendLocation setLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendLocation setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendLocation setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendLocation enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendLocation disableNotification() {
        this.disable_notification = true;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending location", result);
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
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude parameter can't be empty", this);
        }
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude parameter can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendLocation{" +
                "chatId='" + chat_id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
