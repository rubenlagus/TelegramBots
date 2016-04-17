package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represent a user's profile pictures.
 * @date 22 of June of 2015
 */
public class UserProfilePhotos implements IBotApiObject {

    private static final String TOTALCOUNT_FIELD = "total_count";
    private static final String PHOTOS_FIELD = "photos";
    @JsonProperty(TOTALCOUNT_FIELD)
    private Integer totalCount; ///< Total number of profile pictures the target user has
    @JsonProperty(PHOTOS_FIELD)
    private List<List<PhotoSize>> photos; ///< Requested profile pictures (in up to 4 sizes each)

    public UserProfilePhotos() {
        super();
    }

    public UserProfilePhotos(JSONObject jsonObject) {
        super();
        this.totalCount = jsonObject.getInt(TOTALCOUNT_FIELD);
        if (totalCount > 0) {
            this.photos = new ArrayList<>();
            JSONArray photos = jsonObject.getJSONArray(PHOTOS_FIELD);
            for (int i = 0; i < photos.length(); i++) {
                JSONArray innerArray = photos.getJSONArray(i);
                List<PhotoSize> innerPhotos = new ArrayList<>();
                for (int j = 0; j < innerArray.length(); j++) {
                    innerPhotos.add(new PhotoSize(innerArray.getJSONObject(j)));
                }
                this.photos.add(innerPhotos);
            }
        }
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(TOTALCOUNT_FIELD, totalCount);
        if (totalCount > 0) {
            gen.writeArrayFieldStart(PHOTOS_FIELD);
            for (List<PhotoSize> photoSizeList : photos) {
                gen.writeStartArray();
                for (PhotoSize photoSize: photoSizeList) {
                    gen.writeObject(photoSize);
                }
                gen.writeEndArray();
            }
            gen.writeEndArray();
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
        return "UserProfilePhotos{" +
                "totalCount=" + totalCount +
                ", photos=" + photos +
                '}';
    }
}
