package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * This object contains information about the quoted part of a message that is replied to by the given message.
 * @author Ruben Bermudez
 * @version 7.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TextQuote implements BotApiObject {

    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String POSITION_FIELD = "position";
    private static final String IS_MANUAL_FIELD = "is_manual";

    /**
     * Text of the quoted part of a message that is replied to by the given message
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional.
     * Special entities that appear in the quote. Currently, only bold, italic, underline, strikethrough, spoiler, and custom_emoji entities are kept in quotes.
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * 	Approximate quote position in the original message in UTF-16 code units as specified by the sender
     */
    @JsonProperty(POSITION_FIELD)
    private Integer position;
    /**
     * Optional.
     * True, if the quote was chosen manually by the message sender. Otherwise, the quote was added automatically by the server.
     */
    @JsonProperty(IS_MANUAL_FIELD)
    private Boolean isManual;
}
