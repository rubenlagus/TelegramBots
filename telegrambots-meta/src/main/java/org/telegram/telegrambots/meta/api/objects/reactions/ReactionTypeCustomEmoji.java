package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * The reaction is based on a custom emoji.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ReactionTypeCustomEmoji implements ReactionType {
    private static final String TYPE_FIELD = "type";
    private static final String CUSTOM_EMOJI_FIELD = "custom_emoji";

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
    @JsonProperty(CUSTOM_EMOJI_FIELD)
    @NonNull
    private String customEmoji;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (customEmoji.isEmpty()) {
            throw new TelegramApiValidationException("CustomEmoji parameter can't be empty", this);
        }
        if (ReactionType.CUSTOM_EMOJI_TYPE.equals(type)) {
            throw new TelegramApiValidationException("Type must be \"custom_emoji\"", this);
        }
    }
}
