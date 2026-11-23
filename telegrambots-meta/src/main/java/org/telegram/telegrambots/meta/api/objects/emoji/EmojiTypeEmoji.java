package org.telegram.telegrambots.meta.api.objects.emoji;

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

/**
 * A regular emoji.
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
public class EmojiTypeEmoji implements EmojiType {
    public static final String EMOJI_TYPE = "emoji";

    private static final String TYPE_FIELD = "type";
    private static final String EMOJI_FIELD = "emoji";

    /**
     * Type of the emoji, always “emoji”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = EMOJI_TYPE;
    /**
     * The emoji
     */
    @JsonProperty(EMOJI_FIELD)
    @NonNull
    private final String emoji;
}