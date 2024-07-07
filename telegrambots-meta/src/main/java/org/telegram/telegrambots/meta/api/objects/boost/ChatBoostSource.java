package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes the source of a chat boost.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "source"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatBoostSourcePremium.class, name = ChatBoostSource.PREMIUM_TYPE),
        @JsonSubTypes.Type(value = ChatBoostSourceGiveaway.class, name = ChatBoostSource.GIVEAWAY_TYPE),
        @JsonSubTypes.Type(value = ChatBoostSourceGiftCode.class, name = ChatBoostSource.GIFT_CODE_TYPE)
})
public interface ChatBoostSource extends BotApiObject {
    String PREMIUM_TYPE = "premium";
    String GIFT_CODE_TYPE = "gift_code";
    String GIVEAWAY_TYPE = "giveaway";
}
