package org.telegram.telegrambots.meta.api.objects.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;


/**
 * @author Ruben Bermudez
 * @version 7.1
 *
 * This object represents a story.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Story implements BotApiObject {
    /*
    * Refactored code - Using CHAT_ID_FIELD instead of CHAT_FIELD to change bidirectional association to unidirectional association
     */

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String ID_FIELD = "id";

    /**
     * Chat that posted the story
     */
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;
    /**
     * Unique identifier for the story in the chat
     */
    @JsonProperty(ID_FIELD)
    private Integer id;
}
