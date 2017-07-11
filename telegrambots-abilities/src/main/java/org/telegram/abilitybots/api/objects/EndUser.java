package org.telegram.abilitybots.api.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.telegram.telegrambots.api.objects.User;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * This class serves the purpose of separating the basic Telegram {@link User} and the augmented {@link EndUser}.
 * <p>
 * It adds proper hashCode, equals, toString as well as useful utility methods such as {@link EndUser#shortName} and {@link EndUser#fullName}.
 *
 * @author Abbas Abou Daya
 */
public final class EndUser implements Serializable {
  @JsonProperty("id")
  private final Integer id;
  @JsonProperty("firstName")
  private final String firstName;
  @JsonProperty("lastName")
  private final String lastName;
  @JsonProperty("username")
  private final String username;

  private EndUser(Integer id, String firstName, String lastName, String username) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
  }

  @JsonCreator
  public static EndUser endUser(@JsonProperty("id") Integer id,
                                @JsonProperty("firstName") String firstName,
                                @JsonProperty("lastName") String lastName,
                                @JsonProperty("username") String username) {
    return new EndUser(id, firstName, lastName, username);
  }

  /**
   * Constructs an {@link EndUser} from a {@link User}.
   *
   * @param user the Telegram user
   * @return an augmented end-user
   */
  public static EndUser fromUser(User user) {
    return new EndUser(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName());
  }

  public int id() {
    return id;
  }

  public String firstName() {
    return firstName;
  }

  public String lastName() {
    return lastName;
  }

  public String username() {
    return username;
  }

  /**
   * The full name is identified as the concatenation of the first and last name, separated by a space.
   * This method can return an empty name if both first and last name are empty.
   *
   * @return the full name of the user
   */
  public String fullName() {
    StringJoiner name = new StringJoiner(" ");

    if (!isEmpty(firstName))
      name.add(firstName);
    if (!isEmpty(lastName))
      name.add(lastName);

    return name.toString();
  }

  /**
   * The short name is one of the following:
   * <ol>
   * <li>First name</li>
   * <li>Last name</li>
   * <li>Username</li>
   * </ol>
   * The method will try to return the first valid name in the specified order.
   *
   * @return the short name of the user
   */
  public String shortName() {
    if (!isEmpty(firstName))
      return firstName;

    if (!isEmpty(lastName))
      return lastName;

    return username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    EndUser endUser = (EndUser) o;
    return Objects.equals(id, endUser.id) &&
        Objects.equals(firstName, endUser.firstName) &&
        Objects.equals(lastName, endUser.lastName) &&
        Objects.equals(username, endUser.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, username);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("firstName", firstName)
        .add("lastName", lastName)
        .add("username", username)
        .toString();
  }
}
