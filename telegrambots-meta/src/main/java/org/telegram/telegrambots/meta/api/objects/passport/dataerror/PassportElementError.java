package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * This object represents an error in sent Passport Data that should be resolved by the user. It should be one of
 * PassportElementErrorDataField
 * PassportElementErrorFrontSide
 * PassportElementErrorReverseSide
 * PassportElementErrorSelfie
 * PassportElementErrorFile
 * PassportElementErrorFiles
 * PassportElementErrorUnspecified
 * PassportElementErrorTranslationFile
 * PassportElementErrorTranslationFiles
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "source"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PassportElementErrorDataField.class, name = "data"),
        @JsonSubTypes.Type(value = PassportElementErrorFrontSide.class, name = "front_side"),
        @JsonSubTypes.Type(value = PassportElementErrorReverseSide.class, name = "reverse_side"),
        @JsonSubTypes.Type(value = PassportElementErrorSelfie.class, name = "selfie"),
        @JsonSubTypes.Type(value = PassportElementErrorFile.class, name = "file"),
        @JsonSubTypes.Type(value = PassportElementErrorFiles.class, name = "files"),
        @JsonSubTypes.Type(value = PassportElementErrorTranslationFile.class, name = "translation_file"),
        @JsonSubTypes.Type(value = PassportElementErrorTranslationFiles.class, name = "translation_files"),
        @JsonSubTypes.Type(value = PassportElementErrorUnspecified.class, name = "unspecified")
})
public interface PassportElementError extends Validable, BotApiObject {
}
