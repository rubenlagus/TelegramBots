package org.telegram.telegrambots.meta.api.methods.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Deletes a story previously posted by the bot on behalf of a managed business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_manage_stories business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteStory extends BotApiMethodBoolean {
    public static final String PATH = "deleteStory";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String STORY_ID_FIELD = "story_id";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Unique identifier of the story to delete
     */
    @JsonProperty(STORY_ID_FIELD)
    @NonNull
    private Integer storyId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty", this);
        }
    }
}
