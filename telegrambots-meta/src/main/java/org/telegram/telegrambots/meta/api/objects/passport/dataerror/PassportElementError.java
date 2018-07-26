package org.telegram.telegrambots.meta.api.objects.passport.dataerror;

import org.telegram.telegrambots.meta.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

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
 */
public interface PassportElementError extends InputBotApiObject, Validable {
}
