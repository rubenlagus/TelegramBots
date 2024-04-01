package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The boost was obtained by subscribing to Telegram Premium or by gifting a Telegram Premium subscription to another user.
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
public class ChatBoostSourcePremium implements ChatBoostSource {
    private static final String SOURCE_FIELD = "source";
    private static final String USER_FIELD = "user";

    /**
     * Source of the boost, always “premium”
     */
    @JsonProperty(SOURCE_FIELD)
    @Builder.Default
    private final String source = ChatBoostSource.PREMIUM_TYPE;
    /**
     * User that boosted the chat
     */
    @JsonProperty(USER_FIELD)
    private User user;
}
