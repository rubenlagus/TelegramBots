package org.telegram.telegrambots.meta.api.objects.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * This object represents a bot command.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotCommand implements BotApiObject, Validable {
    private static final String COMMAND_FIELD = "command";
    private static final String DESCRIPTION_FIELD = "description";

    /**
     * Text of the command. Can contain only lowercase English letters, digits and underscores. 1-32 characters.
     */
    @JsonProperty(COMMAND_FIELD)
    @NonNull
    private String command; ///< Value of the dice, 1-6
    @JsonProperty(DESCRIPTION_FIELD)
    @NonNull
    private String description; ///< Description of the command, 3-256 characters.

    @Override
    public void validate() throws TelegramApiValidationException {
        if (command == null || command.isEmpty()) {
            throw new TelegramApiValidationException("Command parameter can't be empty", this);
        }
        if (description == null || description.isEmpty()) {
            throw new TelegramApiValidationException("Description parameter can't be empty", this);
        }
    }
}
