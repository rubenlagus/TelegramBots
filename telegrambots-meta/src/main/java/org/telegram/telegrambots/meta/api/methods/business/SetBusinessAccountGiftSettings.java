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
import org.telegram.telegrambots.meta.api.objects.gifts.AcceptedGiftTypes;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 * Changes the privacy settings pertaining to incoming gifts in a managed business account.
 * Returns True on success.
 *
 * @apiNote Requires the can_change_gift_settings business bot right.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetBusinessAccountGiftSettings extends BotApiMethodBoolean {
    public static final String PATH = "setBusinessAccountGiftSettings";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String SHOW_GIFT_BUTTON_FIELD = "show_gift_button";
    private static final String ACCEPTED_GIFT_TYPES_FIELD = "accepted_gift_types";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;
    /**
     * Pass True, if a button for sending a gift to the user or by the business account
     * must always be shown in the input field
     */
    @JsonProperty(SHOW_GIFT_BUTTON_FIELD)
    @NonNull
    private Boolean showGiftButton;
    /**
     * Types of gifts accepted by the business account
     */
    @JsonProperty(ACCEPTED_GIFT_TYPES_FIELD)
    @NonNull
    private AcceptedGiftTypes acceptedGiftTypes;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
    }
}
