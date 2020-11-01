package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.7
 *
 * This object represents type of a poll, which is allowed to be created and sent when the corresponding button is pressed.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyboardButtonPollType implements BotApiObject, Validable {
    private static final String TYPE_FIELD = "type";

    /**
     * Optional.
     *
     * If quiz is passed, the user will be allowed to create only polls in the quiz mode.
     * If regular is passed, only regular polls will be allowed.
     * Otherwise, the user will be allowed to create a poll of any type.
     */
    @JsonProperty(TYPE_FIELD)
    private String type;

    @Override
    public void validate() throws TelegramApiValidationException {
    }
}
