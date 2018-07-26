package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * Represents an error in a file with a document‘s reverse side.
 * The error is considered resolved when the file with the document’s reverse side changes.
 */
public class PassportElementErrorReverseSide implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String FILEHASH_FIELD = "file_hash";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "reverse_side"; ///< Error source, must be reverse_side
    /**
     * Type of the Telegram Passport data with the error, one of “driver_license”, “identity_card”
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(FILEHASH_FIELD)
    private String fileHash; ///< Base64-encoded hash of file with document's reverse side
    @JsonProperty(MESSAGE_FIELD)
    private String message; ///< Error message

    public PassportElementErrorReverseSide() {
        super();
    }

    public PassportElementErrorReverseSide(String type, String fileHash, String message) {
        super();
        this.type = checkNotNull(type);
        this.fileHash = checkNotNull(fileHash);
        this.message = checkNotNull(message);
    }

    public String getType() {
        return type;
    }

    public PassportElementErrorReverseSide setType(String type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getSource() {
        return source;
    }

    public String getFileHash() {
        return fileHash;
    }

    public PassportElementErrorReverseSide setFileHash(String fileHash) {
        this.fileHash = fileHash;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PassportElementErrorReverseSide setMessage(String message) {
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
        return "PassportElementErrorReverseSide{" +
                "source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", fileHash='" + fileHash + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
