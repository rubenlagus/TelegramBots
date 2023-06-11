package org.telegram.telegrambots.meta.api.methods.groupadministration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to set a new profile photo for the chat. Photos can't be changed for private chats.
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
public class SetChatPhoto extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "setChatPhoto";

    public static final String CHATID_FIELD = "chat_id";
    public static final String PHOTO_FIELD = "photo";

    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @NonNull
    private InputFile photo; ///< New chat photo as InputStream, uploaded using multipart/form-data

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (!photo.isNew()) {
            throw new TelegramApiValidationException("Photo parameter is required and must be a new file to upload", this);
        }
    }

    public static class SetChatPhotoBuilder {

        @Tolerate
        public SetChatPhotoBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
