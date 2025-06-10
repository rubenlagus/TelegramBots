package org.telegram.telegrambots.meta.api.objects.stories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.stories.area.StoryAreaType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes a clickable area on a story media.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryArea implements BotApiObject, Validable {
    private static final String POSITION_FIELD = "position";
    private static final String TYPE_FIELD = "type";

    /**
     * Position of the area
     */
    @JsonProperty(POSITION_FIELD)
    @NonNull
    private StoryAreaPosition position;

    /**
     * Type of the area
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private StoryAreaType type;

    @Override
    public void validate() throws TelegramApiValidationException {
        position.validate();
        type.validate();
    }
}
