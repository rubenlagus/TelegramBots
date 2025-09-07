package org.telegram.telegrambots.meta.api.objects.payments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author JetBrains
 * @version 7.0
 *
 * Describes a service message about a change in the price of direct messages sent to a channel chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectMessagePriceChanged implements BotApiObject {
    private static final String ARE_DIRECT_MESSAGES_ENABLED_FIELD = "are_direct_messages_enabled";
    private static final String DIRECT_MESSAGE_STAR_COUNT_FIELD = "direct_message_star_count";

    /**
     * True, if direct messages are enabled for the channel chat; false otherwise
     */
    @JsonProperty(ARE_DIRECT_MESSAGES_ENABLED_FIELD)
    @NonNull
    private Boolean areDirectMessagesEnabled;

    /**
     * Optional. The new number of Telegram Stars that must be paid by users for each direct message sent to the channel.
     * Does not apply to users who have been exempted by administrators. Defaults to 0.
     */
    @JsonProperty(DIRECT_MESSAGE_STAR_COUNT_FIELD)
    private Integer directMessageStarCount;
}
