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
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to export an invite link to a supergroup or a channel. The bot must be an administrator in the
 * chat for this to work and must have the appropriate admin rights. Returns exported invite link as String on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportChatInviteLink extends BotApiMethod<String> {
    public static final String PATH = "exportChatInviteLink";

    private static final String CHATID_FIELD = "chat_id";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public String deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<String> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<String>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error exporting invite link", result);
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
