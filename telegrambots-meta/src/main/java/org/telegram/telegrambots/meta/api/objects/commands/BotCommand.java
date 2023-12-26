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
 * This object represents a bot command.
 * @author Ruben Bermudez
 * @version 4.7
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
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
    private String command;
    /**
     * Description of the command, 3-256 characters.
     */
    @JsonProperty(DESCRIPTION_FIELD)
    @NonNull
    private String description;

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
