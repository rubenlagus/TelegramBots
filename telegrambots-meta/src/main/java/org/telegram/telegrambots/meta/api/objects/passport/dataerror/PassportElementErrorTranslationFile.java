package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.1
 *
 * Represents an issue with one of the files that constitute the translation of a document.
 * The error is considered resolved when the file changes.
 */
@JsonDeserialize
public class PassportElementErrorTranslationFile implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String FILEHASH_FIELD = "file_hash";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "translation_file"; ///< Error source, must be translation_file
    /**
     * Type of element of the user's Telegram Passport which has the issue, one of “passport”, “driver_license”,
     * “identity_card”, “internal_passport”, “utility_bill”, “bank_statement”, “rental_agreement”,
     * “passport_registration”, “temporary_registration”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(FILEHASH_FIELD)
    private String fileHash; ///< Base64-encoded file hash
    @JsonProperty(MESSAGE_FIELD)
    private String message; ///< Error message

    public PassportElementErrorTranslationFile() {
        super();
    }

    public PassportElementErrorTranslationFile(String type, String fileHash, String message) {
        super();
        this.type = checkNotNull(type);
        this.fileHash = checkNotNull(fileHash);
        this.message = checkNotNull(message);
    }

    public String getType() {
        return type;
    }

    public PassportElementErrorTranslationFile setType(String type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getSource() {
        return source;
    }

    public String getFileHash() {
        return fileHash;
    }

    public PassportElementErrorTranslationFile setFileHash(String fileHash) {
        this.fileHash = fileHash;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PassportElementErrorTranslationFile setMessage(String message) {
        this.message = checkNotNull(message);
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (fileHash == null || fileHash.isEmpty()) {
            throw new TelegramApiValidationException("File hash parameter can't be empty", this);
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
        return "PassportElementErrorTranslationFile{" +
                "source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", fileHash='" + fileHash + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
