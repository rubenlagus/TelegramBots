package org.telegram.telegrambots.meta.api.objects.chat.sharetheme;

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
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGift;

/**
 * The theme based on a unique gift owned by the user or the chat.
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
public class ChatShareThemeUniqueGift implements ChatShareTheme {
    public static final String UNIQUE_GIFT_TYPE = "unique_gift";

    private static final String TYPE_FIELD = "type";
    private static final String GIFT_FIELD = "gift";

    /**
     * Type of the theme, always “unique_gift”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = UNIQUE_GIFT_TYPE;
    /**
     * The gift
     */
    @JsonProperty(GIFT_FIELD)
    @NonNull
    private final UniqueGift gift;
}