package org.telegram.telegrambots.meta.api.objects.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about an edited forum topic.
 * @author Ruben Bermudez
 * @version 6.4
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForumTopicEdited implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String ICONCUSTOMEMOJIID_FIELD = "icon_custom_emoji_id";


    /**
     * Optional.
     * New name of the topic, if it was edited
     */
    @JsonProperty(NAME_FIELD)
    private String name;
    /**
     * Optional.
     * New identifier of the custom emoji shown as the topic icon, if it was edited;
     * an empty string if the icon was removed
     */
    @JsonProperty(ICONCUSTOMEMOJIID_FIELD)
    private String iconCustomEmojiId;
}
