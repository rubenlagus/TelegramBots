package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The boost was obtained by the creation of Telegram Premium gift codes to boost a chat.
 * Each such code boosts the chat 4 times for the duration of the corresponding Telegram Premium subscription.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ChatBoostSourceGiftCode implements ChatBoostSource {
    private static final String SOURCE_FIELD = "source";
    private static final String USER_FIELD = "user";

    /**
     * Source of the boost, always “gift_code”
     */
    @JsonProperty(SOURCE_FIELD)
    private String source;
    /**
     * User for which the gift code was created
     */
    @JsonProperty(USER_FIELD)
    private User user;
}
