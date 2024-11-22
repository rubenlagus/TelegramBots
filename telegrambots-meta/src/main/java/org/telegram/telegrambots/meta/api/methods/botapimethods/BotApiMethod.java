package org.telegram.telegrambots.meta.api.methods.botapimethods;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BotApiMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    protected static final String METHOD_FIELD = "method";

    public static abstract class BotApiMethodBuilder<T extends Serializable, C extends BotApiMethod<T>, B extends BotApiMethodBuilder<T, C, B>> extends PartialBotApiMethodBuilder<T, C, B> {

    }
}
