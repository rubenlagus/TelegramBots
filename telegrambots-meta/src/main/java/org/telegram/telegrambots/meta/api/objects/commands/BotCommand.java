package org.telegram.telegrambots.meta.api.objects.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * This object represents a bot command.
 */
public class BotCommand implements BotApiObject, Validable {
    private static final String COMMAND_FIELD = "command";
    private static final String DESCRIPTION_FIELD = "description";

    /**
     * Text of the command. Can contain only lowercase English letters, digits and underscores. 1-32 characters.
     */
    @JsonProperty(COMMAND_FIELD)
    private String command; ///< Value of the dice, 1-6
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Description of the command, 3-256 characters.

    public BotCommand() {
        super();
    }

    public BotCommand(String command, String description) {
        this.command = checkNotNull(command);
        this.description = checkNotNull(description);
    }

    public String getCommand() {
        return command;
    }

    public BotCommand setCommand(String command) {
        this.command = command;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BotCommand setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (command == null || command.isEmpty()) {
            throw new TelegramApiValidationException("Command parameter can't be empty", this);
        }
        if (description == null || description.isEmpty()) {
            throw new TelegramApiValidationException("Description parameter can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "BotCommand{" +
                "command='" + command + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
