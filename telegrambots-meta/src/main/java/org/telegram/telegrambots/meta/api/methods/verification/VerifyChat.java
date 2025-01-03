package org.telegram.telegrambots.meta.api.methods.verification;

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
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 8.2
 *
 * Verifies a chat on behalf of the organization which is represented by the bot.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyChat extends BotApiMethodBoolean {
    public static final String PATH = "verifyChat";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String CUSTOM_DESCRIPTION_FIELD = "custom_description";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;
    /**
     * Optional
     * Custom description for the verification; 0-70 characters.
     * Must be empty if the organization isn't allowed to provide a custom verification description.
     */
    @JsonProperty(CUSTOM_DESCRIPTION_FIELD)
    private String customDescription;

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        if (customDescription != null && (customDescription.length() > 70 || customDescription.isEmpty())) {
            throw new TelegramApiValidationException("Custom description must be between 0 and 70 characters long", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    public abstract static class VerifyChatBuilder<C extends VerifyChat, B extends VerifyChat.VerifyChatBuilder<C, B>> extends BotApiMethodBoolean.BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public VerifyChat.VerifyChatBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
