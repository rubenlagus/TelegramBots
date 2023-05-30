package org.telegram.telegrambots.meta.api.objects.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SentWebAppMessage implements BotApiObject {
    private static final String INLINEMESSAGEID_FIELD = "inline_message_id";

    @JsonProperty(INLINEMESSAGEID_FIELD)
    @NonNull
    private String inlineMessageId;

}
