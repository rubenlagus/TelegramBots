package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.databind.JsonSerializable;
import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IToJson;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief A method of Telegram Bots Api that is fully supported in json format
 * @date 07 of September of 2015
 */
public abstract class BotApiMethod<T extends Serializable> implements JsonSerializable, IToJson {
    protected static final String METHOD_FIELD = "method";

    /**
     * Getter for method path (that is the same as method name)
     * @return Method path
     */
    public abstract String getPath();

    /**
     * Deserialize a json answer to the response type to a method
     * @param answer Json answer received
     * @return Answer for the method
     */
    public abstract T deserializeResponse(JSONObject answer);
}
