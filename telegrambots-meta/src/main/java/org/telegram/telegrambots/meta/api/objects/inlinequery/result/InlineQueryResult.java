package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.serialization.InlineQueryResultDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one result of an inline query.
 *
 * @apiNote All URLs passed in inline query results will be available to end users and therefore must be assumed to be public.
 */
@JsonDeserialize(using = InlineQueryResultDeserializer.class)
public interface InlineQueryResult extends Validable, BotApiObject {
    List<String> VALIDTHUMBTYPES = Collections.unmodifiableList(Arrays.asList("image/jpeg", "image/gif", "video/mp4"));

}
