package org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents the content of a contact message to be sent as the result of an inline query
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InputContactMessageContent implements InputMessageContent {

    private static final String PHONE_NUMBER_FIELD = "phone_number";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    @JsonProperty(PHONE_NUMBER_FIELD)
    private String phoneNumber; ///< Contact's phone number
    @JsonProperty(FIRST_NAME_FIELD)
    private String firstName; ///< Contact's first name
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName; ///< Optional. Contact's last name

    public InputContactMessageContent() {
        super();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InputContactMessageContent setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public InputContactMessageContent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InputContactMessageContent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PHONE_NUMBER_FIELD, phoneNumber);
        jsonObject.put(FIRST_NAME_FIELD, firstName);
        if (lastName != null) {
            jsonObject.put(LAST_NAME_FIELD, lastName);
        }
        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(PHONE_NUMBER_FIELD, phoneNumber);
        gen.writeStringField(FIRST_NAME_FIELD, firstName);
        if (lastName != null) {
            gen.writeStringField(LAST_NAME_FIELD, lastName);
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
        return "InputContactMessageContent{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
