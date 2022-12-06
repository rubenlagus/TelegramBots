package org.telegram.telegrambots.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

import java.io.IOException;

public class TelegramMultipartBuilder {
    public final MultipartBody.Builder internalBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private final ObjectMapper mapper;

    public TelegramMultipartBuilder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public MultipartBody build() {
        return internalBuilder.build();
    }

    /**
     * Add field to the builder if value is not null
     * @param fieldName the field name to add to the multipart
     * @param value the nullable value to add
     * @return the builder
     */
    public TelegramMultipartBuilder addPart(String fieldName, String value) {
        if (value != null) {
            internalBuilder.addFormDataPart(fieldName, value);
        }
        return this;
    }

    /**
     * Add field to the builder if value is not null. The value is converted using toString()
     * @param fieldName the field name to add to the multipart
     * @param value the nullable value to add
     * @return the builder
     */
    public TelegramMultipartBuilder addPart(String fieldName, Object value) {
        if (value != null) {
            this.addPart(fieldName, value.toString());
        }
        return this;
    }

    /**
     * Add field to the builder if value is not null. The value is converted using ObjectMapper.writeValueAsString()
     * @param fieldName the field name to add to the multipart
     * @param value the nullable value to add
     * @return the builder
     */
    public TelegramMultipartBuilder addJsonPart(String fieldName, Object value) throws JsonProcessingException {
        if (value != null) {
            internalBuilder.addFormDataPart(fieldName, mapper.writeValueAsString(value));
        }
        return this;
    }

    public TelegramMultipartBuilder addInputFile(InputFile file, String fileField, boolean addField) throws IOException {
        if (file == null) return this;

        if (file.isNew()) {
            RequestBody body = null;
            if (file.getNewMediaFile() != null) {
                body = RequestBody.create(file.getNewMediaFile(), MediaType.parse("application/octet-stream"));
            } else if (file.getNewMediaStream() != null) {
                body = RequestBody.create(file.getNewMediaStream().readAllBytes(), MediaType.parse("application/octet-stream")
                );
            }
            if (body != null) {
                internalBuilder.addFormDataPart(file.getMediaName(), file.getMediaName(), body);
            }
        }

        if (addField) {
            internalBuilder.addFormDataPart(fileField, file.getAttachName());
        }

        return this;
    }

    public TelegramMultipartBuilder addMedia(InputMedia media) throws IOException {
        if (media == null) return this;

        if (media.isNewMedia()) {
            RequestBody body = null;
            if (media.getNewMediaFile() != null) {
                body = RequestBody.create(media.getNewMediaFile(), MediaType.parse("application/octet-stream"));
            } else if (media.getNewMediaStream() != null) {
                body = RequestBody.create(media.getNewMediaStream().readAllBytes(), MediaType.parse("application/octet-stream")
                );
            }
            if (body != null) {
                internalBuilder.addFormDataPart(media.getMediaName(), media.getMediaName(), body);
            }
        }

        return this;
    }
}
