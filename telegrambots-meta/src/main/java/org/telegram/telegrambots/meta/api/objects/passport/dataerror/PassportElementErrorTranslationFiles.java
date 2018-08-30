package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.1
 *
 * Represents an issue with the translated version of a document.
 * The error is considered resolved when a file with the document translation change.
 */
public class PassportElementErrorTranslationFiles implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String FILEHASHES_FIELD = "file_hashes";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "translation_files"; ///< Error source, must be translation_files
    /**
     * Type of element of the user's Telegram Passport which has the issue, one of “passport”, “driver_license”,
     * “identity_card”, “internal_passport”, “utility_bill”, “bank_statement”, “rental_agreement”,
     * “passport_registration”, “temporary_registration”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(FILEHASHES_FIELD)
    private List<String> fileHashes; ///< List of base64-encoded file hashes
    @JsonProperty(MESSAGE_FIELD)
    private String message; ///< Error message

    public PassportElementErrorTranslationFiles() {
        super();
    }

    public PassportElementErrorTranslationFiles(String type, List<String> fileHashes, String message) {
        super();
        this.type = checkNotNull(type);
        this.fileHashes = checkNotNull(fileHashes);
        this.message = checkNotNull(message);
    }

    public String getType() {
        return type;
    }

    public PassportElementErrorTranslationFiles setType(String type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getSource() {
        return source;
    }

    public List<String> getFileHashes() {
        return fileHashes;
    }

    public PassportElementErrorTranslationFiles setFileHashes(List<String> fileHashes) {
        this.fileHashes = checkNotNull(fileHashes);
        return this;
    }

    public PassportElementErrorTranslationFiles addFileHash(String fileHash) {
        fileHash = checkNotNull(fileHash);
        if (fileHashes == null) {
            fileHashes = new ArrayList<>();
        }
        fileHashes.add(fileHash);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PassportElementErrorTranslationFiles setMessage(String message) {
        this.message = checkNotNull(message);
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (fileHashes == null || fileHashes.isEmpty()) {
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
        return "PassportElementErrorTranslationFiles{" +
                "source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", fileHashes=" + fileHashes +
                ", message='" + message + '\'' +
                '}';
    }
}
