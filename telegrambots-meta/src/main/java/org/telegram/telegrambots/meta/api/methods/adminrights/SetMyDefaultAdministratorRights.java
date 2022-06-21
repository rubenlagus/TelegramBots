package org.telegram.telegrambots.meta.api.methods.adminrights;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.adminrights.ChatAdministratorRights;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Use this method to change default administrator rights of the bot for adding it as an administrator to groups or channels.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetMyDefaultAdministratorRights extends BotApiMethodBoolean {
    public static final String PATH = "setMyDefaultAdministratorRights";

    private static final String RIGHTS_FIELD = "rights";
    private static final String FORCHANNELS_FIELD = "for_channels";

    /**
     * Optional
     * A JSON-serialized object, describing new default administrator rights.
     * If not specified, the default administrator rights will be cleared.
     */
    @JsonProperty(RIGHTS_FIELD)
    private ChatAdministratorRights rights;
    /**
     * Optional
     * Pass True to change default administrator rights of the bot in channels.
     * Otherwise, default administrator rights of the bot for groups and supergroups will be changed.
     */
    @JsonProperty(FORCHANNELS_FIELD)
    private Boolean forChannels;

    @Override
    public String getMethod() {
        return PATH;
    }
}
