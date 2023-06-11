package org.telegram.telegrambots.meta.api.methods.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

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
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetChatMenuButton extends BotApiMethodBoolean {
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

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

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

    public static class SetChatMenuButtonBuilder {

        @Tolerate
        public SetChatMenuButtonBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
