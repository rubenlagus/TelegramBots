package org.telegram.telegrambots.meta.api.methods.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.gifts.owned.OwnedGifts;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.3
 * Returns the gifts owned and hosted by a user. Returns OwnedGifts on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserGifts extends BotApiMethod<OwnedGifts> {
    public static final String PATH = "getUserGifts";

    private static final String USER_ID_FIELD = "user_id";
    private static final String EXCLUDE_UNLIMITED_FIELD = "exclude_unlimited";
    private static final String EXCLUDE_LIMITED_UPGRADABLE_FIELD = "exclude_limited_upgradable";
    private static final String EXCLUDE_LIMITED_NON_UPGRADABLE_FIELD = "exclude_limited_non_upgradable";
    private static final String EXCLUDE_FROM_BLOCKCHAIN_FIELD = "exclude_from_blockchain";
    private static final String EXCLUDE_UNIQUE_FIELD = "exclude_unique";
    private static final String SORT_BY_PRICE_FIELD = "sort_by_price";
    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";

    /**
     * Unique identifier of the user
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;

    /**
     * Optional. Pass True to exclude gifts that can be purchased an unlimited number of times
     */
    @JsonProperty(EXCLUDE_UNLIMITED_FIELD)
    private Boolean excludeUnlimited;

    /**
     * Optional. Pass True to exclude gifts that can be purchased a limited number of times and can be upgraded to unique
     */
    @JsonProperty(EXCLUDE_LIMITED_UPGRADABLE_FIELD)
    private Boolean excludeLimitedUpgradable;

    /**
     * Optional. Pass True to exclude gifts that can be purchased a limited number of times and can't be upgraded to unique
     */
    @JsonProperty(EXCLUDE_LIMITED_NON_UPGRADABLE_FIELD)
    private Boolean excludeLimitedNonUpgradable;

    /**
     * Optional. Pass True to exclude gifts that were assigned from the TON blockchain and can't be resold or transferred in Telegram
     */
    @JsonProperty(EXCLUDE_FROM_BLOCKCHAIN_FIELD)
    private Boolean excludeFromBlockchain;

    /**
     * Optional. Pass True to exclude unique gifts
     */
    @JsonProperty(EXCLUDE_UNIQUE_FIELD)
    private Boolean excludeUnique;

    /**
     * Optional. Pass True to sort results by gift price instead of send date. Sorting is applied before pagination.
     */
    @JsonProperty(SORT_BY_PRICE_FIELD)
    private Boolean sortByPrice;

    /**
     * Optional. Offset of the first entry to return as received from the previous request; use an empty string to get the first chunk of results
     */
    @JsonProperty(OFFSET_FIELD)
    private String offset;

    /**
     * Optional. The maximum number of gifts to be returned; 1-100. Defaults to 100
     */
    @JsonProperty(LIMIT_FIELD)
    private Integer limit;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public OwnedGifts deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, OwnedGifts.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null || userId == 0L) {
            throw new TelegramApiValidationException("UserId can't be empty", this);
        }
        if (limit != null && (limit < 1 || limit > 100)) {
            throw new TelegramApiValidationException("Limit must be between 1 and 100", this);
        }
    }
}
