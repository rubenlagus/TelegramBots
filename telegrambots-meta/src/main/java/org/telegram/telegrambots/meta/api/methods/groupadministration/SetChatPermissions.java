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
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 4.4
 * Use this method to set default chat permissions for all members.
 * The bot must be an administrator in the group or a supergroup
 * for this to work and must have the can_restrict_members admin rights.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetChatPermissions extends BotApiMethodBoolean {
    public static final String PATH = "setChatPermissions";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String PERMISSIONS_FIELD = "permissions";
    private static final String USEINDEPENDENTCHATPERMISSIONS_FIELD = "use_independent_chat_permissions";

    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * New default chat permissions
     */
    @JsonProperty(PERMISSIONS_FIELD)
    @NonNull
    private ChatPermissions permissions;
    /**
     * Optional.
     * Pass True if chat permissions are set independently.
     * Otherwise, the can_send_other_messages and can_add_web_page_previews permissions
     * will imply the can_send_messages, can_send_audios, can_send_documents, can_send_photos, can_send_videos, can_send_video_notes,
     * and can_send_voice_notes permissions; the can_send_polls permission will imply the can_send_messages permission.
     */
    @JsonProperty(USEINDEPENDENTCHATPERMISSIONS_FIELD)
    private Boolean useIndependentChatPermissions;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
    }

    public static class SetChatPermissionsBuilder {

        @Tolerate
        public SetChatPermissionsBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
