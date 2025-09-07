package org.telegram.telegrambots.meta.api.objects.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * This object represents a topic of a direct messages chat.
 * @author Generated based on Telegram Bot API
 * @version 9.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
public class DirectMessagesTopic implements BotApiObject {
    private static final String TOPIC_ID_FIELD = "topic_id";
    private static final String USER_FIELD = "user";

    /**
     * Unique identifier of the topic
     */
    @JsonProperty(TOPIC_ID_FIELD)
    @NonNull
    private Integer topicId;

    /**
     * Optional.
     * Information about the user that created the topic.
     * Currently, it is always present
     */
    @JsonProperty(USER_FIELD)
    private User user;
}
