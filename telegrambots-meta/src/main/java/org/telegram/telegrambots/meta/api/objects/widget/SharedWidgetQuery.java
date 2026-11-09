package org.telegram.telegrambots.meta.api.objects.widget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

/** A query from a forwarded message with a shared widget. @version 10.4 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SharedWidgetQuery implements BotApiObject {
    @JsonProperty("id")
    @NonNull
    private String id;
    @JsonProperty("from")
    @NonNull
    private User from;
    @JsonProperty("type")
    @NonNull
    private WidgetQueryType type;
    @JsonProperty("data")
    @NonNull
    private String data;
    @JsonProperty("widget_id")
    @NonNull
    private String widgetId;
}