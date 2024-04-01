package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The reaction is based on a custom emoji.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReactionTypeCustomEmoji implements ReactionType {
    private static final String TYPE_FIELD = "type";
    private static final String CUSTOM_EMOJI_ID_FIELD = "custom_emoji_id";

    /**
     * Type of the reaction, always “custom_emoji”
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    @Builder.Default
    private String type = ReactionType.CUSTOM_EMOJI_TYPE;
    /**
     * Custom emoji identifier
     */
    @JsonProperty(CUSTOM_EMOJI_ID_FIELD)
    @NonNull
    private String customEmojiId;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (customEmojiId.isEmpty()) {
            throw new TelegramApiValidationException("CustomEmojiId parameter can't be empty", this);
        }
        if (!ReactionType.CUSTOM_EMOJI_TYPE.equals(type)) {
            throw new TelegramApiValidationException("Type must be \"custom_emoji\"", this);
        }
    }
}
