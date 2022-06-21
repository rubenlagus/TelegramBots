package org.telegram.telegrambots.meta.api.methods.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * Use this method to get the current list of the bot's commands for the given scope and user language.
 * Returns Array of BotCommand on success.
 * If commands aren't set, an empty list is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyCommands extends BotApiMethod<ArrayList<BotCommand>> {
    public static final String PATH = "getMyCommands";

    private static final String SCOPE_FIELD = "scope";
    private static final String LANGUAGECODE_FIELD = "language_code";

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
    public ArrayList<BotCommand> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, BotCommand.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (scope != null) {
            scope.validate();
        }
        if (languageCode != null && languageCode.isEmpty()) {
            throw new TelegramApiValidationException("LanguageCode parameter can't be empty string", this);
        }
    }
}
