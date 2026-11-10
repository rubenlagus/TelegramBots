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
 * A custom emoji.
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
public class EmojiTypeCustomEmoji implements EmojiType {
    public static final String CUSTOM_EMOJI_TYPE = "custom_emoji";

    private static final String TYPE_FIELD = "type";
    private static final String CUSTOM_EMOJI_ID_FIELD = "custom_emoji_id";

    /**
     * Type of the emoji, always “custom_emoji”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = CUSTOM_EMOJI_TYPE;
    /**
     * Identifier of the custom emoji
     */
    @JsonProperty(CUSTOM_EMOJI_ID_FIELD)
    @NonNull
    private final String customEmojiId;
}