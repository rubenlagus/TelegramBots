package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 3.0
 * This object represents a Telegram user or bot.
 */
public class User implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String ISBOT_FIELD = "is_bot";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";
    private static final String LANGUAGECODE_FIELD = "language_code";

    @JsonProperty(ID_FIELD)
    private Integer id; ///< Unique identifier for this user or bot
    @JsonProperty(FIRSTNAME_FIELD)
    private String firstName; ///< User‘s or bot’s first name
    @JsonProperty(ISBOT_FIELD)
    private Boolean isBot; ///< True, if this user is a bot
    @JsonProperty(LASTNAME_FIELD)
    private String lastName; ///< Optional. User‘s or bot’s last name
    @JsonProperty(USERNAME_FIELD)
    private String userName; ///< Optional. User‘s or bot’s username
    @JsonProperty(LANGUAGECODE_FIELD)
    private String languageCode; ///< Optional. IETF language tag of the user's language

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public Boolean getBot() {
        return isBot;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", isBot=" + isBot +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
