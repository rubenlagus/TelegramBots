package org.telegram.telegrambots.meta.api.objects.stories.area;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.objects.reactions.ReactionType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Describes a story area pointing to a suggested reaction.
 * Currently, a story can have up to 5 suggested reaction areas.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoryAreaTypeSuggestedReaction extends StoryAreaType {
    private static final String TYPE = "suggested_reaction";
    private static final String REACTION_TYPE_FIELD = "reaction_type";
    private static final String IS_DARK_FIELD = "is_dark";
    private static final String IS_FLIPPED_FIELD = "is_flipped";

    /**
     * Type of the reaction
     */
    @JsonProperty(REACTION_TYPE_FIELD)
    @NonNull
    private ReactionType reactionType;

    /**
     * Optional. Pass True if the reaction area has a dark background
     */
    @JsonProperty(IS_DARK_FIELD)
    private Boolean isDark;

    /**
     * Optional. Pass True if reaction area corner is flipped
     */
    @JsonProperty(IS_FLIPPED_FIELD)
    private Boolean isFlipped;

    @Override
    public void validate() throws TelegramApiValidationException {
        reactionType.validate();
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
