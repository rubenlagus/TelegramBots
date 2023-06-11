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
 * Represents an error in a file with a document‘s reverse side.
 * The error is considered resolved when the file with the document’s reverse side changes.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
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
    @NonNull
    private String type;
    @JsonProperty(FILEHASH_FIELD)
    @NonNull
    private String fileHash; ///< Base64-encoded hash of file with document's reverse side
    @JsonProperty(MESSAGE_FIELD)
    @NonNull
    private String message; ///< Error message

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
}
