package org.telegram.telegrambots.meta.api.methods.stories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.stories.Story;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.3
 *
 * Reposts a story on behalf of a business account from another business account.
 * Both business accounts must be managed by the same bot, and the story on the source account
 * must have been posted (or reposted) by the bot.
 * Requires the can_manage_stories business bot right for both business accounts.
 * Returns Story on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RepostStory extends BotApiMethod<Story> {
    public static final String PATH = "repostStory";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String FROM_CHAT_ID_FIELD = "from_chat_id";
    private static final String FROM_STORY_ID_FIELD = "from_story_id";
    private static final String ACTIVE_PERIOD_FIELD = "active_period";
    private static final String POST_TO_CHAT_PAGE_FIELD = "post_to_chat_page";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Unique identifier of the chat which posted the story that should be reposted
     */
    @JsonProperty(FROM_CHAT_ID_FIELD)
    @NonNull
    private Long fromChatId;

    /**
     * Unique identifier of the story that should be reposted
     */
    @JsonProperty(FROM_STORY_ID_FIELD)
    @NonNull
    private Integer fromStoryId;

    /**
     * Period after which the story is moved to the archive, in seconds;
     * must be one of 6 * 3600, 12 * 3600, 86400, or 2 * 86400
     */
    @JsonProperty(ACTIVE_PERIOD_FIELD)
    @NonNull
    private Integer activePeriod;

    /**
     * Optional. Pass True to keep the story accessible after it expires
     */
    @JsonProperty(POST_TO_CHAT_PAGE_FIELD)
    private Boolean postToChatPage;

    /**
     * Optional. Pass True if the content of the story must be protected from forwarding and screenshotting
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Story deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Story.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId == null || businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId can't be empty", this);
        }
        if (fromChatId == null || fromChatId == 0L) {
            throw new TelegramApiValidationException("FromChatId can't be empty", this);
        }
        if (fromStoryId == null || fromStoryId == 0) {
            throw new TelegramApiValidationException("FromStoryId can't be empty", this);
        }
        if (activePeriod == null) {
            throw new TelegramApiValidationException("ActivePeriod can't be null", this);
        }
        if (activePeriod != 6 * 3600 && activePeriod != 12 * 3600 &&
            activePeriod != 86400 && activePeriod != 2 * 86400) {
            throw new TelegramApiValidationException("ActivePeriod must be one of 6*3600, 12*3600, 86400, or 2*86400", this);
        }
    }
}
