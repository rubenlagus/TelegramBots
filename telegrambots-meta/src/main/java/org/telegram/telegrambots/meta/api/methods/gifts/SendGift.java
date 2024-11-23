package org.telegram.telegrambots.meta.api.methods.gifts;

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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 8.0
 *
 * Sends a gift to the given user.
 * The gift can't be converted to Telegram Stars by the user.
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
public class SendGift extends BotApiMethodBoolean {
    public static final String PATH = "sendGift";

    private static final String USER_ID_FIELD = "user_id";
    private static final String GIFT_ID_FIELD = "gift_id";
    private static final String TEXT_FIELD = "text";
    private static final String TEXT_PARSE_MODE_FIELD = "text_parse_mode";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";

    /**
     * Unique identifier of the target user that will receive the gift
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Integer userId;
    /**
     * Identifier of the gift
     */
    @JsonProperty(GIFT_ID_FIELD)
    @NonNull
    private String giftId;
    /**
     * Optional
     * Text that will be shown along with the gift; 0-255 characters
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional
     * Mode for parsing entities in the text.
     * See formatting options for more details.
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
        if (userId == 0) {
            throw new TelegramApiValidationException("UserId can't be empty", this);
        }
        if (giftId.isEmpty()) {
            throw new TelegramApiValidationException("GiftId can't be empty", this);
        }
        if (text != null && (text.isEmpty() || text.length() > 255)) {
            throw new TelegramApiValidationException("Text must be between 0 and 255 characters", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
