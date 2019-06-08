package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.1
 *
 * Represents an issue in an unspecified place.
 * The error is considered resolved when new data is added.
 */
@JsonDeserialize
public class PassportElementErrorUnspecified implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String ELEMENTHASH_FIELD = "element_hash";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "unspecified"; ///< Error source, must be unspecified
    /**
     * Type of element of the user's Telegram Passport which has the issue
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    @JsonProperty(ELEMENTHASH_FIELD)
    private String elementHash; ///< Base64-encoded element hash
    @JsonProperty(MESSAGE_FIELD)
    private String message; ///< Error message

    public PassportElementErrorUnspecified() {
        super();
    }

    public PassportElementErrorUnspecified(String type, String elementHash, String message) {
        super();
        this.type = checkNotNull(type);
        this.elementHash = checkNotNull(elementHash);
        this.message = checkNotNull(message);
    }

    public String getType() {
        return type;
    }

    public PassportElementErrorUnspecified setType(String type) {
        this.type = checkNotNull(type);
        return this;
    }

    public String getSource() {
        return source;
    }

    public String getElementHash() {
        return elementHash;
    }

    public PassportElementErrorUnspecified setElementHash(String elementHash) {
        this.elementHash = elementHash;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PassportElementErrorUnspecified setMessage(String message) {
        this.message = checkNotNull(message);
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (elementHash == null || elementHash.isEmpty()) {
            throw new TelegramApiValidationException("Element hash parameter can't be empty", this);
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
                ", elementHash='" + elementHash + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
