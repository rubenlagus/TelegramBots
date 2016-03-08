package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.UserProfilePhotos;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 * @date 20 of June of 2015
 */
public class GetUserProfilePhotos extends BotApiMethod<UserProfilePhotos> {
    public static final String PATH = "getuserprofilephotos";

    public static final String USERID_FIELD = "user_id";
    private Integer userId; ///< Unique identifier of the target user
    public static final String OFFSET_FIELD = "offset";
    /**
     * Sequential number of the first photo to be returned. By default, all photos are returned.
     */
    private Integer offset;
    public static final String LIMIT_FIELD = "limit";
    /**
     * Optional. Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
     */
    private Integer limit;

    public GetUserProfilePhotos() {
        super();
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(USERID_FIELD, userId);
        jsonObject.put(OFFSET_FIELD, offset);
        if (limit != null) {
            jsonObject.put(LIMIT_FIELD, limit);
        }
        return jsonObject;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public UserProfilePhotos deserializeResponse(JSONObject answer) {
        if (answer.getBoolean("ok")) {
            return new UserProfilePhotos(answer.getJSONObject("result"));
        }
        return null;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeNumberField(USERID_FIELD, userId);
        gen.writeNumberField(OFFSET_FIELD, offset);
        if (limit != null) {
            gen.writeNumberField(LIMIT_FIELD, limit);
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
        return "GetUserProfilePhotos{" +
                "userId=" + userId +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
