package org.telegram.telegrambots.meta.api.objects.chat.sharetheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGiftBackdropColors;

/**
 * A backdrop for a theme of the screen for sharing a link to a user or a chat.
 * @version 10.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatShareThemeBackdrop implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String COLORS_FIELD = "colors";

    /**
     * Unique identifier of the backdrop
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Integer id;
    /**
     * Name of the backdrop
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
    /**
     * Colors of the backdrop
     */
    @JsonProperty(COLORS_FIELD)
    @NonNull
    private UniqueGiftBackdropColors colors;
}