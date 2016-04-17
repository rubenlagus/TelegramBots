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
 * @version 1.0
 * @brief This object represents a sticker.
 * @date 20 of June of 2015
 */
public class Sticker implements IBotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILESIZE_FIELD = "file_size";
    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Unique identifier for this file
    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Sticker width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Sticker height
    @JsonProperty(THUMB_FIELD)
    private PhotoSize thumb; ///< Optional. Sticker thumbnail in .webp or .jpg format
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size

    public Sticker() {
        super();
    }

    public Sticker(JSONObject jsonObject) {
        super();
        this.fileId = jsonObject.getString(FILEID_FIELD);
        this.width = jsonObject.getInt(WIDTH_FIELD);
        this.height = jsonObject.getInt(HEIGHT_FIELD);
        if (jsonObject.has(THUMB_FIELD)) {
            this.thumb = new PhotoSize(jsonObject.getJSONObject(THUMB_FIELD));
        }
        if (jsonObject.has(FILESIZE_FIELD)) {
            this.fileSize = jsonObject.getInt(FILESIZE_FIELD);
        }
    }
    
        public String getFileId() {
        return fileId;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getThumb() {
        return thumb;
    }
    
    public Integer getFileSize() {
        return fileSize;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(FILEID_FIELD, fileId);
        gen.writeNumberField(WIDTH_FIELD, width);
        gen.writeNumberField(HEIGHT_FIELD, height);
        if (thumb != null) {
            gen.writeObjectField(THUMB_FIELD, thumb);
        }
        if (fileSize != null) {
            gen.writeNumberField(FILESIZE_FIELD, fileSize);
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
        return "Sticker{" +
                "fileId='" + fileId + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", thumb=" + thumb +
                ", fileSize=" + fileSize +
                '}';
    }
}
