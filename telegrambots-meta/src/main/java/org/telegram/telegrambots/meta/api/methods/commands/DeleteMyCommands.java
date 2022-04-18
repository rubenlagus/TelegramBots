package org.telegram.telegrambots.meta.api.methods.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * Use this method to delete the list of the bot's commands for the given scope and user language.
 * After deletion, higher level commands will be shown to affected users.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteMyCommands extends BotApiMethod<Boolean> {
    public static final String PATH = "deleteMyCommands";

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
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error deleting commands", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
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
