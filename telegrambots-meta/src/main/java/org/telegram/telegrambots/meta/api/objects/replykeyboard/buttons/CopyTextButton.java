package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.11
 *
 * This object represents an inline keyboard button that copies specified text to the clipboard.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CopyTextButton implements Validable, BotApiObject {
    private static final String TEXT_FIELD = "text";

    /**
     * The text to be copied to the clipboard; 1-256 characters
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (text.isEmpty()) {
            throw new TelegramApiValidationException("Text parameter can't be empty", this);
        }
    }
}
