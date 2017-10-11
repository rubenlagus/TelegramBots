package org.telegram.telegrambots.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to set a new group sticker set for a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method.
 * Returns True on success.
 */
public class SetChatStickerSet extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatStickerSet";

    private static final String CHATID_FIELD = "chat_id";
    private static final String STICKERSETNAME_FIELD = "sticker_set_name";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(STICKERSETNAME_FIELD)
    private String stickerSetName; ///< Name of the sticker set to be set as the group sticker set

    public SetChatStickerSet() {
        super();
    }

    public SetChatStickerSet(String chatId, String stickerSetName) {
        super();
        this.chatId = checkNotNull(chatId);
        this.stickerSetName = checkNotNull(stickerSetName);
    }

    public SetChatStickerSet(Long chatId) {
        super();
        this.chatId = checkNotNull(chatId).toString();
    }

    public String getChatId() {
        return chatId;
    }

    public SetChatStickerSet setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetChatStickerSet setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public String getStickerSetName() {
        return stickerSetName;
    }

    public SetChatStickerSet setStickerSetName(String stickerSetName) {
        Objects.requireNonNull(stickerSetName);
        this.stickerSetName = stickerSetName;
        return this;
    }

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

    @Override
    public String toString() {
        return "SetChatStickerSet{" +
                "chatId='" + chatId + '\'' +
                ", stickerSetName='" + stickerSetName + '\'' +
                '}';
    }
}
