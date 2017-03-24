package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a phone contact.
 * @date 20 of June of 2015
 */
public class Contact implements BotApiObject {

    private static final String PHONENUMBER_FIELD = "phone_number";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERID_FIELD = "user_id";


    private String phone_number; ///< Contact's phone number

    private String first_name; ///< Contact's first name

    private String last_name; ///< Optional. Contact's last name

    private Integer user_id; ///< Optional. Contact's user identifier in Telegram

    public Contact() {
        super();
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public Integer getUserID() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber='" + phone_number + '\'' +
                ", firstName='" + first_name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", userID=" + user_id +
                '}';
    }
}
