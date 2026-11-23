package org.telegram.telegrambots.meta.api.objects.chat.sharetheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.emoji.EmojiType;

import java.util.List;

/**
 * The theme with emoji placed on a background.
 * @version 10.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatShareThemeEmoji implements ChatShareTheme {
    public static final String EMOJI_TYPE = "emoji";

    private static final String TYPE_FIELD = "type";
    private static final String EMOJIS_FIELD = "emojis";
    private static final String BACKGROUND_BACKDROP_ID_FIELD = "background_backdrop_id";

    /**
     * Type of the theme, always “emoji”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = EMOJI_TYPE;
    /**
     * The emoji; 0-6 regular or custom emoji
     */
    @JsonProperty(EMOJIS_FIELD)
    @NonNull
    private final List<EmojiType> emojis;
    /**
     * Optional. Identifier of the backdrop used as the background
     */
    @JsonProperty(BACKGROUND_BACKDROP_ID_FIELD)
    private final Integer backgroundBackdropId;
}