package org.telegram.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a custom keyboard with reply options.
 * @date 20 of June of 2015
 */
public class ReplyKeyboardMarkup implements ReplyKeyboard {

    public static final String KEYBOARD_FIELD = "keyboard";
    @JsonProperty(KEYBOARD_FIELD)
    private List<List<String>> keyboard; ///< Array of button rows, each represented by an Array of Strings
    public static final String RESIZEKEYBOARD_FIELD = "resize_keyboard";
    @JsonProperty(RESIZEKEYBOARD_FIELD)
    private Boolean resizeKeyboard; ///< Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false.
    public static final String ONETIMEKEYBOARD_FIELD = "one_time_keyboard";
    @JsonProperty(ONETIMEKEYBOARD_FIELD)
    private Boolean oneTimeKeyboad; ///< Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
    public static final String SELECTIVE_FIELD = "selective";
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets:
     *      1) users that are @mentioned in the text of the Message object;
     *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    private Boolean selective;

    public ReplyKeyboardMarkup() {
        super();
        keyboard = new ArrayList<List<String>>();
    }

    public ReplyKeyboardMarkup(JSONObject jsonObject) {
        super();
        this.keyboard = new ArrayList<List<String>>();
        JSONArray keyboard = jsonObject.getJSONArray(KEYBOARD_FIELD);
        for (int i=0; i< keyboard.length(); i++) {
            JSONArray keyboardRow = keyboard.getJSONArray(i);
            List<String> row = new ArrayList<String>();
            for (int j=0; j < keyboardRow.length(); j++) {
                row.add(keyboardRow.getString(j));
            }
            this.keyboard.add(row);
        }
        if (jsonObject.has(RESIZEKEYBOARD_FIELD)) {
            this.resizeKeyboard = jsonObject.getBoolean(RESIZEKEYBOARD_FIELD);
        }
        if (jsonObject.has(ONETIMEKEYBOARD_FIELD)) {
            this.oneTimeKeyboad = jsonObject.getBoolean(ONETIMEKEYBOARD_FIELD);
        }
        if (jsonObject.has(SELECTIVE_FIELD)) {
            this.selective = jsonObject.getBoolean(SELECTIVE_FIELD);
        }
    }

    public List<List<String>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<String>> keyboard) {
        this.keyboard = keyboard;
    }

    public Boolean getResizeKeyboard() {
        return resizeKeyboard;
    }

    public void setResizeKeyboard(Boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
    }

    public Boolean getOneTimeKeyboad() {
        return oneTimeKeyboad;
    }

    public void setOneTimeKeyboad(Boolean oneTimeKeyboad) {
        this.oneTimeKeyboad = oneTimeKeyboad;
    }

    public Boolean getSelective() {
        return selective;
    }

    public void setSelective(Boolean selective) {
        this.selective = selective;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonkeyboard = new JSONArray();

        for (List<String> innerRow : this.keyboard) {
            JSONArray innerJSONKeyboard = new JSONArray();
            for (String element: innerRow) {
                innerJSONKeyboard.put(element);
            }
            jsonkeyboard.put(innerJSONKeyboard);
        }
        jsonObject.put(ReplyKeyboardMarkup.KEYBOARD_FIELD, jsonkeyboard);

        if (this.oneTimeKeyboad != null) {
            jsonObject.put(ReplyKeyboardMarkup.ONETIMEKEYBOARD_FIELD, this.oneTimeKeyboad);
        }
        if (this.resizeKeyboard != null) {
            jsonObject.put(ReplyKeyboardMarkup.RESIZEKEYBOARD_FIELD, this.resizeKeyboard);
        }
        if (this.selective != null) {
            jsonObject.put(ReplyKeyboardMarkup.SELECTIVE_FIELD, this.selective);
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeArrayFieldStart(KEYBOARD_FIELD);
        for (List<String> innerRow : keyboard) {
            gen.writeStartArray();
            for (String element: innerRow) {
                gen.writeString(element);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        if (this.oneTimeKeyboad != null) {
            gen.writeBooleanField(ONETIMEKEYBOARD_FIELD, oneTimeKeyboad);
        }
        if (this.resizeKeyboard != null) {
            gen.writeBooleanField(RESIZEKEYBOARD_FIELD, resizeKeyboard);
        }
        if (this.selective != null) {
            gen.writeBooleanField(SELECTIVE_FIELD, selective);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
