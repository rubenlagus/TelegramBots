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
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 8.0
 *
 * Sends a gift to the given user or channel chat.
 * The gift can't be converted to Telegram Stars by the receiver.
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
public class SendGift extends BotApiMethodBoolean {
    public static final String PATH = "sendGift";

    private static final String USER_ID_FIELD = "user_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String GIFT_ID_FIELD = "gift_id";
    private static final String TEXT_FIELD = "text";
    private static final String TEXT_PARSE_MODE_FIELD = "text_parse_mode";
    private static final String TEXT_ENTITIES_FIELD = "text_entities";
    private static final String PAY_FOR_UPGRADE_FIELD = "pay_for_upgrade";

    /**
     * Optional
     * Required if chat_id is not specified.
     * Unique identifier of the target user who will receive the gift.
     */
    @JsonProperty(USER_ID_FIELD)
    private Long userId;
    /**
     * Optional
     * Required if user_id is not specified. Unique identifier for the chat or username of the channel
     * (in the format @channelusername) that will receive the gift.
     */
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;
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
    /**
     * Optional
     * Pass True to pay for the gift upgrade from the bot's balance, thereby making the upgrade free for the receiver
     */
    @JsonProperty(PAY_FOR_UPGRADE_FIELD)
    private Boolean payForUpgrade;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserOrChatId(userId, chatId, this);
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

    public static abstract class SendGiftBuilder<C extends SendGift, B extends SendGift.SendGiftBuilder<C, B>>  extends BotApiMethodBooleanBuilder<C, B> {
        @Tolerate
        public SendGift.SendGiftBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
