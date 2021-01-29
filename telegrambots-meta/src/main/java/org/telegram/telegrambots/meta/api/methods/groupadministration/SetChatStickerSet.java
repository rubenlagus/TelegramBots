package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to set a new group sticker set for a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetChatStickerSet extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatStickerSet";

    private static final String CHATID_FIELD = "chat_id";
    private static final String STICKERSETNAME_FIELD = "sticker_set_name";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(STICKERSETNAME_FIELD)
    @NonNull
    private String stickerSetName; ///< Name of the sticker set to be set as the group sticker set

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting chat sticker set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (stickerSetName == null || stickerSetName.isEmpty()) {
            throw new TelegramApiValidationException("StickerSetName can't be empty", this);
        }
    }
}
