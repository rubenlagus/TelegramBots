package org.telegram.telegrambots.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;

import java.lang.reflect.Field;

public class ApiResponseTestBuilder<T> {
    private static ObjectMapper mapper = new ObjectMapper();
    private ApiResponse<T> response = new ApiResponse<>();

    private final Field resultField;
    private final Field okField;
    private final Field errorCodeField;
    private final Field errorDescriptionField;
    private final Field parametersField;

    public ApiResponseTestBuilder() {
        try {
            resultField = ApiResponse.class.getDeclaredField("result");
            resultField.setAccessible(true);

            okField = ApiResponse.class.getDeclaredField("ok");
            okField.setAccessible(true);

            errorCodeField = ApiResponse.class.getDeclaredField("errorCode");
            errorCodeField.setAccessible(true);

            errorDescriptionField = ApiResponse.class.getDeclaredField("errorDescription");
            errorDescriptionField.setAccessible(true);

            parametersField = ApiResponse.class.getDeclaredField("parameters");
            parametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public String buildJson() {
        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponseTestBuilder<T> setResult(T result) {
        return setField(resultField, result);
    }

    public ApiResponseTestBuilder<T> setOk(boolean ok) {
        return setField(okField, ok);
    }

    public ApiResponseTestBuilder<T> setErrorCode(int errorCode) {
        return setField(errorCodeField, errorCode);
    }

    public ApiResponseTestBuilder<T> setErrorDescription(String errorDescription) {
        return setField(errorDescriptionField, errorDescription);
    }

    private ApiResponseTestBuilder<T> setField(Field field, Object value) {
        try {
            field.set(response, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}