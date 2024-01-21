package org.telegram.telegrambots.meta.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Reply keyboard abstract type
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForceReplyKeyboard.class),
        @JsonSubTypes.Type(value = ReplyKeyboardMarkup.class),
        @JsonSubTypes.Type(value = InlineKeyboardMarkup.class),
        @JsonSubTypes.Type(value = ReplyKeyboardRemove.class)
})
public interface ReplyKeyboard extends BotApiObject, Validable {
}
