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
 * @brief Use this method to send information about user contact. On success, the sent Message is
 * returned.
 * @date 10 of April of 2016
 */
public class SendContact extends BotApiMethod<Message> {
    public static final String PATH = "sendContact";

    private static final String CHATID_FIELD = "chat_id";
    private static final String PHONE_NUMBER_FIELD = "phone_number";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";


    private String chat_id; ///< Unique identifier for the chat to send the message to (Or username for channels)

    private String phone_number; ///< User's phone number

    private String first_name; ///< User's first name

    private String last_name; ///< Optional. User's last name
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */

    private Boolean disable_notification;

    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message

    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    public SendContact() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendContact setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendContact setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendContact setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return reply_markup;
    }

    public SendContact setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendContact enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendContact disableNotification() {
        this.disable_notification = true;
        return this;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public SendContact setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return first_name;
    }

    public SendContact setFirstName(String firstName) {
        this.first_name = firstName;
        return this;
    }

    public String getLastName() {
        return last_name;
    }

    public SendContact setLastName(String lastName) {
        this.last_name = lastName;
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
                throw new TelegramApiRequestException("Error sending contact", result);
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
        if (phone_number == null) {
            throw new TelegramApiValidationException("PhoneNumber parameter can't be empty", this);
        }
        if (first_name == null) {
            throw new TelegramApiValidationException("FirstName parameter can't be empty", this);
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendContact{" +
                "chatId='" + chat_id + '\'' +
                ", phoneNumber=" + phone_number +
                ", firstName=" + first_name +
                ", lastName=" + last_name +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
