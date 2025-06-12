package org.telegram.telegrambots.meta.api.methods.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Transfers Telegram Stars from the business account balance to the bot's balance.
 * Returns True on success.
 *
 * @apiNote Requires the can_transfer_stars business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferBusinessAccountStars extends BotApiMethodBoolean {
    public static final String PATH = "transferBusinessAccountStars";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String STAR_COUNT_FIELD = "star_count";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Number of Telegram Stars to transfer; 1-10000
     */
    @JsonProperty(STAR_COUNT_FIELD)
    @NonNull
    private Integer starCount;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty", this);
        }

        if (starCount < 1 || starCount > 10000) {
            throw new TelegramApiValidationException("StarCount must be between 1 and 10000", this);
        }
    }
}
