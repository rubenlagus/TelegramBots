package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an error in a field of data provided by a user.
 * The error is considered resolved when the field's value changes.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class PassportElementErrorDataField implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String FIELDNAME_FIELD = "field_name";
    private static final String DATAHASH_FIELD = "data_hash";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    @NonNull
    private final String source = "data"; ///< Error source, must be data
    /**
     * Type of the Telegram Passport data with the error, one of “personal_details”, “passport”, “driver_license”,
     * “identity_card”, “internal_passport”, “address”
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;
    @JsonProperty(FIELDNAME_FIELD)
    @NonNull
    private String fieldName; ///< Name of the data field with the error
    @JsonProperty(DATAHASH_FIELD)
    @NonNull
    private String dataHash; ///< Base64-encoded data hash
    @JsonProperty(MESSAGE_FIELD)
    @NonNull
    private String message; ///< Error message

    @Override
    public void validate() throws TelegramApiValidationException {
        if (fieldName == null || fieldName.isEmpty()) {
            throw new TelegramApiValidationException("Field Name parameter can't be empty", this);
        }
        if (dataHash == null || dataHash.isEmpty()) {
            throw new TelegramApiValidationException("Data hash parameter can't be empty", this);
        }
        if (message == null || message.isEmpty()) {
            throw new TelegramApiValidationException("Message parameter can't be empty", this);
        }
        if (type == null || type.isEmpty()) {
            throw new TelegramApiValidationException("Type parameter can't be empty", this);
        }
    }
}
