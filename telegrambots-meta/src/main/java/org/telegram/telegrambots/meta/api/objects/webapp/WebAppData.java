package org.telegram.telegrambots.meta.api.objects.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Contains data sent from a web app to the bot.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebAppData implements BotApiObject {
    private static final String DATA_FIELD = "data";
    private static final String BUTTONTEXT_FIELD = "button_text";

    @JsonProperty(DATA_FIELD)
    @NonNull
    private String data; ///< The data
    @JsonProperty(BUTTONTEXT_FIELD)
    @NonNull
    private String buttonText; ///< Text of the web_app keyboard button, from which the web app was opened

}
