package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.objects.passport.dataerror.PassportElementError;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Informs a user that some Telegram Passport data contains errors.
 * The user will not be able to resend data, until the errors are fixed
 */
public class SetPassportDataErrors extends BotApiMethod<Boolean> {
    public static final String PATH = "setPassportDataErrors";

    private static final String USERID_FIELD = "user_id";
    private static final String ERRORS_FIELD = "errors";
    
    @JsonProperty(USERID_FIELD)
    private Integer userId; ///< User identifier
    @JsonProperty(ERRORS_FIELD)
    private List<PassportElementError> errors; ///< A JSON-serialized array describing the errors

    public SetPassportDataErrors(Integer userId, List<PassportElementError> errors) {
        super();
        this.userId = checkNotNull(userId);
        this.errors = checkNotNull(errors);
    }

    public SetPassportDataErrors() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public SetPassportDataErrors setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public List<PassportElementError> getErrors() {
        return errors;
    }

    public SetPassportDataErrors setErrors(List<PassportElementError> errors) {
        this.errors = errors;
        return this;
    }

    public SetPassportDataErrors addError(PassportElementError error) {
        error = checkNotNull(error);
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
        return this;
    }

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
