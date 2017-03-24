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
 * @brief Use this method to send information about a venue. On success, the sent Message is
 * returned.
 * @date 10 of April of 2016
 */
public class SendVenue extends BotApiMethod<Message> {
    public static final String PATH = "sendVenue";

    private static final String CHATID_FIELD = "chat_id";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String TITLE_FIELD = "title";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUARE_ID_FIELD = "foursquare_id";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    private Float latitude; ///< Latitude of venue location

    private Float longitude; ///< Longitude of venue location

    private String title; ///< Title of the venue
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */

    private Boolean disable_notification;

    private String address; ///< Address of the venue

    private String foursquare_id; ///< Optional. Foursquare identifier of the venue

    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message

    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendVenue() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendVenue setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendVenue setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public SendVenue setLatitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getLongitude() {
        return longitude;
    }

    public SendVenue setLongitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendVenue setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendVenue setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendVenue enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendVenue disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SendVenue setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SendVenue setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getFoursquareId() {
        return foursquare_id;
    }

    public SendVenue setFoursquareId(String foursquareId) {
        this.foursquare_id = foursquareId;
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
                throw new TelegramApiRequestException("Error sending venue", result);
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
        if (longitude == null) {
            throw new TelegramApiValidationException("Longitude parameter can't be empty", this);
        }
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (address == null) {
            throw new TelegramApiValidationException("Address parameter can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendVenue{" +
                "chatId='" + chat_id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", title=" + title +
                ", address=" + address +
                ", foursquareId=" + foursquare_id +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
