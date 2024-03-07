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
 * The boost was obtained by subscribing to Telegram Premium or by gifting a Telegram Premium subscription to another user.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ChatBoostSourcePremium implements ChatBoostSource {
    private static final String SOURCE_FIELD = "source";
    private static final String USER_FIELD = "user";

    /**
     * Source of the boost, always “premium”
     */
    @JsonProperty(SOURCE_FIELD)
    private String source;
    /**
     * User that boosted the chat
     */
    @JsonProperty(USER_FIELD)
    private User user;
}
