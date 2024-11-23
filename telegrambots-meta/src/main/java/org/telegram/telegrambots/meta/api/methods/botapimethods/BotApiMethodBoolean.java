package org.telegram.telegrambots.meta.api.methods.botapimethods;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BotApiMethodBoolean extends BotApiMethod<Boolean> {
    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    public static abstract class BotApiMethodBooleanBuilder<C extends BotApiMethodBoolean, B extends BotApiMethodBooleanBuilder<C, B>> extends BotApiMethodBuilder<Boolean, C, B> {

    }
}
