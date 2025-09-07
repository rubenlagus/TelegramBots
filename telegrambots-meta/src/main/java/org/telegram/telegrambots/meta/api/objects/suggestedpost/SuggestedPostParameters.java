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

/**
 * Contains parameters of a post that is being suggested by the bot.
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
public class SuggestedPostParameters implements BotApiObject {
    private static final String PRICE_FIELD = "price";
    private static final String SEND_DATE_FIELD = "send_date";

    /**
     * Optional.
     * Proposed price for the post. If the field is omitted, then the post is unpaid.
     */
    @JsonProperty(PRICE_FIELD)
    private SuggestedPostPrice price;

    /**
     * Optional.
     * Proposed send date of the post. If specified, then the date must be between 300 second 
     * and 2678400 seconds (30 days) in the future. If the field is omitted, then the post 
     * can be published at any time within 30 days at the sole discretion of the user who approves it.
     */
    @JsonProperty(SEND_DATE_FIELD)
    private Integer sendDate;
}
