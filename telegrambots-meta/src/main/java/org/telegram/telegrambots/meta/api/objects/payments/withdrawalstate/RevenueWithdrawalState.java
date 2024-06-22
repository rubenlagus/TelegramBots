package org.telegram.telegrambots.meta.api.objects.payments.withdrawalstate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * This object describes the state of a revenue withdrawal operation. Currently, it can be one of:
 * RevenueWithdrawalStatePending
 * RevenueWithdrawalStateSucceeded
 * RevenueWithdrawalStateFailed
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RevenueWithdrawalStatePending.class, name = "pending"),
        @JsonSubTypes.Type(value = RevenueWithdrawalStateSucceeded.class, name = "succeeded"),
        @JsonSubTypes.Type(value = RevenueWithdrawalStateFailed.class, name = "failed"),
})
public interface RevenueWithdrawalState extends Validable, BotApiObject {
}
