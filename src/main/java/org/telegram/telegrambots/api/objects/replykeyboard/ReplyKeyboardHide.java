package org.telegram.telegrambots.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Upon receiving a message with this object, Telegram clients will hide the current custom
 * keyboard and display the default letter-keyboard. By default, custom keyboards are displayed
 * until a new keyboard is sent by a bot. An exception is made for one-time keyboards that are
 * hidden immediately after the user presses a button (@see ReplyKeyboardMarkup).
 * @date 20 of June of 2015
 */
public class ReplyKeyboardHide implements ReplyKeyboard {
    private static final String HIDEKEYBOARD_FIELD = "hide_keyboard";
    private static final String SELECTIVE_FIELD = "selective";
    @JsonProperty(HIDEKEYBOARD_FIELD)
    private Boolean hideKeyboard; ///< Requests clients to hide the custom keyboard
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's
     * message is a reply (has reply_to_message_id), sender of the original message.
     */
    @JsonProperty(SELECTIVE_FIELD)
    private Boolean selective;

    public ReplyKeyboardHide() {
        super();
        this.hideKeyboard = true;
    }

    public ReplyKeyboardHide(JSONObject jsonObject) {
        super();
        if (jsonObject.has(HIDEKEYBOARD_FIELD)) {
            this.hideKeyboard = jsonObject.getBoolean(HIDEKEYBOARD_FIELD);
        }
        if (jsonObject.has(SELECTIVE_FIELD)) {
            this.selective = jsonObject.getBoolean(SELECTIVE_FIELD);
        }
    }

    public Boolean getHideKeyboard() {
        return hideKeyboard;
    }

    public Boolean getSelective() {
        return selective;
    }

    public ReplyKeyboardHide setSelective(Boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(HIDEKEYBOARD_FIELD, this.hideKeyboard);
        jsonObject.put(SELECTIVE_FIELD, this.selective);
        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeBooleanField(HIDEKEYBOARD_FIELD, hideKeyboard);
        gen.writeBooleanField(SELECTIVE_FIELD, selective);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "ReplyKeyboardHide{" +
                "hideKeyboard=" + hideKeyboard +
                ", selective=" + selective +
                '}';
    }
}
