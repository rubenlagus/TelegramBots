package org.telegram.telegrambots.meta.api.objects.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.3
 * This object contains information about the color scheme for a user's name, message replies and link previews based on a unique gift.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniqueGiftColors implements BotApiObject {
    private static final String MODEL_CUSTOM_EMOJI_ID_FIELD = "model_custom_emoji_id";
    private static final String SYMBOL_CUSTOM_EMOJI_ID_FIELD = "symbol_custom_emoji_id";
    private static final String LIGHT_THEME_MAIN_COLOR_FIELD = "light_theme_main_color";
    private static final String LIGHT_THEME_OTHER_COLORS_FIELD = "light_theme_other_colors";
    private static final String DARK_THEME_MAIN_COLOR_FIELD = "dark_theme_main_color";
    private static final String DARK_THEME_OTHER_COLORS_FIELD = "dark_theme_other_colors";

    /**
     * Custom emoji identifier of the unique gift's model
     */
    @JsonProperty(MODEL_CUSTOM_EMOJI_ID_FIELD)
    @NonNull
    private String modelCustomEmojiId;
    /**
     * Custom emoji identifier of the unique gift's symbol
     */
    @JsonProperty(SYMBOL_CUSTOM_EMOJI_ID_FIELD)
    @NonNull
    private String symbolCustomEmojiId;
    /**
     * Main color used in light themes; RGB format
     */
    @JsonProperty(LIGHT_THEME_MAIN_COLOR_FIELD)
    @NonNull
    private Integer lightThemeMainColor;
    /**
     * List of 1-3 additional colors used in light themes; RGB format
     */
    @JsonProperty(LIGHT_THEME_OTHER_COLORS_FIELD)
    @NonNull
    private List<Integer> lightThemeOtherColors;
    /**
     * Main color used in dark themes; RGB format
     */
    @JsonProperty(DARK_THEME_MAIN_COLOR_FIELD)
    @NonNull
    private Integer darkThemeMainColor;
    /**
     * List of 1-3 additional colors used in dark themes; RGB format
     */
    @JsonProperty(DARK_THEME_OTHER_COLORS_FIELD)
    @NonNull
    private List<Integer> darkThemeOtherColors;
}
