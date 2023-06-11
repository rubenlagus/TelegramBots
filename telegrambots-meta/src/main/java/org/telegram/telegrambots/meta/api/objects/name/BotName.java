package org.telegram.telegrambots.meta.api.objects.name;

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
 * @version 6.7
 * This object represents the bot's name.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class BotName implements BotApiObject  {
    private static final String NAME_FIELD = "name";

    /**
     * The bot's name
     */
    @JsonProperty(NAME_FIELD)
    private String name;
}
