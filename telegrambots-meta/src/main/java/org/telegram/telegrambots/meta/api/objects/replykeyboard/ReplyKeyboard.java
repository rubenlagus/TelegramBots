package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * Reply keyboard abstract type
 *
 * @implNote Subtypes are resolved by {@link ReplyKeyboardDeserializer} rather than by
 * {@code JsonTypeInfo.Id.DEDUCTION}: since Bot API 10.3 both {@link ForceReplyKeyboard} and
 * {@link ReplyKeyboardMarkup} carry {@code force_reply}, which makes deduction ambiguous.
 */
@JsonDeserialize(using = ReplyKeyboardDeserializer.class)
public interface ReplyKeyboard extends BotApiObject, Validable {
}
