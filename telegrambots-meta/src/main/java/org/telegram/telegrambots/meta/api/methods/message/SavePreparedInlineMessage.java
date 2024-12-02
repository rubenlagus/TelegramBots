package org.telegram.telegrambots.meta.api.methods.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.message.PreparedInlineMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 8.0
 * Stores a message that can be sent by a user of a Mini App.
 * Returns a PreparedInlineMessage object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavePreparedInlineMessage extends BotApiMethod<PreparedInlineMessage> {
    public static final String PATH = "savePreparedInlineMessage";

    private static final String USER_ID_FIELD = "user_id";
    private static final String RESULT_FIELD = "result";
    private static final String ALLOW_USER_CHATS_FIELD = "allow_user_chats";
    private static final String ALLOW_BOT_CHATS_FIELD = "allow_bot_chats";
    private static final String ALLOW_GROUP_CHATS_FIELD = "allow_group_chats";
    private static final String ALLOW_CHANNEL_CHATS_FIELD = "allow_channel_chats";

    /**
     * Unique identifier of the target user that can use the prepared message
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * A JSON-serialized object describing the message to be sent
     */
    @JsonProperty(RESULT_FIELD)
    @NonNull
    private InlineQueryResult result;
    /**
     * Pass True if the message can be sent to private chats with users
     */
    @JsonProperty(ALLOW_USER_CHATS_FIELD)
    private Boolean allowUserChats;
    /**
     * Pass True if the message can be sent to private chats with bots
     */
    @JsonProperty(ALLOW_BOT_CHATS_FIELD)
    private Boolean allowBotChats;
    /**
     * Pass True if the message can be sent to group and supergroup chats
     */
    @JsonProperty(ALLOW_GROUP_CHATS_FIELD)
    @NonNull
    private Boolean allowGroupChats;
    /**
     * Pass True if the message can be sent to channel chats
     */
    @JsonProperty(ALLOW_CHANNEL_CHATS_FIELD)
    @NonNull
    private Boolean allowChannelChats;

    @Override
    public PreparedInlineMessage deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, PreparedInlineMessage.class);
    }


    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
        result.validate();
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
