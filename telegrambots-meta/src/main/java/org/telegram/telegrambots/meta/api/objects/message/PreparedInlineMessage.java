package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 8.0
 *
 * Describes an inline message to be sent by a user of a Mini App.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreparedInlineMessage implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String EXPIRATION_DATE_FIELD = "expiration_date";

    /**
     * Unique identifier of the prepared message
     */
    @JsonProperty(ID_FIELD)
    private String messageId;
    /**
     * Expiration date of the prepared message, in Unix time. Expired prepared messages can no longer be used
     */
    @JsonProperty(EXPIRATION_DATE_FIELD)
    private Integer expirationDate;
}
