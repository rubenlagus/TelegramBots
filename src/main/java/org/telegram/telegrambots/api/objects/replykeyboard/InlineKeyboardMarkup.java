package org.telegram.telegrambots.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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

    private static final String KEYBOARD_FIELD = "inline_keyboard";
    @JsonProperty(KEYBOARD_FIELD)
    private List<List<InlineKeyboardButton>> keyboard; ///< Array of button rows, each represented by an Array of Strings

    public InlineKeyboardMarkup() {
        super();
        keyboard = new ArrayList<>();
    }

    public List<List<InlineKeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public InlineKeyboardMarkup setKeyboard(List<List<InlineKeyboardButton>> keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonkeyboard = new JSONArray();

        for (List<InlineKeyboardButton> innerRow : this.keyboard) {
            JSONArray innerJSONKeyboard = new JSONArray();
            for (InlineKeyboardButton element : innerRow) {
                innerJSONKeyboard.put(element.toJson());
            }
            jsonkeyboard.put(innerJSONKeyboard);
        }
        jsonObject.put(InlineKeyboardMarkup.KEYBOARD_FIELD, jsonkeyboard);

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeArrayFieldStart(KEYBOARD_FIELD);
        for (List<InlineKeyboardButton> innerRow : keyboard) {
            gen.writeStartArray();
            for (InlineKeyboardButton element : innerRow) {
                gen.writeObject(element);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "InlineKeyboardMarkup{" +
                "inline_keyboard=" + keyboard +
                '}';
    }
}
