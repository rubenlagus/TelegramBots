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
 * Use this method to kick a user from a group, a supergroup or a channel. In the case of supergroups,
 * the user will not be able to return to the group on their own using invite links, etc., unless
 * unbanned first. The bot must be an administrator in the group for this to work. Returns True on
 * success.
 * @apiNote  This will method only work if the ‘All Members Are Admins’ setting is off in the target
 * group. Otherwise members may only be removed by the group's creator or by the member that added
 * them.
 */
public class KickChatMember extends BotApiMethod<Boolean> {
    public static final String PATH = "kickchatmember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String UNTILDATE_FIELD = "until_date";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    private Integer userId; ///< Required. Unique identifier of the target user
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate; ///< Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever

    public KickChatMember() {
        super();
    }

    public KickChatMember(String chatId, Integer userId) {
        this.chatId = checkNotNull(chatId);
        this.userId = checkNotNull(userId);
    }

    public KickChatMember(Long chatId, Integer userId) {
        this.chatId = checkNotNull(chatId).toString();
        this.userId = checkNotNull(userId);
    }


    public String getChatId() {
        return chatId;
    }

    public KickChatMember setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public KickChatMember setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public KickChatMember setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUntilDate() {
        return untilDate;
    }

    public KickChatMember setUntilDate(Integer untilDate) {
        this.untilDate = untilDate;
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
                throw new TelegramApiRequestException("Error kicking chat member", result);
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
        return "KickChatMember{" +
                "chatId='" + chatId + '\'' +
                ", userId=" + userId +
                ", untilDate=" + untilDate +
                '}';
    }
}
