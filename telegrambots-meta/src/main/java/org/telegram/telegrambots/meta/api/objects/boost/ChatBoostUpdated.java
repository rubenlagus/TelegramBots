package org.telegram.telegrambots.meta.api.objects.boost;

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
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a boost added to a chat or changed.
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
public class ChatBoostUpdated implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String BOOST_FIELD = "boost";

    /**
     * Chat which was boosted
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Information about the chat boost
     */
    @JsonProperty(BOOST_FIELD)
    private ChatBoost boost;
}
