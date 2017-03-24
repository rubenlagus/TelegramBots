package org.telegram.telegrambots.api.objects.replykeyboard;





import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.ResponseParameters;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 06 of November of 2016
 */

public class ApiResponse<T> implements Serializable {
    private static final String OK_FIELD = "ok";
    private static final String ERROR_CODE_FIELD = "error_code";
    private static final String DESCRIPTION_CODE_FIELD = "description";
    private static final String PARAMETERS_FIELD = "parameters";
    private static final String RESULT_FIELD = "result";

    private Boolean ok;
    private Integer error_code;

    private String error_description;

    private ResponseParameters parameters;

    private T result;

    public Boolean getOk() {
        return ok;
    }

    public Integer getErrorCode() {
        return error_code;
    }

    public String getErrorDescription() {
        return error_description;
    }



    public T getResult() {


        return (T)result;
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
                    ", errorCode=" + error_code +
                    ", errorDescription='" + error_description + '\'' +
                    ", parameters='" + parameters + '\'' +
                    '}';
        }
    }
}
