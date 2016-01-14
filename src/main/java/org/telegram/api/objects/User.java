package org.telegram.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONObject;
import org.telegram.api.interfaces.BotApiObject;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a Telegram user or bot.
 * @date 20 of June of 2015
 */
public class User implements BotApiObject {

    public static final String ID_FIELD = "id";
    @JsonProperty(ID_FIELD)
    private Integer id; ///< Unique identifier for this user or bot
    public static final String FIRSTNAME_FIELD = "first_name";
    @JsonProperty(FIRSTNAME_FIELD)
    private String firstName; ///< User‘s or bot’s first name
    public static final String LASTNAME_FIELD = "last_name";
    @JsonProperty(LASTNAME_FIELD)
    private String lastName; ///< Optional. User‘s or bot’s last name
    public static final String USERNAME_FIELD = "username";
    @JsonProperty(USERNAME_FIELD)
    private String userName; ///< Optional. User‘s or bot’s username

    public User() {
        super();
    }

    public User(JSONObject jsonObject) {
        super();
        this.id = jsonObject.getInt(ID_FIELD);
        this.firstName = jsonObject.getString(FIRSTNAME_FIELD);
        if (jsonObject.has(LASTNAME_FIELD)) {
            this.lastName = jsonObject.getString(LASTNAME_FIELD);
        }
        if (jsonObject.has(USERNAME_FIELD)) {
            this.userName = jsonObject.getString(USERNAME_FIELD);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(ID_FIELD, id);
        gen.writeStringField(FIRSTNAME_FIELD, firstName);
        if (lastName != null) {
            gen.writeStringField(LASTNAME_FIELD, lastName);
        }
        if (userName != null) {
            gen.writeStringField(USERNAME_FIELD, userName);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
