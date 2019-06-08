package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Response from Telegram Server
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    private static final String OK_FIELD = "ok";
    private static final String ERROR_CODE_FIELD = "error_code";
    private static final String DESCRIPTION_CODE_FIELD = "description";
    private static final String PARAMETERS_FIELD = "parameters";
    private static final String RESULT_FIELD = "result";

    @JsonProperty(OK_FIELD)
    private Boolean ok;
    @JsonProperty(ERROR_CODE_FIELD)
    private Integer errorCode;
    @JsonProperty(DESCRIPTION_CODE_FIELD)
    private String errorDescription;
    @JsonProperty(PARAMETERS_FIELD)
    private ResponseParameters parameters;
    @JsonProperty(RESULT_FIELD)
    private T result;

    public Boolean getOk() {
        return ok;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public T getResult() {
        return result;
    }

    public ResponseParameters getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        if (ok) {
            return "ApiResponse{" +
                    "ok=" + ok +
                    ", result=" + result +
                    '}';
        } else {
            return "ApiResponse{" +
                    "ok=" + ok +
                    ", errorCode=" + errorCode +
                    ", errorDescription='" + errorDescription + '\'' +
                    ", parameters='" + parameters + '\'' +
                    '}';
        }
    }
}
