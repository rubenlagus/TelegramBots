package org.telegram.telegrambots.meta.api.objects.boost;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.boost.serialization.ChatBoostSourceDeserializer;

/**
 * @author Ruben Bermudez
 * @version 7.0
 * This object describes the source of a chat boost.
 */
@JsonDeserialize(using = ChatBoostSourceDeserializer.class)
public interface ChatBoostSource  extends BotApiObject {
    String PREMIUM_TYPE = "premium";
    String GIFT_CODE_TYPE = "gift_code";
    String GIVEAWAY_TYPE = "giveaway";
}
