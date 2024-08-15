package org.telegram.telegrambots.meta.api.objects.reactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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
public class ReactionTypePaid implements ReactionType {
    private static final String TYPE_FIELD = "type";
    private static final String CUSTOM_EMOJI_ID_FIELD = "custom_emoji_id";

    /**
     * Type of the reaction, always “paid”
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    @Builder.Default
    private String type = ReactionType.PAID;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (!ReactionType.PAID.equals(type)) {
            throw new TelegramApiValidationException("Type must be \"paid\"", this);
        }
    }
}
