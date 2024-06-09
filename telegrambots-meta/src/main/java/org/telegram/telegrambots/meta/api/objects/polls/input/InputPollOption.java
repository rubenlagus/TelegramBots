package org.telegram.telegrambots.meta.api.objects.polls.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * This object contains information about one answer option in a poll to send.
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
public class InputPollOption implements BotApiObject, Validable {
    private static final String TEXT_FIELD = "text";
    private static final String TEXT_PARSE_MODE_FIELD = "text_parse_mode";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";

    /**
     * Option text, 1-100 characters
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;
    /**
     * Optional.
     * Mode for parsing entities in the text.
     * See formatting options for more details.
     * Currently, only custom emoji entities are allowed
     */
    @JsonProperty(TEXT_PARSE_MODE_FIELD)
    private String textParseMode;
    /**
     * Optional.
     * A JSON-serialized list of special entities that appear in the poll option text.
     * It can be specified instead of text_parse_mode
     */
    @JsonProperty(TEXT_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> textEntities;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text.isEmpty() || text.length() > 100) {
            throw new TelegramApiValidationException("Text must be between 1 and 100 characters", this);
        }
        if (textParseMode != null && textEntities != null) {
            throw new TelegramApiValidationException("Parse Mode and Entities can´t be used together", this);
        }
    }
}
