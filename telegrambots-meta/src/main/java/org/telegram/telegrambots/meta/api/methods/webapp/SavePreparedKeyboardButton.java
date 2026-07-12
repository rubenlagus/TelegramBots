package org.telegram.telegrambots.meta.api.methods.webapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.PreparedKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 9.6
 *
 * Stores a keyboard button that can be used by a user within a Mini App.
 * Returns a PreparedKeyboardButton object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavePreparedKeyboardButton extends BotApiMethod<PreparedKeyboardButton> {
    public static final String PATH = "savePreparedKeyboardButton";

    private static final String USER_ID_FIELD = "user_id";
    private static final String BUTTON_FIELD = "button";

    /**
     * Unique identifier of the target user that can use the button
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * A JSON-serialized object describing the button to be saved.
     * The button must be of the type request_users, request_chat, or request_managed_bot
     */
    @JsonProperty(BUTTON_FIELD)
    @NonNull
    private KeyboardButton button;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public PreparedKeyboardButton deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, PreparedKeyboardButton.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
        if (button != null) {
            button.validate();
        }
    }

    public static abstract class SavePreparedKeyboardButtonBuilder<C extends SavePreparedKeyboardButton, B extends SavePreparedKeyboardButtonBuilder<C, B>> extends BotApiMethodBuilder<PreparedKeyboardButton, C, B> {

    }
}
