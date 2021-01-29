package org.telegram.telegrambots.meta.api.methods.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * Use this method to change the list of the bot's commands. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMyCommands extends BotApiMethod<Boolean> {
    public static final String PATH = "setMyCommands";

    private static final String COMMANDS_FIELD = "commands";

    /**
     * A JSON-serialized list of bot commands to be set as the list of the bot's commands.
     * At most 100 commands can be specified.
     */
    @JsonProperty(COMMANDS_FIELD)
    @Singular
    @NonNull
    private List<BotCommand> commands;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending commands", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (commands == null) {
            throw new TelegramApiValidationException("Commands parameter can't be empty", this);
        }
        if (commands.size() > 100) {
            throw new TelegramApiValidationException("No more than 100 commands are allowed", this);
        }
        for (BotCommand command : commands) {
            command.validate();
        }
    }
}
