package org.telegram.telegrambots.meta.api.methods.adminrights;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.adminrights.ChatAdministratorRights;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * @author Ruben Bermudez
 * @version 6.0
 *
 * Use this method to get the current default administrator rights of the bot.
 *
 * Returns ChatAdministratorRights on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyDefaultAdministratorRights extends BotApiMethod<ChatAdministratorRights> {
    public static final String PATH = "getMyDefaultAdministratorRights";

    private static final String FORCHANNELS_FIELD = "for_channels";

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

    @Override
    public ChatAdministratorRights deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, ChatAdministratorRights.class);
    }
}
