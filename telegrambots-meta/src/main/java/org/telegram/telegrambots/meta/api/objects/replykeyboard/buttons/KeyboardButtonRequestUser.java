package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.5
 *
 * This object defines the criteria used to request a suitable user.
 * The identifier of the selected user will be shared with the bot when the corresponding button is pressed.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class KeyboardButtonRequestUser implements Validable, BotApiObject {

    private static final String REQUESTID_FIELD = "request_id";
    private static final String USERISBOT_FIELD = "user_is_bot";
    private static final String USERISPREMIUM_FIELD = "user_is_premium";

    /**
     * Signed 32-bit identifier of the request
     */
    @JsonProperty(REQUESTID_FIELD)
    @NonNull
    private String requestId;
    /**
     * Optional.
     * Pass True to request a bot, pass False to request a regular user.
     * If not specified, no additional restrictions are applied.
     */
    @JsonProperty(USERISBOT_FIELD)
    private Boolean userIsBot;
    /**
     * Optional.
     * Pass True to request a premium user, pass False to request a non-premium user.
     * If not specified, no additional restrictions are applied.
     */
    @JsonProperty(USERISPREMIUM_FIELD)
    private Boolean userIsPremium;


    @Override
    public void validate() throws TelegramApiValidationException {
        if (requestId.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
    }
}
