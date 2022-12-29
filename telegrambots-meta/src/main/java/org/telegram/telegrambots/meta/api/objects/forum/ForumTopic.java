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
 * This object represents a forum topic.
 * @author Ruben Bermudez
 * @version 6.3
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForumTopic implements BotApiObject {
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String NAME_FIELD = "name";
    private static final String ICONCOLOR_FIELD = "icon_color";
    private static final String ICONCUSTOMEMOJIID_FIELD = "icon_custom_emoji_id";

    /**
     * Unique identifier of the forum topic
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;
    /**
     * Name of the topic
     */
    @JsonProperty(NAME_FIELD)
    private String name;
    /**
     * Color of the topic icon in RGB format
     */
    @JsonProperty(ICONCOLOR_FIELD)
    private Integer iconColor;
    /**
     * Optional.
     * Unique identifier of the custom emoji shown as the topic icon
     */
    @JsonProperty(ICONCUSTOMEMOJIID_FIELD)
    private String iconCustomEmojiId;
}
