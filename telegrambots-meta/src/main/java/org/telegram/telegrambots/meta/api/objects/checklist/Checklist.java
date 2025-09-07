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
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

/**
 * @author JetBrains
 * @version 7.0
 *
 * Describes a checklist.
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
public class Checklist implements BotApiObject {
    private static final String TITLE_FIELD = "title";
    private static final String TITLE_ENTITIES_FIELD = "title_entities";
    private static final String TASKS_FIELD = "tasks";
    private static final String OTHERS_CAN_ADD_TASKS_FIELD = "others_can_add_tasks";
    private static final String OTHERS_CAN_MARK_TASKS_AS_DONE_FIELD = "others_can_mark_tasks_as_done";

    /**
     * Title of the checklist
     */
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title;

    /**
     * Optional. Special entities that appear in the checklist title
     */
    @JsonProperty(TITLE_ENTITIES_FIELD)
    private List<MessageEntity> titleEntities;

    /**
     * List of tasks in the checklist
     */
    @JsonProperty(TASKS_FIELD)
    @NonNull
    private List<ChecklistTask> tasks;

    /**
     * Optional. True, if users other than the creator of the list can add tasks to the list
     */
    @JsonProperty(OTHERS_CAN_ADD_TASKS_FIELD)
    private Boolean othersCanAddTasks;

    /**
     * Optional. True, if users other than the creator of the list can mark tasks as done or not done
     */
    @JsonProperty(OTHERS_CAN_MARK_TASKS_AS_DONE_FIELD)
    private Boolean othersCanMarkTasksAsDone;
}
