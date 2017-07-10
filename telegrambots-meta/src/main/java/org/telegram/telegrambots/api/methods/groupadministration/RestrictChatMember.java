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
 * Use this method to restrict a user in a supergroup.
 * The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights.
 * Pass True for all boolean parameters to lift restrictions from a user. Returns True on success.
 *
 */
public class RestrictChatMember extends BotApiMethod<Boolean> {
    public static final String PATH = "restrictchatmember";

    private static final String CHATID_FIELD = "chat_id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String UNTILDATE_FIELD = "until_date";
    private static final String CANSENDMESSAGES_FIELD = "can_send_messages";
    private static final String CANSENDMEDIAMESSAGES_FIELD = "can_send_media_messages";
    private static final String CANSENDOTHERMESSAGES_FIELD = "can_send_other_messages";
    private static final String CANADDWEBPAGEPREVIEWS_FIELD = "can_add_web_page_previews";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///< Required. Unique identifier for the chat to send the message to (Or username for channels)
    @JsonProperty(USER_ID_FIELD)
    private Integer userId; ///< Required. Unique identifier of the target user
    @JsonProperty(UNTILDATE_FIELD)
    private Integer untilDate; ///< Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be banned forever
    @JsonProperty(CANSENDMESSAGES_FIELD)
    private Boolean canSendMessages; ///< Pass True, if the user can send text messages, contacts, locations and venues
    @JsonProperty(CANSENDMEDIAMESSAGES_FIELD)
    private Boolean canSendMediaMessages; ///< Pass True, if the user can send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
    @JsonProperty(CANSENDOTHERMESSAGES_FIELD)
    private Boolean canSendOtherMessages; ///< Pass True, if the user can send animations, games, stickers and use inline bots, implies can_send_media_messages
    @JsonProperty(CANADDWEBPAGEPREVIEWS_FIELD)
    private Boolean canAddWebPagePreviews; ///< Pass True, if the user may add web page previews to their messages, implies can_send_messages


    public RestrictChatMember() {
        super();
    }

    public RestrictChatMember(String chatId, Integer userId) {
        this.chatId = checkNotNull(chatId);
        this.userId = checkNotNull(userId);
    }

    public RestrictChatMember(Long chatId, Integer userId) {
        this.chatId = checkNotNull(chatId).toString();
        this.userId = checkNotNull(userId);
    }

    public String getChatId() {
        return chatId;
    }

    public RestrictChatMember setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public RestrictChatMember setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public RestrictChatMember setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUntilDate() {
        return untilDate;
    }

    public RestrictChatMember setUntilDate(Integer untilDate) {
        this.untilDate = untilDate;
        return this;
    }

    public Boolean getCanSendMessages() {
        return canSendMessages;
    }

    public RestrictChatMember setCanSendMessages(Boolean canSendMessages) {
        this.canSendMessages = canSendMessages;
        return this;
    }

    public Boolean getCanSendMediaMessages() {
        return canSendMediaMessages;
    }

    public RestrictChatMember setCanSendMediaMessages(Boolean canSendMediaMessages) {
        this.canSendMediaMessages = canSendMediaMessages;
        return this;
    }

    public Boolean getCanSendOtherMessages() {
        return canSendOtherMessages;
    }

    public RestrictChatMember setCanSendOtherMessages(Boolean canSendOtherMessages) {
        this.canSendOtherMessages = canSendOtherMessages;
        return this;
    }

    public Boolean getCanAddWebPagePreviews() {
        return canAddWebPagePreviews;
    }

    public RestrictChatMember setCanAddWebPagePreviews(Boolean canAddWebPagePreviews) {
        this.canAddWebPagePreviews = canAddWebPagePreviews;
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
                throw new TelegramApiRequestException("Error restricting chat member", result);
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
            throw new TelegramApiValidationException("UserId can't be empty", this);
        }
    }

    @Override
    public String toString() {
        return "RestrictChatMember{" +
                "chatId='" + chatId + '\'' +
                ", userId=" + userId +
                ", untilDate=" + untilDate +
                ", canSendMessages=" + canSendMessages +
                ", canSendMediaMessages=" + canSendMediaMessages +
                ", canSendOtherMessages=" + canSendOtherMessages +
                ", canAddWebPagePreviews=" + canAddWebPagePreviews +
                '}';
    }
}
