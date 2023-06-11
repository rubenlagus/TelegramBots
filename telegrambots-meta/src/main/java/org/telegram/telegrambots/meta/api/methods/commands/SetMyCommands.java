package org.telegram.telegrambots.meta.api.methods.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * Use this method to change the list of the bot's commands.
 * See https://core.telegram.org/bots#commands for more details about bot commands.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetMyCommands extends BotApiMethodBoolean {
    public static final String PATH = "setMyCommands";

    private static final String COMMANDS_FIELD = "commands";
    private static final String SCOPE_FIELD = "scope";
    private static final String LANGUAGECODE_FIELD = "language_code";

    /**
     * A JSON-serialized list of bot commands to be set as the list of the bot's commands.
     * At most 100 commands can be specified.
     */
    @JsonProperty(COMMANDS_FIELD)
    @Singular
    @NonNull
    private List<BotCommand> commands;
    /**
     * Optional
     * A JSON-serialized object, describing scope of users for which the commands are relevant.
     * Defaults to BotCommandScopeDefault.
     */
    @JsonProperty(SCOPE_FIELD)
    private BotCommandScope scope;
    /**
     * Optional
     * A two-letter ISO 639-1 language code.
     * If empty, commands will be applied to all users from the given scope, for whose language there are no dedicated commands
     */
    @JsonProperty(LANGUAGECODE_FIELD)
    private String languageCode;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (languageCode != null && languageCode.isEmpty()) {
            throw new TelegramApiValidationException("LanguageCode parameter can't be empty string", this);
        }
        if (commands.isEmpty()) {
            throw new TelegramApiValidationException("Commands parameter can't be empty", this);
        }
        if (commands.size() > 100) {
            throw new TelegramApiValidationException("No more than 100 commands are allowed", this);
        }
        for (BotCommand command : commands) {
            command.validate();
        }
        if (scope != null) {
            scope.validate();
        }
    }
}
