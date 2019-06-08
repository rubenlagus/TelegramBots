package org.telegram.telegrambots.meta.api.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * An object used in the Bots API to answer updates
 *
 * @deprecated Please, use BotApiObject directly
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Deprecated
public interface InputBotApiObject extends BotApiObject {
}
