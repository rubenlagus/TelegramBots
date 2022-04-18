package org.telegram.telegrambots.meta.api.methods.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.api.objects.webapp.SentWebAppMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Use this method to change bot's menu button in a private chat, or the default menu button.
 *
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetChatMenuButton extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatMenuButton";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MENUBUTTON_FIELD = "menu_button";

    /**
     * Optional
     * Unique identifier for the target private chat.
     * If not specified, default bot's menu button will be changed
     */
    @JsonProperty(CHATID_FIELD)
    private String chatId;
    /**
     * Optional
     * A JSON-serialized object for the new bot's menu button.
     * Defaults to MenuButtonDefault
     */
    @JsonProperty(MENUBUTTON_FIELD)
    private MenuButton menuButton;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (menuButton != null) {
            menuButton.validate();
        }
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
                throw new TelegramApiRequestException("Error setting chat menu button query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }
}
