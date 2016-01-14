package org.telegram.telegrambots.api.interfaces;

import org.json.JSONObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Add conversion to JSON object
 * @date 08 of September of 2015
 */
public interface IToJson {

    /**
     * Convert to json object
     * @return JSONObject created in the conversion
     */
    JSONObject toJson();
}
