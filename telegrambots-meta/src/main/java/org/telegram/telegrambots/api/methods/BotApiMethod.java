package org.telegram.telegrambots.api.methods;



import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * A method of Telegram Bots Api that is fully supported in json format
 */

public abstract class BotApiMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    protected static final String METHOD_FIELD = "method";

    /**
     * Getter for method path (that is the same as method name)
     * @return Method path
     */

    public abstract String getMethod();
}
