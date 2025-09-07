package org.telegram.telegrambots.meta.api.objects.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
 * Describes a service message about checklist tasks marked as done or not done.
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
public class ChecklistTasksDone implements BotApiObject {
    private static final String CHECKLIST_MESSAGE_FIELD = "checklist_message";
    private static final String MARKED_AS_DONE_TASK_IDS_FIELD = "marked_as_done_task_ids";
    private static final String MARKED_AS_NOT_DONE_TASK_IDS_FIELD = "marked_as_not_done_task_ids";

    /**
     * Optional. Message containing the checklist whose tasks were marked as done or not done.
     * Note that the Message object in this field will not contain the reply_to_message field
     * even if it itself is a reply.
     */
    @JsonProperty(CHECKLIST_MESSAGE_FIELD)
    private Message checklistMessage;

    /**
     * Optional. Identifiers of the tasks that were marked as done
     */
    @JsonProperty(MARKED_AS_DONE_TASK_IDS_FIELD)
    private List<Integer> markedAsDoneTaskIds;

    /**
     * Optional. Identifiers of the tasks that were marked as not done
     */
    @JsonProperty(MARKED_AS_NOT_DONE_TASK_IDS_FIELD)
    private List<Integer> markedAsNotDoneTaskIds;
}
