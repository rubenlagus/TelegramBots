package org.telegram.telegrambots.meta.api.methods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;

/**
 * @author Ruben Bermudez
 * @version 9.4
 * Removes the profile photo of the bot. Requires no parameters. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class RemoveMyProfilePhoto extends BotApiMethodBoolean {
    public static final String PATH = "removeMyProfilePhoto";

    @Override
    public String getMethod() {
        return PATH;
    }
}
