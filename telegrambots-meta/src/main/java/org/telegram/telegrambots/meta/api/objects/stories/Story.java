package org.telegram.telegrambots.meta.api.objects.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Chat;

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
    private static final String CHAT_FIELD = "chat";
    private static final String ID_FIELD = "id";

    /**
     * Chat that posted the story
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Unique identifier for the story in the chat
     */
    @JsonProperty(ID_FIELD)
    private Integer id;
}
