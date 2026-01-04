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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Describes a service message about the approval of a suggested post.
 * @author Generated using AI assistance
 * @version 9.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestedPostApproved implements BotApiObject {
    private static final String SUGGESTED_POST_MESSAGE_FIELD = "suggested_post_message";
    private static final String PRICE_FIELD = "price";
    private static final String SEND_DATE_FIELD = "send_date";

    /**
     * Optional.
     * Message containing the suggested post. Note that the Message object in this field will not contain 
     * the reply_to_message field even if it itself is a reply.
     */
    @JsonProperty(SUGGESTED_POST_MESSAGE_FIELD)
    private Message suggestedPostMessage;

    /**
     * Optional.
     * Amount paid for the post
     */
    @JsonProperty(PRICE_FIELD)
    private SuggestedPostPrice price;

    /**
     * Date when the post will be published
     */
    @JsonProperty(SEND_DATE_FIELD)
    @NonNull
    private Integer sendDate;
}
