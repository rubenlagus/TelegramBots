package org.telegram.telegrambots.meta.api.objects.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

/**
 * @author Ruben Bermudez
 * @version 7.2
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
public class BusinessIntro implements BotApiObject {
    private static final String TITLE_FIELD = "title";
    private static final String MESSAGE_FIELD = "message";
    private static final String STICKER_FIELD = "sticker";

    /**
     * Optional.
     * Title text of the business intro
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * Optional.
     * Message text of the business intro
     */
    @JsonProperty(MESSAGE_FIELD)
    private String message;
    /**
     * Optional.
     * Sticker of the business intro
     */
    @JsonProperty(STICKER_FIELD)
    private Sticker sticker;
}
