package org.telegram.telegrambots.api.methods.send;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.ActionType;
import org.telegram.telegrambots.api.methods.BotApiMethod;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method when you need to tell the user that something is happening on the bot's
 * side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram
 * clients clear its typing status).
 * @date 20 of June of 2015
 */
public class SendChatAction extends BotApiMethod<Boolean> {

    public static final String PATH = "sendChatAction";

    public static final String CHATID_FIELD = "chat_id";
    public static final String ACTION_FIELD = "action";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Type of action to broadcast. Choose one, depending on what the user is about to receive:
     * 'typing' for text messages 'upload_photo' for photos 'record_video' or 'upload_video' for
     * videos 'record_audio' or 'upload_audio' for audio files 'upload_document' for general files,
     * 'find_location' for location data.
     */
    private ActionType action;

    public String getChatId() {
        return chatId;
    }

    public SendChatAction setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    /**
     * @deprecated
     * @return Action type text
     */
    @Deprecated
    public String getAction() {
        return action.toString();
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    /**
     * @deprecated Use {@link #setAction(ActionType)} instead
     * @param action Text of the action to create
     * @return Reference to this same instance
     * @throws IllegalArgumentException if action is not valid
     */
    @Deprecated
    public SendChatAction setAction(String action) throws IllegalArgumentException {
        this.action = ActionType.GetActionType(action);
        return this;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            return answer.getBoolean(Constants.RESPONSEFIELDRESULT);
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CHATID_FIELD, chatId);
        jsonObject.put(ACTION_FIELD, action);
        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(CHATID_FIELD, chatId);
        gen.writeStringField(ACTION_FIELD, action.toString());
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "SendChatAction{" +
                "chatId='" + chatId + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
