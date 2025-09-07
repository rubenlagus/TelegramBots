package org.telegram.telegrambots.meta.api.objects.suggestedpost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Describes a service message about the rejection of a suggested post.
 * @author Generated using AI assistance
 * @version 9.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestedPostDeclined implements BotApiObject {
    private static final String SUGGESTED_POST_MESSAGE_FIELD = "suggested_post_message";
    private static final String COMMENT_FIELD = "comment";

    /**
     * Optional.
     * Message containing the suggested post. Note that the Message object in this field will not contain 
     * the reply_to_message field even if it itself is a reply.
     */
    @JsonProperty(SUGGESTED_POST_MESSAGE_FIELD)
    private Message suggestedPostMessage;

    /**
     * Optional.
     * Comment with which the post was declined
     */
    @JsonProperty(COMMENT_FIELD)
    private String comment;
}
