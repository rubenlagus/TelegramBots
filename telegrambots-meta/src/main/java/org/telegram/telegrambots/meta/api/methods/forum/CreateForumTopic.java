package org.telegram.telegrambots.meta.api.methods.forum;

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
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.forum.ForumTopic;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.3
 * Use this method to create a topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the can_manage_topics administrator rights.
 * Returns information about the created topic as a ForumTopic object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreateForumTopic extends BotApiMethod<ForumTopic> {
    public static final String PATH = "createForumTopic";

    private static final String CHATID_FIELD = "chat_id";
    private static final String NAME_FIELD = "name";
    private static final String ICONCOLOR_FIELD = "icon_color";
    private static final String ICONCUSTOMEMOJIID_FIELD = "icon_custom_emoji_id";

    /**
     * Unique identifier for the target chat or username
     * of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Topic name, 1-128 characters
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
    /**
     * Optional.
     * Color of the topic icon in RGB format. Currently,
     * must be one of 0x6FB9F0, 0xFFD67E, 0xCB86DB, 0x8EEE98, 0xFF93B2, or 0xFB6F5F
     */
    @JsonProperty(ICONCOLOR_FIELD)
    private Integer iconColor;
    /**
     * Optional.
     * Unique identifier of the custom emoji shown as the topic icon.
     * Use getForumTopicIconStickers to get all allowed custom emoji identifiers
     */
    @JsonProperty(ICONCUSTOMEMOJIID_FIELD)
    private String iconCustomEmojiId;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (name.isEmpty() || name.length() > 128) {
            throw new TelegramApiValidationException("Name must be between 1 and 128 characters", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ForumTopic deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, ForumTopic.class);
    }

    public static class CreateForumTopicBuilder {

        @Tolerate
        public CreateForumTopicBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
