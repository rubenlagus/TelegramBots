package org.telegram.telegrambots.api.methods.updates;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.WebhookInfo;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.1
 * @brief Return webhook information for current bot.
 *
 * If webhook is not configured, this method raise an exception
 *
 * @date 12 of August of 2016
 */
public class GetWebhookInfo extends BotApiMethod<WebhookInfo> {
    public static final String PATH = "getwebhookinfo";

    public GetWebhookInfo() {
    }

    @Override
    public String toString() {
        return "GetWebhookInfo{}";
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public WebhookInfo deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            return new WebhookInfo(answer.getJSONObject(Constants.RESPONSEFIELDRESULT));
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject();
    }
}
