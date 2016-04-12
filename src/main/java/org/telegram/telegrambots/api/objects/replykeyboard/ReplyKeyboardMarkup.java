package org.telegram.telegrambots.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

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

    private static final String KEYBOARD_FIELD = "keyboard";
    private static final String RESIZEKEYBOARD_FIELD = "resize_keyboard";
    private static final String ONETIMEKEYBOARD_FIELD = "one_time_keyboard";
    private static final String SELECTIVE_FIELD = "selective";
    @JsonProperty(KEYBOARD_FIELD)
    private List<KeyboardRow> keyboard; ///< Array of button rows, each represented by an Array of Strings
    @JsonProperty(RESIZEKEYBOARD_FIELD)
    private Boolean resizeKeyboard; ///< Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false.
    @JsonProperty(ONETIMEKEYBOARD_FIELD)
    private Boolean oneTimeKeyboad; ///< Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
    @JsonProperty(SELECTIVE_FIELD)
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets:
     *      1) users that are @mentioned in the text of the Message object;
     *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    private Boolean selective;

    public ReplyKeyboardMarkup() {
        super();
        keyboard = new ArrayList<>();
    }

    public List<KeyboardRow> getKeyboard() {
        return keyboard;
    }

    public ReplyKeyboardMarkup setKeyboard(List<KeyboardRow> keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    public Boolean getResizeKeyboard() {
        return resizeKeyboard;
    }

    public ReplyKeyboardMarkup setResizeKeyboard(Boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
        return this;
    }

    public Boolean getOneTimeKeyboad() {
        return oneTimeKeyboad;
    }

    public ReplyKeyboardMarkup setOneTimeKeyboad(Boolean oneTimeKeyboad) {
        this.oneTimeKeyboad = oneTimeKeyboad;
        return this;
    }

    public Boolean getSelective() {
        return selective;
    }

    public ReplyKeyboardMarkup setSelective(Boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonkeyboard = new JSONArray();

        for (KeyboardRow innerRow : this.keyboard) {
            JSONArray innerJSONKeyboard = new JSONArray();
            for (KeyboardButton button : innerRow) {
                innerJSONKeyboard.put(button.toJson());
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
        for (KeyboardRow innerRow : keyboard) {
            gen.writeStartArray();
            for (KeyboardButton button : innerRow) {
                gen.writeObject(button);
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
