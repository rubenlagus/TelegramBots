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
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import java.util.List;

/**
 * @author JetBrains
 * @version 7.0
 *
 * Describes a task in a checklist.
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
public class ChecklistTask implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String TEXT_FIELD = "text";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";
    private static final String COMPLETED_BY_USER_FIELD = "completed_by_user";
    private static final String COMPLETED_BY_CHAT_FIELD = "completed_by_chat";
    private static final String COMPLETION_DATE_FIELD = "completion_date";

    /**
     * Unique identifier of the task
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Integer id;

    /**
     * Text of the task
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;

    /**
     * Optional. Special entities that appear in the task text
     */
    @JsonProperty(TEXT_ENTITIES_FIELD)
    private List<MessageEntity> textEntities;

    /**
     * Optional. User that completed the task; omitted if the task wasn't completed by a user
     */
    @JsonProperty(COMPLETED_BY_USER_FIELD)
    private User completedByUser;

    /**
     * Optional. Chat that completed the task; omitted if the task wasn't completed by a chat
     */
    @JsonProperty(COMPLETED_BY_CHAT_FIELD)
    private Chat completedByChat;

    /**
     * Optional. Point in time (Unix timestamp) when the task was completed; 0 if the task wasn't completed
     */
    @JsonProperty(COMPLETION_DATE_FIELD)
    private Integer completionDate;
}
