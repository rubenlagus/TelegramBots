package org.telegram.telegrambots.meta.api.objects.videochat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about a video chat scheduled in the chat.
 * @author Ruben Bermudez
 * @version 6.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class VideoChatScheduled implements BotApiObject {
    private static final String START_DATE_FIELD = "start_date";

    /**
     * Point in time (Unix timestamp) when the voice chat is supposed to be started by a chat administrator
     */
    @JsonProperty(START_DATE_FIELD)
    @NonNull
    private Integer startDate;
}
