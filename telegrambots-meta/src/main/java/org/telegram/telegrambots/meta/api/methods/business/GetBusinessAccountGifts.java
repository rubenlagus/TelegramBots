package org.telegram.telegrambots.meta.api.methods.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.gifts.owned.OwnedGifts;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Returns the gifts received and owned by a managed business account.
 * Requires the can_view_gifts_and_stars business bot right.
 * Returns OwnedGifts on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetBusinessAccountGifts extends BotApiMethod<OwnedGifts> {
    public static final String PATH = "getBusinessAccountGifts";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String EXCLUDE_UNSAVED_FIELD = "exclude_unsaved";
    private static final String EXCLUDE_SAVED_FIELD = "exclude_saved";
    private static final String EXCLUDE_UNLIMITED_FIELD = "exclude_unlimited";
    private static final String EXCLUDE_LIMITED_UPGRADABLE_FIELD = "exclude_limited_upgradable";
    private static final String EXCLUDE_LIMITED_NON_UPGRADABLE_FIELD = "exclude_limited_non_upgradable";
    private static final String EXCLUDE_FROM_BLOCKCHAIN_FIELD = "exclude_from_blockchain";
    private static final String EXCLUDE_UNIQUE_FIELD = "exclude_unique";
    private static final String SORT_BY_PRICE_FIELD = "sort_by_price";
    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    /**
     * Optional. Pass True to exclude gifts that aren't saved to the account's profile page
     */
    @JsonProperty(EXCLUDE_UNSAVED_FIELD)
    private Boolean excludeUnsaved;

    /**
     * Optional. Pass True to exclude gifts that are saved to the account's profile page
     */
    @JsonProperty(EXCLUDE_SAVED_FIELD)
    private Boolean excludeSaved;

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
     * Optional. Offset of the first entry to return as received from the previous request; use empty string to get the first chunk of results
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
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty", this);
        }
        if (limit != null && (limit < 1 || limit > 100)) {
            throw new TelegramApiValidationException("Limit parameter must be between 1 and 100", this);
        }
    }

    /**
     * @deprecated Use excludeLimitedUpgradable and excludeLimitedNonUpgradable fields instead
     */
    @Deprecated
    public Boolean getExcludeLimited() {
        if (Boolean.TRUE.equals(excludeLimitedUpgradable) && Boolean.TRUE.equals(excludeLimitedNonUpgradable)) {
            return true;
        }
        return null;
    }

    /**
     * @deprecated Use excludeLimitedUpgradable and excludeLimitedNonUpgradable fields instead
     */
    @Deprecated
    public void setExcludeLimited(Boolean excludeLimited) {
        if (Boolean.TRUE.equals(excludeLimited)) {
            this.excludeLimitedUpgradable = true;
            this.excludeLimitedNonUpgradable = true;
        } else {
            this.excludeLimitedUpgradable = null;
            this.excludeLimitedNonUpgradable = null;
        }
    }

    /**
     * Builder helper class for backward compatibility
     */
    public static class GetBusinessAccountGiftsBuilder {
        /**
         * @deprecated Use excludeLimitedUpgradable and excludeLimitedNonUpgradable builder methods instead
         */
        @Deprecated
        public GetBusinessAccountGiftsBuilder excludeLimited(Boolean excludeLimited) {
            if (Boolean.TRUE.equals(excludeLimited)) {
                this.excludeLimitedUpgradable = true;
                this.excludeLimitedNonUpgradable = true;
            } else {
                this.excludeLimitedUpgradable = null;
                this.excludeLimitedNonUpgradable = null;
            }
            return this;
        }
    }
}
