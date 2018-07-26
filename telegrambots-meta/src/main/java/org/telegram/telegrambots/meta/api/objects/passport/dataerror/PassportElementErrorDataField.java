package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an error in a field of data provided by a user.
 * The error is considered resolved when the field's value changes.
 */
public class PassportElementErrorDataField implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String FIELDNAME_FIELD = "field_name";
    private static final String DATAHASH_FIELD = "data_hash";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "data"; ///< Error source, must be data
    /**
     * Type of the Telegram Passport data with the error, one of “personal_details”, “passport”, “driver_license”,
     * “identity_card”, “internal_passport”, “address”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(FIELDNAME_FIELD)
    private String fieldName; ///< Name of the data field with the error
    @JsonProperty(DATAHASH_FIELD)
    private String dataHash; ///< Base64-encoded data hash
    @JsonProperty(MESSAGE_FIELD)
    private String message; ///< Error message

    public PassportElementErrorDataField() {
        super();
    }

    public PassportElementErrorDataField(String type, String fieldName, String dataHash, String message) {
        super();
        this.type = checkNotNull(type);
        this.fieldName = checkNotNull(fieldName);
        this.dataHash = checkNotNull(dataHash);
        this.message = checkNotNull(message);
    }

    public String getType() {
        return type;
    }

    public PassportElementErrorDataField setType(String type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getSource() {
        return source;
    }

    public String getFieldName() {
        return fieldName;
    }

    public PassportElementErrorDataField setFieldName(String fieldName) {
        this.fieldName = checkNotNull(fieldName);
        return this;
    }

    public String getDataHash() {
        return dataHash;
    }

    public PassportElementErrorDataField setDataHash(String dataHash) {
        this.dataHash = checkNotNull(dataHash);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PassportElementErrorDataField setMessage(String message) {
        this.message = checkNotNull(message);
        return this;
    }

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

    @Override
    public String toString() {
        return "PassportElementErrorDataField{" +
                "source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", dataHash='" + dataHash + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
