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
 * The boost was obtained by the creation of Telegram Premium gift codes to boost a chat.
 * Each such code boosts the chat 4 times for the duration of the corresponding Telegram Premium subscription.
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
public class ChatBoostSourceGiftCode implements ChatBoostSource {
    private static final String SOURCE_FIELD = "source";
    private static final String USER_FIELD = "user";

    /**
     * Source of the boost, always “gift_code”
     */
    @JsonProperty(SOURCE_FIELD)
    @Builder.Default
    private final String source = ChatBoostSource.GIFT_CODE_TYPE;
    /**
     * User for which the gift code was created
     */
    @JsonProperty(USER_FIELD)
    private User user;
}
