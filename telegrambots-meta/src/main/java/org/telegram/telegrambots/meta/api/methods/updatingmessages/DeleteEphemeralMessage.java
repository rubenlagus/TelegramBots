package org.telegram.telegrambots.meta.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * Use this method to delete an ephemeral message.
 * Note that it is not guaranteed that the user will receive the message deletion event,
 * especially if they are offline. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteEphemeralMessage extends BotApiMethodBoolean {
    public static final String PATH = "deleteEphemeralMessage";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String RECEIVER_USER_ID_FIELD = "receiver_user_id";
    private static final String EPHEMERAL_MESSAGE_ID_FIELD = "ephemeral_message_id";

    /**
     * Unique identifier for the target chat or username of the target supergroup
     * (in the format @supergroupusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Identifier of the user who received the message
     */
    @JsonProperty(RECEIVER_USER_ID_FIELD)
    @NonNull
    private Long receiverUserId;
    /**
     * Identifier of the ephemeral message to delete
     */
    @JsonProperty(EPHEMERAL_MESSAGE_ID_FIELD)
    @NonNull
    private Integer ephemeralMessageId;

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
        Validations.requiredChatId(chatId, this);
    }

    public static abstract class DeleteEphemeralMessageBuilder<C extends DeleteEphemeralMessage, B extends DeleteEphemeralMessageBuilder<C, B>> extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public DeleteEphemeralMessageBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
