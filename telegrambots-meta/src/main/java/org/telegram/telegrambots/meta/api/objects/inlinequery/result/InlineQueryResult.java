package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.serialization.InlineQueryResultDeserializer;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one result of an inline query.
 */
@JsonDeserialize(using = InlineQueryResultDeserializer.class)
public interface InlineQueryResult extends InputBotApiObject, Validable {
}
