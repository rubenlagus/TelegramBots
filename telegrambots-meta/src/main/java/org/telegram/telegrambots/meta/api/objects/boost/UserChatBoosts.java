package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a list of boosts added to a chat by a user.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserChatBoosts implements BotApiObject {
    private static final String BOOSTS_FIELD = "boosts";

    /**
     * The list of boosts added to the chat by the user
     */
    @JsonProperty(BOOSTS_FIELD)
    private List<ChatBoost> boosts;
}
