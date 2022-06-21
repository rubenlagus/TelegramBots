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
 * Use this method to log out from the cloud Bot API server before launching the bot locally.
 * You must log out the bot before running it locally, otherwise there is no guarantee that the bot will receive updates.
 * After a successful call, you will not be able to log in again using the same token for 10 minutes.
 * Returns True on success.
 * Requires no parameters.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class LogOut extends BotApiMethodBoolean {
    public static final String PATH = "logOut";

    @Override
    public String getMethod() {
        return PATH;
    }
}
