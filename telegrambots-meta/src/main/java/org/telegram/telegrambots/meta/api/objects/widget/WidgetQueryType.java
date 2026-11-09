package org.telegram.telegrambots.meta.api.objects.widget;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/** The type of a query from a widget. @version 10.4 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WidgetQueryTypeData.class, name = "data"),
        @JsonSubTypes.Type(value = WidgetQueryTypeSignData.class, name = "sign_data"),
        @JsonSubTypes.Type(value = WidgetQueryTypeSentTransaction.class, name = "sent_transaction")
})
public interface WidgetQueryType extends BotApiObject {
    String getType();
}