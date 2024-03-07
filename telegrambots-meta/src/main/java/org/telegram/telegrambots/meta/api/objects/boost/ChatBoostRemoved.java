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
 * This object represents a boost removed from a chat.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ChatBoostRemoved implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String BOOST_ID_FIELD = "boost_id";
    private static final String REMOVE_DATE_FIELD = "remove_date";
    private static final String SOURCE_FIELD = "source";

    /**
     * Chat which was boosted
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Unique identifier of the boost
     */
    @JsonProperty(BOOST_ID_FIELD)
    private String boostId;
    /**
     * Point in time (Unix timestamp) when the boost was removed
     */
    @JsonProperty(REMOVE_DATE_FIELD)
    private Integer removeDate;
    /**
     * Source of the removed boost
     */
    @JsonProperty(SOURCE_FIELD)
    private ChatBoostSource source;
}
