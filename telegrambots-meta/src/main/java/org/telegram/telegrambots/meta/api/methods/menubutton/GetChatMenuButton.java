package org.telegram.telegrambots.meta.api.methods.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Use this method to get the current value of the bot's menu button in a private chat, or the default menu button.
 *
 * Returns MenuButton on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChatMenuButton extends BotApiMethod<MenuButton> {
    public static final String PATH = "getChatMenuButton";

    private static final String CHATID_FIELD = "chat_id";

    /**
     * Optional
     * Unique identifier for the target private chat.
     * If not specified, default bot's menu button will be returned
     */
    @JsonProperty(CHATID_FIELD)
    private String chatId;

    @Override
    public void validate() throws TelegramApiValidationException {

    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public MenuButton deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseDefault(answer,"Error getting chat menu button query");
    }
}
