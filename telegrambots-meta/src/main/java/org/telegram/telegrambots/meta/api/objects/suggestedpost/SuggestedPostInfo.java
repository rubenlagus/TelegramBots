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

/**
 * Contains information about a suggested post.
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
public class SuggestedPostInfo implements BotApiObject {
    private static final String STATE_FIELD = "state";
    private static final String PRICE_FIELD = "price";
    private static final String SEND_DATE_FIELD = "send_date";

    /**
     * State of the suggested post. Currently, it can be one of "pending", "approved", "declined".
     */
    @JsonProperty(STATE_FIELD)
    @NonNull
    private String state;

    /**
     * Optional. 
     * Proposed price of the post. If the field is omitted, then the post is unpaid.
     */
    @JsonProperty(PRICE_FIELD)
    private SuggestedPostPrice price;

    /**
     * Optional.
     * Proposed send date of the post. If the field is omitted, then the post can be published at any time 
     * within 30 days at the sole discretion of the user or administrator who approves it.
     */
    @JsonProperty(SEND_DATE_FIELD)
    private Integer sendDate;
}
