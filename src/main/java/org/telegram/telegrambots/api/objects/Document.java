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
 * @brief This object represents a general file (as opposed to photos and audio files).
 * Telegram users can send files of any type of up to 1.5 GB in size.
 * @date 20 of June of 2015
 */
public class Document implements IBotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILENAME_FIELD = "file_name";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";
    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Unique identifier for this file
    @JsonProperty(THUMB_FIELD)
    private PhotoSize thumb; ///< Document thumbnail as defined by sender
    @JsonProperty(FILENAME_FIELD)
    private String fileName; ///< Optional. Original filename as defined by sender
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Optional. Mime type of a file as defined by sender
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size

    public Document() {
        super();
    }

    public Document(JSONObject jsonObject) {
        this.fileId = jsonObject.getString(FILEID_FIELD);
        if (jsonObject.has(THUMB_FIELD)) {
            this.thumb = new PhotoSize(jsonObject.getJSONObject(THUMB_FIELD));
        }
        if (jsonObject.has(FILENAME_FIELD)) {
            this.fileName = jsonObject.getString(FILENAME_FIELD);
        }
        if (jsonObject.has(MIMETYPE_FIELD)) {
            this.mimeType = jsonObject.getString(MIMETYPE_FIELD);
        }
        if (jsonObject.has(FILESIZE_FIELD)) {
            this.fileSize = jsonObject.getInt(FILESIZE_FIELD);
        }
    }

    public String getFileId() {
        return fileId;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(FILEID_FIELD, fileId);
        gen.writeObjectField(THUMB_FIELD, thumb);
        if (fileName != null) {
            gen.writeStringField(FILENAME_FIELD, fileName);
        }
        if (mimeType != null) {
            gen.writeStringField(MIMETYPE_FIELD, mimeType);
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
        return "Document{" +
                "fileId='" + fileId + '\'' +
                ", thumb=" + thumb +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
