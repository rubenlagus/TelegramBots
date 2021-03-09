package org.telegram.telegrambots.meta.api.methods;

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
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.passport.dataerror.PassportElementError;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Informs a user that some Telegram Passport data contains errors.
 * The user will not be able to resend data, until the errors are fixed
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetPassportDataErrors extends BotApiMethod<Boolean> {
    public static final String PATH = "setPassportDataErrors";

    private static final String USERID_FIELD = "user_id";
    private static final String ERRORS_FIELD = "errors";
    
    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId; ///< User identifier
    @JsonProperty(ERRORS_FIELD)
    @NonNull
    @Singular
    private List<PassportElementError> errors; ///< A JSON-serialized array describing the errors

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
                throw new TelegramApiRequestException("Error setting passport data errors", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null) {
            throw new TelegramApiValidationException("User ID can't be empty", this);
        }
        if (errors == null || errors.isEmpty()) {
            throw new TelegramApiValidationException("Errors can't be empty", this);
        }
    }
}
