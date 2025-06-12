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
 * Describes a story area pointing to a unique gift.
 * Currently, a story can have at most 1 unique gift area.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoryAreaTypeUniqueGift extends StoryAreaType {
    private static final String TYPE = "unique_gift";
    private static final String NAME_FIELD = "name";

    /**
     * Unique name of the gift
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name.isEmpty()) {
            throw new TelegramApiValidationException("Name parameter can't be empty", this);
        }
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
