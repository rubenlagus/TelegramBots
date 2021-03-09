package org.telegram.telegrambots.meta.api.objects.voicechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 5.1
 *
 * This object represents a service message about a voice chat ended in the chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChatEnded implements BotApiObject {
    private static final String DURATION_FIELD = "duration";

    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Voice chat duration; in seconds
}
