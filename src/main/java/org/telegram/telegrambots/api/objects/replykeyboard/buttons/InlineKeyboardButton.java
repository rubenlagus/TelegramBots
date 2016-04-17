package org.telegram.telegrambots.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;
import org.telegram.telegrambots.api.interfaces.IToJson;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one button of an inline keyboard. You must use exactly one of the
 * optional fields.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * display unsupported message.
 * @date 10 of April of 2016
 */
public class InlineKeyboardButton implements IBotApiObject, IToJson {

    private static final String TEXT_FIELD = "text";
    private static final String URL_FIELD = "url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";
    private static final String SWITCH_INLINE_QUERY_FIELD = "switch_inline_query";
    @JsonProperty(TEXT_FIELD)
    private String text; ///< Label text on the button
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. HTTP url to be opened when button is pressed
    @JsonProperty(CALLBACK_DATA_FIELD)
    private String callbackData; ///< Optional. Data to be sent in a callback query to the bot when button is pressed
    @JsonProperty(SWITCH_INLINE_QUERY_FIELD)
    /**
     * Optional.
     * If set, pressing the button will prompt the user to select one of their chats,
     * open that chat and insert the bot‘s username and the specified inline query in the input field.
     * Can be empty, in which case just the bot’s username will be inserted.
     * @Note: This offers an easy way for users to start using your bot in inline mode when
     * they are currently in a private chat with it.
     * Especially useful when combined with switch_pm… actions – in this case the user will
     * be automatically returned to the chat they switched from, skipping the chat selection screen.
     */
    private String switchInlineQuery;

    public InlineKeyboardButton() {
        super();
    }

    public InlineKeyboardButton(JSONObject jsonObject) {
        super();
        text = jsonObject.getString(TEXT_FIELD);
        if (jsonObject.has(URL_FIELD)) {
            url = jsonObject.getString(URL_FIELD);
        }
        if (jsonObject.has(CALLBACK_DATA_FIELD)) {
            callbackData = jsonObject.getString(CALLBACK_DATA_FIELD);
        }
        if (jsonObject.has(SWITCH_INLINE_QUERY_FIELD)) {
            switchInlineQuery = jsonObject.getString(SWITCH_INLINE_QUERY_FIELD);
        }
    }

    public String getText() {
        return text;
    }

    public InlineKeyboardButton setText(String text) {
        this.text = text;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public InlineKeyboardButton setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public InlineKeyboardButton setCallbackData(String callbackData) {
        this.callbackData = callbackData;
        return this;
    }

    public String getSwitchInlineQuery() {
        return switchInlineQuery;
    }

    public InlineKeyboardButton setSwitchInlineQuery(String switchInlineQuery) {
        this.switchInlineQuery = switchInlineQuery;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TEXT_FIELD, text);
        if (url != null) {
            jsonObject.put(URL_FIELD, url);
        }
        if (callbackData != null) {
            jsonObject.put(CALLBACK_DATA_FIELD, callbackData);
        }
        if (switchInlineQuery != null) {
            jsonObject.put(SWITCH_INLINE_QUERY_FIELD, switchInlineQuery);
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TEXT_FIELD, text);
        if (url != null) {
            gen.writeStringField(URL_FIELD, url);
        }
        if (callbackData != null) {
            gen.writeStringField(CALLBACK_DATA_FIELD, callbackData);
        }
        if (switchInlineQuery != null) {
            gen.writeStringField(SWITCH_INLINE_QUERY_FIELD, switchInlineQuery);
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
        return "InlineKeyboardButton{" +
                "text=" + text +
                ", url=" + url +
                ", callbackData=" + callbackData +
                ", switchInlineQuery=" + switchInlineQuery +
                '}';
    }
}
