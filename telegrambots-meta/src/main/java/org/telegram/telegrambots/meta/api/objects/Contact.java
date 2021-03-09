package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a phone contact.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements BotApiObject {

    private static final String PHONENUMBER_FIELD = "phone_number";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERID_FIELD = "user_id";
    private static final String VCARD_FIELD = "vcard";

    @JsonProperty(PHONENUMBER_FIELD)
    private String phoneNumber; ///< Contact's phone number
    @JsonProperty(FIRSTNAME_FIELD)
    private String firstName; ///< Contact's first name
    @JsonProperty(LASTNAME_FIELD)
    private String lastName; ///< Optional. Contact's last name
    /**
     * Optional.
     * Contact's user identifier in Telegram.
     *
     * @apiNote This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
     */
    @JsonProperty(USERID_FIELD)
    private Long userId; ///< Optional. Contact's user identifier in Telegram
    @JsonProperty(VCARD_FIELD)
    private String vCard; ///< Optional. Additional data about the contact in the form of a vCard
}
