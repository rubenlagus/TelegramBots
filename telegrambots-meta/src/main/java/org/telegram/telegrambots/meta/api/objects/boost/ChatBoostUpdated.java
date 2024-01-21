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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object represents a boost added to a chat or changed.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
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
