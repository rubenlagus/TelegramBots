package org.telegram.telegrambots.meta.api.objects.chat.sharetheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * A list of backdrops for chat share themes.
 * @version 10.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatShareThemeBackdrops implements BotApiObject {
    private static final String BACKDROPS_FIELD = "backdrops";

    /**
     * The list of backdrops
     */
    @JsonProperty(BACKDROPS_FIELD)
    @NonNull
    private List<ChatShareThemeBackdrop> backdrops;
}