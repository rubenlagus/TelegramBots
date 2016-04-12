package org.telegram.telegrambots.api.objects.replykeyboard;

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
 * @brief This object represents an inline keyboard that appears right next to the message it
 * belongs to
 * @note Inline keyboards are currently being tested and are only available in one-on-one chats
 * (i.e., user-bot or user-user in the case of inline bots).
 * @date 10 of April of 2016
 */
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "keyboard";
    private static final String RESIZEKEYBOARD_FIELD = "resize_keyboard";
    private static final String ONETIMEKEYBOARD_FIELD = "one_time_keyboard";
    private static final String SELECTIVE_FIELD = "selective";
    @JsonProperty(KEYBOARD_FIELD)
    private List<List<String>> keyboard; ///< Array of button rows, each represented by an Array of Strings
    @JsonProperty(RESIZEKEYBOARD_FIELD)
    private Boolean resizeKeyboard; ///< Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false.
    @JsonProperty(ONETIMEKEYBOARD_FIELD)
    private Boolean oneTimeKeyboad; ///< Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's
     * message is a reply (has reply_to_message_id), sender of the original message.
     */
    private Boolean selective;

    public InlineKeyboardMarkup() {
        super();
        keyboard = new ArrayList<List<String>>();
    }

    public InlineKeyboardMarkup(JSONObject jsonObject) {
        super();
        this.keyboard = new ArrayList<List<String>>();
        JSONArray keyboard = jsonObject.getJSONArray(KEYBOARD_FIELD);
        for (int i = 0; i < keyboard.length(); i++) {
            JSONArray keyboardRow = keyboard.getJSONArray(i);
            List<String> row = new ArrayList<String>();
            for (int j = 0; j < keyboardRow.length(); j++) {
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

    public InlineKeyboardMarkup setKeyboard(List<List<String>> keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    public Boolean getResizeKeyboard() {
        return resizeKeyboard;
    }

    public InlineKeyboardMarkup setResizeKeyboard(Boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
        return this;
    }

    public Boolean getOneTimeKeyboad() {
        return oneTimeKeyboad;
    }

    public InlineKeyboardMarkup setOneTimeKeyboad(Boolean oneTimeKeyboad) {
        this.oneTimeKeyboad = oneTimeKeyboad;
        return this;
    }

    public Boolean getSelective() {
        return selective;
    }

    public InlineKeyboardMarkup setSelective(Boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonkeyboard = new JSONArray();

        for (List<String> innerRow : this.keyboard) {
            JSONArray innerJSONKeyboard = new JSONArray();
            for (String element : innerRow) {
                innerJSONKeyboard.put(element);
            }
            jsonkeyboard.put(innerJSONKeyboard);
        }
        jsonObject.put(InlineKeyboardMarkup.KEYBOARD_FIELD, jsonkeyboard);

        if (this.oneTimeKeyboad != null) {
            jsonObject.put(InlineKeyboardMarkup.ONETIMEKEYBOARD_FIELD, this.oneTimeKeyboad);
        }
        if (this.resizeKeyboard != null) {
            jsonObject.put(InlineKeyboardMarkup.RESIZEKEYBOARD_FIELD, this.resizeKeyboard);
        }
        if (this.selective != null) {
            jsonObject.put(InlineKeyboardMarkup.SELECTIVE_FIELD, this.selective);
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeArrayFieldStart(KEYBOARD_FIELD);
        for (List<String> innerRow : keyboard) {
            gen.writeStartArray();
            for (String element : innerRow) {
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

    @Override
    public String toString() {
        return "ReplyKeyboardMarkup{" +
                "keyboard=" + keyboard +
                ", resizeKeyboard=" + resizeKeyboard +
                ", oneTimeKeyboad=" + oneTimeKeyboad +
                ", selective=" + selective +
                '}';
    }
}
