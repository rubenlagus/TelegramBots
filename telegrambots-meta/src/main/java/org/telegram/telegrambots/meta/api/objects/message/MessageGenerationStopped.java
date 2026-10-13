package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * This object describes an update about a user stopping message generation.
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
public class MessageGenerationStopped implements BotApiObject {
    private static final String CHAT_FIELD = "chat";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String DRAFT_ID_FIELD = "draft_id";

    /**
     * Chat in which the message is generated
     */
    @JsonProperty(CHAT_FIELD)
    @NonNull
    private Chat chat;

    /**
     * Optional. Unique identifier of the message thread in which the message is generated
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;

    /**
     * Unique identifier of the message draft which was stopped
     */
    @JsonProperty(DRAFT_ID_FIELD)
    @NonNull
    private Integer draftId;
}
