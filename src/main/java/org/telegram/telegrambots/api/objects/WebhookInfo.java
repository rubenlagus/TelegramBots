package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief Contains information about the current status of a webhook.
 * @date 12 of August of 2016
 */
public class WebhookInfo implements IBotApiObject {

    private static final String URL_FIELD = "url";
    private static final String HASCUSTOMCERTIFICATE_FIELD = "has_custom_certificate";
    private static final String PENDINGUPDATESCOUNT_FIELD = "pending_updates_count";
    private static final String LASTERRORDATE_FIELD = "last_error_date";
    private static final String LASTERRORMESSAGE_FIELD = "last_error_message";

    @JsonProperty(URL_FIELD)
    private String url; ///< Webhook URL, may be empty if webhook is not set up
    @JsonProperty(HASCUSTOMCERTIFICATE_FIELD)
    private Boolean hasCustomCertificate; ///< True, if a custom certificate was provided for webhook certificate checks
    @JsonProperty(PENDINGUPDATESCOUNT_FIELD)
    private Integer pendingUpdatesCount; ///< Number updates awaiting delivery
    @JsonProperty(LASTERRORDATE_FIELD)
    private Integer lastErrorDate; ///< Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook
    @JsonProperty(LASTERRORMESSAGE_FIELD)
    private String lastErrorMessage; ///< Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook


    public WebhookInfo() {
    }

    public WebhookInfo(JSONObject object) {
        url = object.getString(URL_FIELD);
        hasCustomCertificate = object.getBoolean(HASCUSTOMCERTIFICATE_FIELD);
        pendingUpdatesCount = object.getInt(PENDINGUPDATESCOUNT_FIELD);
        if (object.has(LASTERRORDATE_FIELD)) {
            lastErrorDate = object.getInt(LASTERRORDATE_FIELD);
        }
        if (object.has(LASTERRORMESSAGE_FIELD)) {
            lastErrorMessage = object.getString(LASTERRORMESSAGE_FIELD);
        }

    }

    public String getUrl() {
        return url;
    }

    public boolean isHasCustomCertificate() {
        return hasCustomCertificate;
    }

    public int getPendingUpdatesCount() {
        return pendingUpdatesCount;
    }

    public int getLastErrorDate() {
        return lastErrorDate;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(URL_FIELD, url);
        gen.writeBooleanField(HASCUSTOMCERTIFICATE_FIELD, hasCustomCertificate);
        gen.writeNumberField(PENDINGUPDATESCOUNT_FIELD, pendingUpdatesCount);
        if (lastErrorDate != null) {
            gen.writeNumberField(LASTERRORDATE_FIELD, lastErrorDate);
        }
        if (lastErrorMessage != null) {
            gen.writeStringField(LASTERRORMESSAGE_FIELD, lastErrorMessage);
        }
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
