package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.serialization.InputMessageContentDeserializer;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents the content of a message to be sent as a result of an inline
 * query.
 */
@JsonDeserialize(using = InputMessageContentDeserializer.class)
public interface InputMessageContent extends Validable, BotApiObject {
}
