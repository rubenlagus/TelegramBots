package org.telegram.telegrambots.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MultipartBody;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

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
    public TelegramMultipartBuilder addPart(@NotNull String fieldName, @Nullable String value) {
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
    public TelegramMultipartBuilder addPart(@NotNull String fieldName, @Nullable Object value) {
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
    public TelegramMultipartBuilder addJsonPart(@NotNull String fieldName, @Nullable Object value) throws JsonProcessingException {
        if (value != null) {
            internalBuilder.addFormDataPart(fieldName, mapper.writeValueAsString(value));
        }
        return this;
    }
}
