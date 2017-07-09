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
 * @version 3.1
 * Use this method to promote or demote a user in a supergroup or a channel.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Pass False for all boolean parameters to demote a user. Returns True on success.
 *
 */
public class PromoteChatMember extends BotApiMethod<Boolean> {
    public static final String PATH = "promoteChatMember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String CANCHANGEINFORMATION_FIELD = "can_change_info";
    private static final String CANPOSTMESSAGES_FIELD = "can_post_messages";
    private static final String CANEDITMESSAGES_FIELD = "can_edit_messages";
    private static final String CANDELETEMESSAGES_FIELD = "can_delete_messages";
    private static final String CANINVITEUSERS_FIELD = "can_invite_users";
    private static final String CANRESTRICTMEMBERS_FIELD = "can_restrict_members";
    private static final String CANPINMESSAGES_FIELD = "can_pin_messages";
    private static final String CANPROMOTEMEMBERS_FIELD = "can_promote_members";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    private Integer userId; ///< Required. Unique identifier of the target user
    @JsonProperty(CANCHANGEINFORMATION_FIELD)
    private Boolean canChangeInformation; ///< Pass True, if the administrator can change chat title, photo and other settings
    @JsonProperty(CANPOSTMESSAGES_FIELD)
    private Boolean canPostMessages; ///< Pass True, if the administrator can create channel posts, channels only
    @JsonProperty(CANEDITMESSAGES_FIELD)
    private Boolean canEditMessages; ///< Pass True, if the administrator can edit messages of other users, channels only
    @JsonProperty(CANDELETEMESSAGES_FIELD)
    private Boolean canDeleteMessages; ///< Pass True, if the administrator can delete messages of other users
    @JsonProperty(CANINVITEUSERS_FIELD)
    private Boolean canInviteUsers; ///< Pass True, if the administrator can invite new users to the chat
    @JsonProperty(CANRESTRICTMEMBERS_FIELD)
    private Boolean canRestrictMembers; ///< Pass True, if the administrator can restrict, ban or unban chat members
    @JsonProperty(CANPINMESSAGES_FIELD)
    private Boolean canPinMessages; ///< Pass True, if the administrator can pin messages, supergroups only
    @JsonProperty(CANPROMOTEMEMBERS_FIELD)
    private Boolean canPromoteMembers; ///< Pass True, if the administrator can add new administrators with a subset of his own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administators that were appointed by the him)

    public PromoteChatMember() {
        super();
    }

    public PromoteChatMember(String chatId, Integer userId) {
        this.chatId = checkNotNull(chatId);
        this.userId = checkNotNull(userId);
    }

    public PromoteChatMember(Long chatId, Integer userId) {
        this.chatId = checkNotNull(chatId).toString();
        this.userId = checkNotNull(userId);
    }

    public String getChatId() {
        return chatId;
    }

    public PromoteChatMember setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public PromoteChatMember setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public PromoteChatMember setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Boolean getCanChangeInformation() {
        return canChangeInformation;
    }

    public PromoteChatMember setCanChangeInformation(Boolean canChangeInformation) {
        this.canChangeInformation = canChangeInformation;
        return this;
    }

    public Boolean getCanPostMessages() {
        return canPostMessages;
    }

    public PromoteChatMember setCanPostMessages(Boolean canPostMessages) {
        this.canPostMessages = canPostMessages;
        return this;
    }

    public Boolean getCanEditMessages() {
        return canEditMessages;
    }

    public PromoteChatMember setCanEditMessages(Boolean canEditMessages) {
        this.canEditMessages = canEditMessages;
        return this;
    }

    public Boolean getCanDeleteMessages() {
        return canDeleteMessages;
    }

    public PromoteChatMember setCanDeleteMessages(Boolean canDeleteMessages) {
        this.canDeleteMessages = canDeleteMessages;
        return this;
    }

    public Boolean getCanInviteUsers() {
        return canInviteUsers;
    }

    public PromoteChatMember setCanInviteUsers(Boolean canInviteUsers) {
        this.canInviteUsers = canInviteUsers;
        return this;
    }

    public Boolean getCanRestrictMembers() {
        return canRestrictMembers;
    }

    public PromoteChatMember setCanRestrictMembers(Boolean canRestrictMembers) {
        this.canRestrictMembers = canRestrictMembers;
        return this;
    }

    public Boolean getCanPinMessages() {
        return canPinMessages;
    }

    public PromoteChatMember setCanPinMessages(Boolean canPinMessages) {
        this.canPinMessages = canPinMessages;
        return this;
    }

    public Boolean getCanPromoteMembers() {
        return canPromoteMembers;
    }

    public PromoteChatMember setCanPromoteMembers(Boolean canPromoteMembers) {
        this.canPromoteMembers = canPromoteMembers;
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
                    new TypeReference<ApiResponse<Boolean>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error promoting chat member", result);
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
        if (userId == null) {
            throw new TelegramApiValidationException("UserId can't be null", this);
        }
    }

    @Override
    public String toString() {
        return "PromoteChatMember{" +
                "chatId='" + chatId + '\'' +
                ", userId=" + userId +
                ", canChangeInformation=" + canChangeInformation +
                ", canPostMessages=" + canPostMessages +
                ", canEditMessages=" + canEditMessages +
                ", canDeleteMessages=" + canDeleteMessages +
                ", canInviteUsers=" + canInviteUsers +
                ", canRestrictMembers=" + canRestrictMembers +
                ", canPinMessages=" + canPinMessages +
                ", canPromoteMembers=" + canPromoteMembers +
                '}';
    }
}
