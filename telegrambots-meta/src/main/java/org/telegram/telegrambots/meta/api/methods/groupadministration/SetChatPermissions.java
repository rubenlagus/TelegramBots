package org.telegram.telegrambots.meta.api.methods.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 4.4
 * Use this method to set default chat permissions for all members.
 * The bot must be an administrator in the group or a supergroup
 * for this to work and must have the can_restrict_members admin rights.
 */
public class SetChatPermissions extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatPermissions";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String PERMISSIONS_FIELD = "permissions";

    @JsonProperty(CHAT_ID_FIELD)
    private String chatId; ///< Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
    @JsonProperty(PERMISSIONS_FIELD)
    private ChatPermissions permissions; ///< New default chat permissions

    public SetChatPermissions() {
        super();
    }

    public SetChatPermissions(String chatId, ChatPermissions permissions) {
        super();
        this.chatId = checkNotNull(chatId);
        this.permissions = checkNotNull(permissions);
    }

    public SetChatPermissions(Long chatId, ChatPermissions permissions) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.permissions = checkNotNull(permissions);
    }

    public String getChatId() {
        return chatId;
    }

    public SetChatPermissions setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SetChatPermissions setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public ChatPermissions getPermissions() {
        return permissions;
    }

    public SetChatPermissions setPermissions(ChatPermissions permissions) {
        Objects.requireNonNull(permissions);
        this.permissions = permissions;
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
                throw new TelegramApiRequestException("Error setting chat description", result);
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
        if (permissions == null) {
            throw new TelegramApiValidationException("Permissions can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "SetChatPermissions{" +
                "chatId='" + chatId + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
