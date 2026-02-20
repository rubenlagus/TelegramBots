package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents the content of a message to be sent as a result of an inline
 * query.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(InputTextMessageContent.class),
        @JsonSubTypes.Type(InputVenueMessageContent.class),
        @JsonSubTypes.Type(InputLocationMessageContent.class),
        @JsonSubTypes.Type(InputContactMessageContent.class),
        @JsonSubTypes.Type(InputInvoiceMessageContent.class)
})
public interface InputMessageContent extends Validable, BotApiObject {
}
