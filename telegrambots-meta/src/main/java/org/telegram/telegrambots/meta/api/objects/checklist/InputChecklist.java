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
 * Describes a checklist to create.
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
public class InputChecklist implements BotApiObject, Validable {
    private static final String TITLE_FIELD = "title";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String TITLE_ENTITIES_FIELD = "title_entities";
    private static final String TASKS_FIELD = "tasks";
    private static final String OTHERS_CAN_ADD_TASKS_FIELD = "others_can_add_tasks";
    private static final String OTHERS_CAN_MARK_TASKS_AS_DONE_FIELD = "others_can_mark_tasks_as_done";

    /**
     * Title of the checklist; 1-255 characters after entities parsing
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;

    /**
     * Optional. Mode for parsing entities in the title. See formatting options for more details.
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;

    /**
     * Optional. List of special entities that appear in the title, which can be specified instead of parse_mode.
     * Currently, only bold, italic, underline, strikethrough, spoiler, and custom_emoji entities are allowed.
     */
    @JsonProperty(TITLE_ENTITIES_FIELD)
    private List<MessageEntity> titleEntities;

    /**
     * List of 1-30 tasks in the checklist
     */
    @JsonProperty(TASKS_FIELD)
    @NonNull
    private List<InputChecklistTask> tasks;

    /**
     * Optional. Pass True if other users can add tasks to the checklist
     */
    @JsonProperty(OTHERS_CAN_ADD_TASKS_FIELD)
    private Boolean othersCanAddTasks;

    /**
     * Optional. Pass True if other users can mark tasks as done or not done in the checklist
     */
    @JsonProperty(OTHERS_CAN_MARK_TASKS_AS_DONE_FIELD)
    private Boolean othersCanMarkTasksAsDone;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Checklist title can't be empty", this);
        }
        if (title.length() > 255) {
            throw new TelegramApiValidationException("Checklist title can't be longer than 255 characters", this);
        }
        if (tasks == null || tasks.isEmpty()) {
            throw new TelegramApiValidationException("Checklist tasks can't be empty", this);
        }
        if (tasks.size() > 30) {
            throw new TelegramApiValidationException("Checklist can't have more than 30 tasks", this);
        }

        for (InputChecklistTask task : tasks) {
            task.validate();
        }
    }
}
