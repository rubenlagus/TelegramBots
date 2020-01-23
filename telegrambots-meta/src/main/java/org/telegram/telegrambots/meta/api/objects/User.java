package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.Objects;

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
    private static final String CANJOINGROUPS_FIELD = "can_join_groups";
    private static final String CANREADALLGROUPMESSAGES_FIELD = "can_read_all_group_messages";
    private static final String SUPPORTINLINEQUERIES_FIELD = "supports_inline_queries";

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
    @JsonProperty(CANJOINGROUPS_FIELD)
    private Boolean canJoinGroups; ///< Optional. True, if the bot can be invited to groups. Returned only in getMe.
    @JsonProperty(CANREADALLGROUPMESSAGES_FIELD)
    private Boolean canReadAllGroupMessages; ///< Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
    @JsonProperty(SUPPORTINLINEQUERIES_FIELD)
    private Boolean supportInlineQueries; ///< Optional. True, if the bot supports inline queries. Returned only in getMe.

    public User() {
        super();
    }

    public User(Integer id, String firstName, Boolean isBot, String lastName, String userName, String languageCode) {
        this.id = id;
        this.firstName = firstName;
        this.isBot = isBot;
        this.lastName = lastName;
        this.userName = userName;
        this.languageCode = languageCode;
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

    public Boolean getCanJoinGroups() {
        return canJoinGroups;
    }

    public Boolean getCanReadAllGroupMessages() {
        return canReadAllGroupMessages;
    }

    public Boolean getSupportInlineQueries() {
        return supportInlineQueries;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(id, user.id) &&
          Objects.equals(firstName, user.firstName) &&
          Objects.equals(isBot, user.isBot) &&
          Objects.equals(lastName, user.lastName) &&
          Objects.equals(userName, user.userName) &&
          Objects.equals(languageCode, user.languageCode) &&
          Objects.equals(canJoinGroups, user.canJoinGroups) &&
          Objects.equals(canReadAllGroupMessages, user.canReadAllGroupMessages) &&
          Objects.equals(supportInlineQueries, user.supportInlineQueries);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id, firstName, isBot, lastName, userName, languageCode,
              canJoinGroups, canReadAllGroupMessages, supportInlineQueries);
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
                ", canJoinGroups=" + canJoinGroups +
                ", canReadAllGroupMessages=" + canReadAllGroupMessages +
                ", supportInlineQueries=" + supportInlineQueries +
                '}';
    }
}
