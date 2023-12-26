package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.regex.Pattern;

/**
 * @author Ruben Bermudez
 * @version 6.7
 * This object represents a button to be shown above inline query results.
 *
 * You must use exactly one of the optional fields.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class InlineQueryResultsButton implements Validable, BotApiObject  {

    private static final String TEXT_FIELD = "text";
    private static final String WEB_APP_FIELD = "web_app";
    private static final String START_PARAMETER_FIELD = "start_parameter";

    /**
     * Label text on the button
     */
    @NonNull
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional.
     * Description of the Web App that will be launched when the user presses the button.
     * The Web App will be able to switch back to the inline mode using the method web_app_switch_inline_query inside the Web App.
     */
    @JsonProperty(WEB_APP_FIELD)
    private WebAppInfo webApp;

    /**
     * Optional.
     * Deep-linking parameter for the /start message sent to the bot when a user presses the button. 1-64 characters,
     * only A-Z, a-z, 0-9, _ and - are allowed.
     */
    @JsonProperty(START_PARAMETER_FIELD)
    private String startParameter;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text.isEmpty()) {
            throw new TelegramApiValidationException("Text can't be empty", this);

        }

        if (startParameter != null && webApp != null) {
            throw new TelegramApiValidationException("Only one of Start Parameter or Web App is allowed", this);
        }

        if (startParameter != null) {
            if (startParameter.isEmpty() || startParameter.length() > 64) {
                throw new TelegramApiValidationException("SwitchPmParameter can't be longer than 64 chars", this);
            }
            if (!Pattern.matches("[A-Za-z0-9_\\-]+", startParameter.trim())) {
                throw new TelegramApiValidationException("SwitchPmParameter only allows A-Z, a-z, 0-9, _ and - characters", this);
            }
        }

        if (webApp != null) {
            webApp.validate();
        }
    }
}
