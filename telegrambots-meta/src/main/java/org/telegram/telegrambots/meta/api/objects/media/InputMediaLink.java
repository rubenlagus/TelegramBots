package org.telegram.telegrambots.meta.api.objects.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import org.telegram.telegrambots.meta.api.objects.polls.input.InputPollOptionMedia;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Represents an HTTP link to be sent.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputMediaLink implements Validable, BotApiObject, InputPollOptionMedia {
    public static final String TYPE = "link";
    private static final String TYPE_FIELD = "type";
    private static final String URL_FIELD = "url";

    /**
     * Type of the result, must be link
     */
    @Builder.Default
    @JsonProperty(TYPE_FIELD)
    private String type = TYPE;

    /**
     * HTTP URL of the link
     */
    @JsonProperty(URL_FIELD)
    @NonNull
    private String url;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url == null || url.isEmpty()) {
            throw new TelegramApiValidationException("Url parameter can't be empty", this);
        }
    }
}
