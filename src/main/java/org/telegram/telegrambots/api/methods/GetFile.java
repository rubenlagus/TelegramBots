package org.telegram.telegrambots.api.methods;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.objects.File;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to get basic info about a file and prepare it for downloading.
 * For the moment, bots can download files of up to 20MB in size.
 * On success, a File object is returned.
 * The file can then be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>,
 * where <file_path> is taken from the response.
 * It is guaranteed that the link will be valid for at least 1 hour.
 * When the link expires, a new one can be requested by calling getFile again.
 * @date 20 of June of 2015
 */
public class GetFile extends BotApiMethod<File> {
    public static final String PATH = "getFile";

    public static final String FILEID_FIELD = "file_id";
    private String fileId; ///< File identifier to get info about

    public GetFile() {
        super();
    }

    public String getFileId() {
        return fileId;
    }

    public GetFile setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(METHOD_FIELD, PATH);
        gen.writeStringField(FILEID_FIELD, fileId);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(FILEID_FIELD, fileId);
        return jsonObject;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public File deserializeResponse(JSONObject answer) {
        if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
            return new File(answer.getJSONObject(Constants.RESPONSEFIELDRESULT));
        }
        return null;
    }

    @Override
    public String toString() {
        return "GetFile{" +
                "fileId='" + fileId + '\'' +
                '}';
    }
}
