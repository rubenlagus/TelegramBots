package org.telegram.telegrambots.meta.api.objects.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author JetBrains
 * @version 7.0
 *
 * Describes a task to add to a checklist.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputChecklistTask implements BotApiObject, Validable {
    private static final String ID_FIELD = "id";
    private static final String TEXT_FIELD = "text";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";

    /**
     * Unique identifier of the task; must be positive and unique among all task identifiers currently present in the checklist
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Integer id;

    /**
     * Text of the task; 1-100 characters after entities parsing
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;

    /**
     * Optional. Mode for parsing entities in the text. See formatting options for more details.
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;

    /**
     * Optional. List of special entities that appear in the text, which can be specified instead of parse_mode.
     * Currently, only bold, italic, underline, strikethrough, spoiler, and custom_emoji entities are allowed.
     */
    @JsonProperty(TEXT_ENTITIES_FIELD)
    private List<MessageEntity> textEntities;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id <= 0) {
            throw new TelegramApiValidationException("Task id must be positive", this);
        }
        if (text == null || text.isEmpty()) {
            throw new TelegramApiValidationException("Task text can't be empty", this);
        }
        if (text.length() > 100) {
            throw new TelegramApiValidationException("Task text can't be longer than 100 characters", this);
        }
    }
}
