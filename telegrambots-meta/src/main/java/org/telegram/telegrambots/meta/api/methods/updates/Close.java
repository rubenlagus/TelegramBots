package org.telegram.telegrambots.meta.api.methods.updates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * Use this method to close the bot instance before moving it from one local server to another.
 * You need to delete the webhook before calling this method to ensure that the bot isn't launched again after server restart.
 * The method will return error 429 in the first 10 minutes after the bot is launched.
 * Returns True on success.
 * Requires no parameters.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Close extends BotApiMethodBoolean {
    public static final String PATH = "close";

    @Override
    public String getMethod() {
        return PATH;
    }
}
