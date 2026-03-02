package org.telegram.telegrambots.meta.api.objects;

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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.4
 *
 * This object represents the audios displayed on a user's profile.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileAudios implements BotApiObject {
    private static final String TOTAL_COUNT_FIELD = "total_count";
    private static final String AUDIOS_FIELD = "audios";

    /**
     * Total number of profile audios for the target user
     */
    @JsonProperty(TOTAL_COUNT_FIELD)
    @NonNull
    private Integer totalCount;
    /**
     * Requested profile audios
     */
    @JsonProperty(AUDIOS_FIELD)
    @NonNull
    private List<Audio> audios;
}
