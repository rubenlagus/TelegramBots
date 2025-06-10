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
 * Describes a story area pointing to an HTTP or tg:// link.
 * Currently, a story can have up to 3 link areas.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoryAreaTypeLink extends StoryAreaType {
    private static final String TYPE = "link";
    private static final String URL_FIELD = "url";

    /**
     * HTTP or tg:// URL to be opened when the area is clicked
     */
    @JsonProperty(URL_FIELD)
    @NonNull
    private String url;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url.isEmpty()) {
            throw new TelegramApiValidationException("URL parameter can't be empty", this);
        }
    }
    
    @Override
    public String getType() {
        return TYPE;
    }
}
