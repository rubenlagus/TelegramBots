package org.telegram.telegrambots.meta.api.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * An object from the Bots API received from Telegram Servers
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BotApiObject extends Serializable {
}
