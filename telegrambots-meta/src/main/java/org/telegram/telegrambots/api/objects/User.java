package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a Telegram user or bot.
 * @date 20 of June of 2015
 */
public class User implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";


    private Integer id; ///< Unique identifier for this user or bot

    private String first_name; ///< User‘s or bot’s first name

    private String last_name; ///< Optional. User‘s or bot’s last name

    private String user_name; ///< Optional. User‘s or bot’s username

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getUserName() {
        return user_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + first_name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", userName='" + user_name + '\'' +
                '}';
    }
}
