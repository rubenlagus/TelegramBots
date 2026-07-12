package org.telegram.telegrambots.meta.api.objects.managed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 9.6
 *
 * This object contains information about the creation or token update of a bot that is managed by the current bot.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagedBotUpdated implements BotApiObject {
    private static final String USER_FIELD = "user";
    private static final String BOT_FIELD = "bot";

    /**
     * User that created the bot
     */
    @JsonProperty(USER_FIELD)
    @NonNull
    private User user;
    /**
     * Information about the bot.
     * Token of the bot can be fetched using the method getManagedBotToken.
     */
    @JsonProperty(BOT_FIELD)
    @NonNull
    private User bot;
}
