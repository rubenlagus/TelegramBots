/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.api.objects.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief This object represents an animation file.
 * @date 27 of September of 2016
 */
public class Animation implements IBotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILENAME_FIELD = "file_name";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Unique file identifier
    @JsonProperty(THUMB_FIELD)
    private PhotoSize thumb; ///< Optional. Animation thumbnail as defined by sender
    @JsonProperty(FILENAME_FIELD)
    private String fileName; ///< Optional. Original animation filename as defined by sender
    @JsonProperty(MIMETYPE_FIELD)
    private String mimetype; ///< Optional. MIME type of the file as defined by sender
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size

    public Animation() {
        super();
    }

    public Animation(JSONObject object) {
        super();
        fileId = object.getString(FILEID_FIELD);
        if (object.has(THUMB_FIELD)) {
            thumb = new PhotoSize(object.getJSONObject(THUMB_FIELD));
        }
        if (object.has(FILENAME_FIELD)) {
            fileName = object.getString(FILENAME_FIELD);
        }
        if (object.has(MIMETYPE_FIELD)) {
            mimetype = object.getString(MIMETYPE_FIELD);
        }
        if (object.has(FILESIZE_FIELD)) {
            fileSize = object.getInt(FILESIZE_FIELD);
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

    public String getMimetype() {
        return mimetype;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(FILEID_FIELD, fileId);
        if (thumb != null) {
            gen.writeObjectField(THUMB_FIELD, thumb);
        }
        if (fileName != null) {
            gen.writeStringField(FILENAME_FIELD, fileName);
        }
        if (mimetype != null) {
            gen.writeStringField(MIMETYPE_FIELD, mimetype);
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
        return "Animation{" +
                "fileId='" + fileId + '\'' +
                ", thumb=" + thumb +
                ", fileName='" + fileName + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
