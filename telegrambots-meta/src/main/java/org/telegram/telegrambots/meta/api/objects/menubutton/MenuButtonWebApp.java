package org.telegram.telegrambots.meta.api.objects.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Represents menu button, which launches a web app.
 */
@SuppressWarnings({"unused"})
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Builder
public class MenuButtonWebApp extends MenuButton {
    private static final String TYPE = "web_app"; ///< Type of the button, must be web_app

    public static final String TEXT_FIELD = "text";
    public static final String WEBAPP_FIELD = "web_app";

    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text; ///< Text of the button
    /**
     * Description of the web app that will be launched when the user presses the button.
     * The web app will be able to send an arbitrary message on behalf of the user using the method answerWebAppQuery.
     */
    @JsonProperty(WEBAPP_FIELD)
    @NonNull
    private WebAppInfo webAppInfo;

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();

        if (text.isEmpty()) {
            throw new TelegramApiValidationException("Text can't be empty", this);
        }
        webAppInfo.validate();
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
