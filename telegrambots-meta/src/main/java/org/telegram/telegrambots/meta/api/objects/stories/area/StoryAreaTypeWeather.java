package org.telegram.telegrambots.meta.api.objects.stories.area;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Describes a story area containing weather information.
 * Currently, a story can have up to 3 weather areas.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoryAreaTypeWeather extends StoryAreaType {
    private static final String TYPE = "weather";
    private static final String TEMPERATURE_FIELD = "temperature";
    private static final String EMOJI_FIELD = "emoji";
    private static final String BACKGROUND_COLOR_FIELD = "background_color";

    /**
     * Temperature, in degree Celsius
     */
    @JsonProperty(TEMPERATURE_FIELD)
    @NonNull
    private Float temperature;

    /**
     * Emoji representing the weather
     */
    @JsonProperty(EMOJI_FIELD)
    @NonNull
    private String emoji;

    /**
     * A color of the area background in the ARGB format
     */
    @JsonProperty(BACKGROUND_COLOR_FIELD)
    @NonNull
    private Integer backgroundColor;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (emoji.isEmpty()) {
            throw new TelegramApiValidationException("Emoji parameter can't be empty", this);
        }
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
