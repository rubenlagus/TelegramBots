package org.telegram.telegrambots.meta.api.methods.widget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.widget.WidgetValidations;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/** Answers a widget query within 15 seconds. The answer reaches only the querying widget and does not change its data. @version 10.4 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerWidgetQuery extends BotApiMethodBoolean {
    public static final String PATH = "answerWidgetQuery";
    @JsonProperty("widget_query_id") @NonNull private String widgetQueryId;
    @JsonProperty("data") private String data;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (widgetQueryId == null || widgetQueryId.isEmpty()) {
            throw new TelegramApiValidationException("widget_query_id can't be empty", this);
        }
        WidgetValidations.validateOptionalData(data, "data", this);
    }
}