package org.telegram.telegrambots.meta.api.objects.videochat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * This object represents a service message about a video chat ended in the chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoChatEnded implements BotApiObject {
    private static final String DURATION_FIELD = "duration";

    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Voice chat duration; in seconds
}
