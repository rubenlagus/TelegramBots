package org.telegram.telegrambots.meta.api.objects.chat.background.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 7.3
 *
 * The background is taken directly from a built-in chat theme.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackgroundTypeChatTheme implements BackgroundType {
    public static final String CHAT_THEME_TYPE = "chat_theme";

    private static final String TYPE_FIELD = "type";
    private static final String THEME_NAME_FIELD = "theme_name";

    /**
     * Type of the background, always “chat_theme”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = CHAT_THEME_TYPE;
    /**
     * Name of the chat theme, which is usually an emoji
     */
    @JsonProperty(THEME_NAME_FIELD)
    private final String themeName;
}
