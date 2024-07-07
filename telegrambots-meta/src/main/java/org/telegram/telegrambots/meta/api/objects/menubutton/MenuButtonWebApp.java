package org.telegram.telegrambots.meta.api.objects.menubutton;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Represents menu button, which launches a web app.
 */
@SuppressWarnings({"unused"})

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuButtonWebApp extends MenuButton {
    private static final String TYPE = "web_app"; ///< Type of the button, must be web_app

    public static final String TEXT_FIELD = "text";
    public static final String WEBAPP_FIELD = "web_app";

    /**
     * Text of the button
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;
    /**
     * Description of the Web App that will be launched when the user presses the button.
     * The Web App will be able to send an arbitrary message on behalf of the user using the method answerWebAppQuery.
     * Alternatively, a t.me link to a Web App of the bot can be specified in the object instead of the Web App's URL,
     * in which case the Web App will be opened as if the user pressed the link.
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
