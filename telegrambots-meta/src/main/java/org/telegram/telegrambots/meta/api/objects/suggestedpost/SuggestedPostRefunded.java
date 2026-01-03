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
 * Describes a service message about a payment refund for a suggested post.
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
public class SuggestedPostRefunded implements BotApiObject {
    private static final String SUGGESTED_POST_MESSAGE_FIELD = "suggested_post_message";
    private static final String REASON_FIELD = "reason";

    /**
     * Optional.
     * Message containing the suggested post. Note that the Message object in this field will not contain 
     * the reply_to_message field even if it itself is a reply.
     */
    @JsonProperty(SUGGESTED_POST_MESSAGE_FIELD)
    private Message suggestedPostMessage;

    /**
     * Reason for the refund. Currently, one of "post_deleted" if the post was deleted within 24 hours 
     * of being posted or removed from scheduled messages without being posted, or "payment_refunded" 
     * if the payer refunded their payment.
     */
    @JsonProperty(REASON_FIELD)
    @NonNull
    private String reason;
}
