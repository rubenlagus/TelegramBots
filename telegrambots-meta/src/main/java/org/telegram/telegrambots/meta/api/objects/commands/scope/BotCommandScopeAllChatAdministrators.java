package org.telegram.telegrambots.meta.api.objects.commands.scope;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * Represents the scope of bot commands, covering all group and supergroup chat administrators.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Jacksonized
public class BotCommandScopeAllChatAdministrators implements BotCommandScope {
    private static final String TYPE_FIELD = "type";

    /**
     * Scope type, must be chat
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "all_chat_administrators";

}
