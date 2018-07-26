package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an error in a list of attached files.
 * The error is considered resolved when the file list changes.
 */
public class PassportElementErrorFiles implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String FILEHASHES_FIELD = "file_hashes";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "file"; ///< Error source, must be file
    /**
     * Type of the Telegram Passport data with the error, one of “utility_bill”,
     * “bank_statement”, “rental_agreement”, “passport_registration”, “temporary_registration”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(FILEHASHES_FIELD)
    private List<String> fileHashes; ///< List of base64-encoded file hashes
    @JsonProperty(MESSAGE_FIELD)
    private String message; ///< Error message

    public PassportElementErrorFiles() {
        super();
    }

    public PassportElementErrorFiles(String type, List<String> fileHashes, String message) {
        super();
        this.type = checkNotNull(type);
        this.fileHashes = checkNotNull(fileHashes);
        this.message = checkNotNull(message);
    }

    public String getType() {
        return type;
    }

    public PassportElementErrorFiles setType(String type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getSource() {
        return source;
    }

    public List<String> getFileHashes() {
        return fileHashes;
    }

    public PassportElementErrorFiles setFileHashes(List<String> fileHashes) {
        this.fileHashes = checkNotNull(fileHashes);
        return this;
    }

    public PassportElementErrorFiles addFileHash(String fileHash) {
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

    public PassportElementErrorFiles setMessage(String message) {
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
        return "PassportElementErrorFile{" +
                "source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", fileHashes='" + fileHashes + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
