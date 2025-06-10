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
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.stories.Story;
import org.telegram.telegrambots.meta.api.objects.stories.StoryArea;
import org.telegram.telegrambots.meta.api.objects.stories.input.InputStoryContent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Posts a story on behalf of a managed business account.
 * Requires the can_manage_stories business bot right.
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
public class PostStory extends BotApiMethod<Story> {
    public static final String PATH = "postStory";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String CONTENT_FIELD = "content";
    private static final String ACTIVE_PERIOD_FIELD = "active_period";
    private static final String CAPTION_FIELD = "caption";
    private static final String PARSE_MODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    private static final String AREAS_FIELD = "areas";
    private static final String POST_TO_CHAT_PAGE_FIELD = "post_to_chat_page";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Content of the story
     */
    @JsonProperty(CONTENT_FIELD)
    @NonNull
    private InputStoryContent content;

    /**
     * Period after which the story is moved to the archive, in seconds;
     * must be one of 6 * 3600, 12 * 3600, 86400, or 2 * 86400
     */
    @JsonProperty(ACTIVE_PERIOD_FIELD)
    @NonNull
    private Integer activePeriod;

    /**
     * Optional. Caption of the story, 0-2048 characters after entities parsing
     */
    @JsonProperty(CAPTION_FIELD)
    private String caption;

    /**
     * Optional. Mode for parsing entities in the story caption. See formatting options for more details.
     */
    @JsonProperty(PARSE_MODE_FIELD)
    private String parseMode;

    /**
     * Optional. A JSON-serialized list of special entities that appear in the caption,
     * which can be specified instead of parse_mode
     */
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    private List<MessageEntity> captionEntities;

    /**
     * Optional. A JSON-serialized list of clickable areas to be shown on the story
     */
    @JsonProperty(AREAS_FIELD)
    private List<StoryArea> areas;

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

    @Tolerate
    public void setBusinessConnectionId(Long businessConnectionId) {
        this.businessConnectionId = businessConnectionId.toString();
    }

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
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty", this);
        }

        content.validate();

        // Check that activePeriod is one of the allowed values
        int sixHours = 6 * 3600;
        int twelveHours = 12 * 3600;
        int oneDay = 86400;
        int twoDays = 2 * 86400;
        
        if (activePeriod != sixHours && activePeriod != twelveHours && 
            activePeriod != oneDay && activePeriod != twoDays) {
            throw new TelegramApiValidationException(
                "ActivePeriod must be one of: 6 hours (21600), 12 hours (43200), 24 hours (86400), or 48 hours (172800)", 
                this
            );
        }

        if (caption != null && caption.length() > 2048) {
            throw new TelegramApiValidationException("Caption can't be longer than 2048 characters", this);
        }

        if (areas != null) {
            for (StoryArea area : areas) {
                area.validate();
            }
        }
    }
}
