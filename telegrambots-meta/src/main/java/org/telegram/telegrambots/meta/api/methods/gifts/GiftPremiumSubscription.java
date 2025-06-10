package org.telegram.telegrambots.meta.api.methods.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Gifts a Telegram Premium subscription to the given user.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftPremiumSubscription extends BotApiMethodBoolean {
    public static final String PATH = "giftPremiumSubscription";

    private static final String USER_ID_FIELD = "user_id";
    private static final String MONTH_COUNT_FIELD = "month_count";
    private static final String STAR_COUNT_FIELD = "star_count";
    private static final String TEXT_FIELD = "text";
    private static final String TEXT_PARSE_MODE_FIELD = "text_parse_mode";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";

    /**
     * Unique identifier of the target user who will receive a Telegram Premium subscription
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Number of months the Telegram Premium subscription will be active for the user; must be one of 3, 6, or 12
     */
    @JsonProperty(MONTH_COUNT_FIELD)
    @NonNull
    private Integer monthCount;
    /**
     * Number of Telegram Stars to pay for the Telegram Premium subscription; must be 1000 for 3 months, 1500 for 6 months, and 2500 for 12 months
     */
    @JsonProperty(STAR_COUNT_FIELD)
    @NonNull
    private Integer starCount;
    /**
     * Optional
     * Text that will be shown along with the service message about the subscription; 0-128 characters
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional
     * Mode for parsing entities in the text. See formatting options for more details.
     *
     * @apiNote Entities other than “bold”, “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji” are ignored.
     */
    @JsonProperty(TEXT_PARSE_MODE_FIELD)
    private String textParseMode;
    /**
     * Optional
     * A JSON-serialized list of special entities that appear in the gift text.
     * It can be specified instead of text_parse_mode.
     * @apiNote  Entities other than “bold”, “italic”, “underline”, “strikethrough”, “spoiler”, and “custom_emoji” are ignored.
     */
    @JsonProperty(TEXT_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> textEntities;

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
        if (text != null && (text.isEmpty() || text.length() > 128)) {
            throw new TelegramApiValidationException("Text must be between 0 and 128 characters", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
