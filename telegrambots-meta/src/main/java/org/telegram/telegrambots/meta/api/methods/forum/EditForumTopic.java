package org.telegram.telegrambots.meta.api.methods.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.3
 * Use this method to edit name and icon of a topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have
 * can_manage_topics administrator rights, unless it is the creator of the topic.
 *
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class EditForumTopic extends BotApiMethodBoolean {
    public static final String PATH = "editForumTopic";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String NAME_FIELD = "name";
    private static final String ICONCUSTOMEMOJIID_FIELD = "icon_custom_emoji_id";

    /**
     * Unique identifier for the target chat or username
     * of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Unique identifier for the target message thread of the forum topic
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    @NonNull
    private Integer messageThreadId;
    /**
     * Optional.
     * New topic name, 0-128 characters.
     * If not specified or empty, the current name of the topic will be kept
     */
    @JsonProperty(NAME_FIELD)
    private String name;
    /**
     * Optional.
     * New unique identifier of the custom emoji shown as the topic icon.
     * Use getForumTopicIconStickers to get all allowed custom emoji identifiers.
     * Pass an empty string to remove the icon.
     * If not specified, the current icon will be kept
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
        if (name != null && !name.isEmpty()) {
            if (name.length() > 128) {
                throw new TelegramApiValidationException("Name must be less than 128 characters", this);
            }
        }
        if (messageThreadId <= 0) {
            throw new TelegramApiValidationException("Message Thread Id can't be empty", this);
        }
        if (iconCustomEmojiId.isEmpty()) {
            throw new TelegramApiValidationException("Icon Custom Emoji Id can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static class EditForumTopicBuilder {

        @Tolerate
        public EditForumTopicBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
