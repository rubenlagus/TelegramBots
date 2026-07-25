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
 * @version 10.2
 * Describes a service message about a chat being added to a community.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityChatAdded implements BotApiObject {
    private static final String COMMUNITY_FIELD = "community";

    /**
     * The new community to which the chat belongs
     */
    @JsonProperty(COMMUNITY_FIELD)
    @NonNull
    private Community community;
}
