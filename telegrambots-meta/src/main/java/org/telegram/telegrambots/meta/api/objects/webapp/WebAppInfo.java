package org.telegram.telegrambots.meta.api.objects.webapp;

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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebAppInfo implements Validable, BotApiObject {
    private static final String URL_FIELD = "url";

    @JsonProperty(URL_FIELD)
    @NonNull
    private String url; ///< An HTTPS URL of a web app to be opened with additional data as specified at ...

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url.isEmpty()) {
            throw new TelegramApiValidationException("Url can't be empty", this);
        }
    }
}
