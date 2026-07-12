package org.telegram.telegrambots.meta.api.objects.richtext;

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

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A custom emoji.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextCustomEmoji implements RichText {
    public static final String TYPE = "custom_emoji";
    private static final String TYPE_FIELD = "type";
    private static final String CUSTOM_EMOJI_ID_FIELD = "custom_emoji_id";
    private static final String ALTERNATIVE_TEXT_FIELD = "alternative_text";

    /**
     * Type of the rich text, always "custom_emoji"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Unique identifier of the custom emoji. Use getCustomEmojiStickers to get full information about the sticker.
     */
    @JsonProperty(CUSTOM_EMOJI_ID_FIELD)
    @NonNull
    private String customEmojiId;

    /**
     * Alternative emoji for the custom emoji
     */
    @JsonProperty(ALTERNATIVE_TEXT_FIELD)
    @NonNull
    private String alternativeText;
}
