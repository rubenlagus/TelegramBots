package org.telegram.telegrambots.meta.api.objects.forum;

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

/**
 * This object represents a service message about a new forum topic created in the chat.
 * @author Ruben Bermudez
 * @version 6.1
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumTopicCreated implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String ICONCOLOR_FIELD = "icon_color";
    private static final String ICONCUSTOMEMOJIID_FIELD = "icon_custom_emoji_id";
    private static final String IS_NAME_IMPLICIT_FIELD = "is_name_implicit";


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
    /**
     * Optional.
     * True, if the name of the topic wasn't specified explicitly by its creator and likely needs to be changed by the bot
     */
    @JsonProperty(IS_NAME_IMPLICIT_FIELD)
    private Boolean isNameImplicit;

}
