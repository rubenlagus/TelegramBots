package org.telegram.telegrambots.meta.api.objects.messageorigin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The message was originally sent by an unknown user.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class MessageOriginHiddenUser implements MessageOrigin {
    private static final String TYPE_FIELD = "type";
    private static final String DATE_FIELD = "date";
    private static final String SENDER_USER_NAME_FIELD = "sender_user_name";

    /**
     * Type of the message origin, always “hidden_user”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    /**
     * Date the message was sent originally in Unix time
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Name of the user that sent the message originally
     */
    @JsonProperty(SENDER_USER_NAME_FIELD)
    private String senderUserName;

}
