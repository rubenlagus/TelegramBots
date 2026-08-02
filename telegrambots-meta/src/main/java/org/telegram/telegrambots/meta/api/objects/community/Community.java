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
 * Represents a community (a group of chats).
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Community implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";

    /**
     * Unique identifier for this community.
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it. But it has at most 52 significant bits,
     * so a signed 64-bit integer or double-precision float type are safe for storing this identifier.
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Long id;

    /**
     * Name of the community
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
}
