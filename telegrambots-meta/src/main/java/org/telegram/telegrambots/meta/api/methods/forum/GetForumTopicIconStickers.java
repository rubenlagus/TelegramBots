package org.telegram.telegrambots.meta.api.methods.forum;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

/**
 * @author Ruben Bermudez
 * @version 6.3
 * Use this method to get custom emoji stickers, which can be used as a forum topic icon by any user. Requires no parameters.
 * Returns an Array of Sticker objects.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class GetForumTopicIconStickers extends BotApiMethod<ArrayList<Sticker>> {
    private static final String PATH = "getForumTopicIconStickers";

    @Override
    public void validate() throws TelegramApiValidationException {

    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<Sticker> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, Sticker.class);
    }
}
