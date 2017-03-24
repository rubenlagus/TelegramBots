package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;



import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents the content of a contact message to be sent as the result of an inline query
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InputContactMessageContent implements InputMessageContent {

    private static final String PHONE_NUMBER_FIELD = "phone_number";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";


    private String phone_number; ///< Contact's phone number

    private String first_name; ///< Contact's first name

    private String last_name; ///< Optional. Contact's last name

    public InputContactMessageContent() {
        super();
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public InputContactMessageContent setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return first_name;
    }

    public InputContactMessageContent setFirstName(String firstName) {
        this.first_name = firstName;
        return this;
    }

    public String getLastName() {
        return last_name;
    }

    public InputContactMessageContent setLastName(String lastName) {
        this.last_name = lastName;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (phone_number == null || phone_number.isEmpty()) {
            throw new TelegramApiValidationException("PhoneNumber parameter can't be empty", this);
        }
        if (phone_number == null || phone_number.isEmpty()) {
            throw new TelegramApiValidationException("FirstName parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "InputContactMessageContent{" +
                "phoneNumber='" + phone_number + '\'' +
                ", firstName='" + phone_number + '\'' +
                ", lastName='" + phone_number + '\'' +
                '}';
    }
}
