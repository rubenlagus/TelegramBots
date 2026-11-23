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
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

/** A query from a widget in a message sent by or via the bot. @version 10.4 */
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
public class WidgetQuery implements BotApiObject {
    @JsonProperty("id")
    @NonNull
    private String id;
    @JsonProperty("from")
    @NonNull
    private User from;
    @JsonProperty("chat")
    private Chat chat;
    @JsonProperty("message_id")
    private Integer messageId;
    @JsonProperty("inline_message_id")
    private String inlineMessageId;
    @JsonProperty("chat_instance")
    @NonNull
    private String chatInstance;
    @JsonProperty("chat_type")
    private String chatType;
    @JsonProperty("type")
    private WidgetQueryType type;
    @JsonProperty("data")
    @NonNull
    private String data;
    @JsonProperty("widget_id")
    private String widgetId;
}