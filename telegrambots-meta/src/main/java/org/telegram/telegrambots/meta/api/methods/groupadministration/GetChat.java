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
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get information about the chat. Returns Chat object on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChat extends BotApiMethod<Chat> {
    public static final String PATH = "getChat";

    private static final String CHATID_FIELD = "chat_id";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Chat deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Chat> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Chat>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting chat", result);
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
    }
}
