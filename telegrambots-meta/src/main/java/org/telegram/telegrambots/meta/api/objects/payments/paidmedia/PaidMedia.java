package org.telegram.telegrambots.meta.api.objects.payments.paidmedia;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerFragment;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerOther;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerTelegramAds;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartnerUser;

/**
 * @author Ruben Bermudez
 * @version 7.6
 *
 * This object describes paid media. Currently, it can be one of
 * PaidMediaPreview
 * PaidMediaPhoto
 * PaidMediaVideo
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaidMediaPreview.class, name = "preview"),
        @JsonSubTypes.Type(value = PaidMediaPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = PaidMediaVideo.class, name = "video")
})
public interface PaidMedia extends Validable, BotApiObject {
}
