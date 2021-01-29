package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.passport.dataerror.serialization.PassportElementErrorDeserializer;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 *
 * This object represents an error in sent Passport Data that should be resolved by the user. It should be one of
 *
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
@JsonDeserialize(using = PassportElementErrorDeserializer.class)
public interface PassportElementError extends Validable, BotApiObject {
}
