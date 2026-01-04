package org.telegram.telegrambots.meta.api.objects.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

/**
 * @author JetBrains
 * @version 7.0
 *
 * Describes a service message about tasks added to a checklist.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChecklistTasksAdded implements BotApiObject {
    private static final String CHECKLIST_MESSAGE_FIELD = "checklist_message";
    private static final String TASKS_FIELD = "tasks";

    /**
     * Optional. Message containing the checklist to which the tasks were added.
     * Note that the Message object in this field will not contain the reply_to_message field
     * even if it itself is a reply.
     */
    @JsonProperty(CHECKLIST_MESSAGE_FIELD)
    private Message checklistMessage;

    /**
     * List of tasks added to the checklist
     */
    @JsonProperty(TASKS_FIELD)
    @NonNull
    private List<ChecklistTask> tasks;
}
