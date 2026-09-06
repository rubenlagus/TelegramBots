package org.telegram.telegrambots.meta.api.objects.community;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * Describes a service message about a chat being joined by a user from a community.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityChatJoined implements BotApiObject {
    private static final String COMMUNITY_FIELD = "community";

    /**
     * The community from which the chat was joined
     */
    @JsonProperty(COMMUNITY_FIELD)
    @NonNull
    private Community community;
}
