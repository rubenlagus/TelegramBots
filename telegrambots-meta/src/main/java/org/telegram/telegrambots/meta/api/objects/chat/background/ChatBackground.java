package org.telegram.telegrambots.meta.api.objects.chat.background;

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
import org.telegram.telegrambots.meta.api.objects.chat.background.type.BackgroundType;

/**
 * This object represents a chat background.
 * @author Ruben Bermudez
 * @version 7.3
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatBackground implements BotApiObject {
    private static final String TYPE_FIELD = "type";

    /**
     * Type of the background
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private BackgroundType type;
}
