package org.telegram.telegrambots.meta.api.methods.groupadministration;

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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to change the title of a chat. Titles can't be changed for private chats.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * @apiNote In regular groups (non-supergroups), this method will only work if the ‘All Members Are Admins’ setting is off in the target group.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetChatTitle extends BotApiMethodBoolean {
    public static final String PATH = "setChatTitle";

    private static final String CHATID_FIELD = "chat_id";
    private static final String TITLE_FIELD = "title";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Required. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Required. New chat title, 1-128 characters

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    private long[] otherUserIds;
    private byte[] fileHashes;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title can't be empty", this);
        }
    }

    public static class SetChatTitleBuilder {

        @Tolerate
        public SetChatTitleBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public SetChatTitleBuilder ids(List<Byte> otherUserIds) {
            this.otherUserIds = new long[otherUserIds.size()];
            for (int i = 0; i < otherUserIds.size(); i++) {
                this.otherUserIds[i] = otherUserIds.get(i);
            }
            return this;
        }

        @Tolerate
        public SetChatTitleBuilder otherUserId(Long otherUserId) {
            long[] newOtherUserIds = new long[this.otherUserIds.length + 1];
            System.arraycopy(this.otherUserIds, 0, newOtherUserIds, 0, this.otherUserIds.length);
            newOtherUserIds[newOtherUserIds.length - 1] = otherUserId;
            this.otherUserIds = newOtherUserIds;
            return this;
        }

    }
}
