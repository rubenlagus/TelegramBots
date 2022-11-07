package org.telegram.telegrambots.meta.api.objects.videochat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a service message about a video chat ended in the chat.
 * @author Ruben Bermudez
 * @version 6.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoChatEnded implements BotApiObject {
    private static final String DURATION_FIELD = "duration";

    /**
     * Voice chat duration; in seconds
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
}
