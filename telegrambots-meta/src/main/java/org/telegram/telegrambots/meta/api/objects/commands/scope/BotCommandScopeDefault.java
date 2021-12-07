package org.telegram.telegrambots.meta.api.objects.commands.scope;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * Represents the default scope of bot commands.
 * Default commands are used if no commands with a narrower scope are specified for the user.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class BotCommandScopeDefault implements BotCommandScope {
    private static final String TYPE_FIELD = "type";

    /**
     * Scope type, must be chat
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "default";

    @Override
    public void validate() throws TelegramApiValidationException {

    }
}
