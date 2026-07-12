package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Represents the content of a rich message to be sent as the result of an inline query.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputRichMessageContent implements InputMessageContent {
    private static final String RICH_MESSAGE_FIELD = "rich_message";

    /**
     * The message to be sent
     */
    @JsonProperty(RICH_MESSAGE_FIELD)
    @NonNull
    private InputRichMessage richMessage;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (richMessage == null) {
            throw new TelegramApiValidationException("RichMessage parameter can't be null", this);
        }
    }
}
