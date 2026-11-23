package org.telegram.telegrambots.meta.api.methods.gifts;

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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Deletes a regular gift owned by a managed business account without receiving Telegram Stars.
 * Requires the can_convert_gifts_to_stars right. The gift must be unlimited and not refunded.
 * Returns True on success.
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
public class DeleteBusinessAccountGift extends BotApiMethodBoolean {
    public static final String PATH = "deleteBusinessAccountGift";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String OWNED_GIFT_ID_FIELD = "owned_gift_id";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Unique identifier of the regular gift that should be deleted
     */
    @JsonProperty(OWNED_GIFT_ID_FIELD)
    @NonNull
    private String ownedGiftId;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId == null || businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId can't be empty", this);
        }
        if (ownedGiftId == null || ownedGiftId.isEmpty()) {
            throw new TelegramApiValidationException("OwnedGiftId can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}