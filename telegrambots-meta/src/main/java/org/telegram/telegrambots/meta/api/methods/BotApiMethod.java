package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BotApiMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    protected static final String METHOD_FIELD = "method";

}
