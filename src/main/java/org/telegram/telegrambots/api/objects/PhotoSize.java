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
 * @brief This object represents one size of a photo or a file / sticker thumbnail.
 * @date 20 of June of 2015
 */
public class PhotoSize implements IBotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String FILEPATH_FIELD = "file_path";
    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Unique identifier for this file
    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Photo width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Photo height
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size
    @JsonProperty(FILEPATH_FIELD)
    private String filePath; ///< Undocumented field. Optional. Can contain the path to download the file direclty without calling to getFile

    public PhotoSize() {
        super();
    }

    public PhotoSize(JSONObject jsonObject) {
        super();
        this.fileId = jsonObject.getString(FILEID_FIELD);
        this.width = jsonObject.getInt(WIDTH_FIELD);
        this.height = jsonObject.getInt(HEIGHT_FIELD);
        if (jsonObject.has(FILESIZE_FIELD)) {
            this.fileSize = jsonObject.getInt(FILESIZE_FIELD);
        }
        if (jsonObject.has(FILEPATH_FIELD)) {
            this.filePath = jsonObject.getString(FILEPATH_FIELD);
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

    public Integer getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(FILEID_FIELD, fileId);
        gen.writeNumberField(WIDTH_FIELD, width);
        gen.writeNumberField(HEIGHT_FIELD, height);
        if (fileSize != null) {
            gen.writeNumberField(FILESIZE_FIELD, fileSize);
        }
        if (filePath != null) {
            gen.writeStringField(FILEPATH_FIELD, filePath);
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
        return "PhotoSize{" +
                "fileId='" + fileId + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", fileSize=" + fileSize +
                '}';
    }
}
