package org.telegram.telegrambots.meta.api.objects.description;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 6.6
 * This object represents the bot's short description.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class BotShortDescription implements BotApiObject  {
    private static final String SHORT_DESCRIPTION_FIELD = "short_description";

    /**
     * The bot's short description
     */
    @JsonProperty(SHORT_DESCRIPTION_FIELD)
    private String shortDescription;
}
