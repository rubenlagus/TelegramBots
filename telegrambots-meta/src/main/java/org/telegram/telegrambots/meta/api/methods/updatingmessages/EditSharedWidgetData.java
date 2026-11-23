package org.telegram.telegrambots.meta.api.methods.updatingmessages;

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

/**
 * Edits public data of a shared widget at most once per second. An empty string removes the data;
 * cache time is 5-3600 seconds, or unchanged when omitted.
 * @version 10.4
 */
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
public class EditSharedWidgetData extends BotApiMethodBoolean {
    public static final String PATH = "editSharedWidgetData";
    @JsonProperty("widget_id") @NonNull private String widgetId;
    @JsonProperty("public_data") @NonNull private String publicData;
    @JsonProperty("cache_time") private Integer cacheTime;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (widgetId == null || widgetId.isEmpty()) {
            throw new TelegramApiValidationException("widget_id can't be empty", this);
        }
        if (publicData == null) {
            throw new TelegramApiValidationException("public_data is required", this);
        }
        WidgetValidations.validateOptionalData(publicData, "public_data", this);
        if (cacheTime != null && (cacheTime < 5 || cacheTime > 3600)) {
            throw new TelegramApiValidationException("cache_time must be 5-3600 seconds", this);
        }
    }
}