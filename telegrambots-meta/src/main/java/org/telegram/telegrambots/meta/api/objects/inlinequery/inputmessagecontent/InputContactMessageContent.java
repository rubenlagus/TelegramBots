package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents the content of a contact message to be sent as the result of an inline query
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
public class InputContactMessageContent implements InputMessageContent {

    private static final String PHONE_NUMBER_FIELD = "phone_number";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String VCARD_FIELD = "vcard";

    @JsonProperty(PHONE_NUMBER_FIELD)
    private String phoneNumber; ///< Contact's phone number
    @JsonProperty(FIRST_NAME_FIELD)
    private String firstName; ///< Contact's first name
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName; ///< Optional. Contact's last name
    @JsonProperty(VCARD_FIELD)
    private String vCard; ///< Optional. Additional data about the contact in the form of a vCard, 0-2048 bytes

    public InputContactMessageContent() {
        super();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InputContactMessageContent setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public InputContactMessageContent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InputContactMessageContent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getVCard() {
        return vCard;
    }

    public InputContactMessageContent setVCard(String vCard) {
        this.vCard = vCard;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new TelegramApiValidationException("PhoneNumber parameter can't be empty", this);
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new TelegramApiValidationException("FirstName parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "InputContactMessageContent{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", vCard='" + vCard + '\'' +
                '}';
    }
}
