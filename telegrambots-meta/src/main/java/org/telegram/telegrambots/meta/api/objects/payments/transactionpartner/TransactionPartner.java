package org.telegram.telegrambots.meta.api.objects.payments.transactionpartner;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * This object describes the source of a transaction, or its recipient for outgoing transactions.
 * Currently, it can be one of
 *  TransactionPartnerFragment
 *  TransactionPartnerUser
 *  TransactionPartnerOther
 *  TransactionPartnerTelegramAds
 *  TransactionPartnerTelegramApi
 *  TransactionPartnerAffiliateProgram
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransactionPartnerFragment.class, name = "fragment"),
        @JsonSubTypes.Type(value = TransactionPartnerUser.class, name = "user"),
        @JsonSubTypes.Type(value = TransactionPartnerOther.class, name = "other"),
        @JsonSubTypes.Type(value = TransactionPartnerTelegramAds.class, name = "telegram_ads"),
        @JsonSubTypes.Type(value = TransactionPartnerTelegramApi.class, name = "telegram_api"),
        @JsonSubTypes.Type(value = TransactionPartnerAffiliateProgram.class, name = "affiliate_program")
})
public interface TransactionPartner extends Validable, BotApiObject {
}
