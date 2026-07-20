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
 * This object contains information about the bot that was created to be managed by the current bot.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagedBotCreated implements BotApiObject {
    private static final String BOT_FIELD = "bot";

    /**
     * Information about the bot.
     * The bot's token can be fetched using the method getManagedBotToken.
     */
    @JsonProperty(BOT_FIELD)
    @NonNull
    private User bot;
}
